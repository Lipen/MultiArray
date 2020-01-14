package com.github.lipen.multiarray

/**
 * Multi-dimensional array inspired by [kmath](https://github.com/altavir/kmath).
 *
 * @param[T] Element type.
 */
class MultiArray<T> private constructor(
    private val storage: MutableStorage<T>,
    private val strides: Strides
) {
    val shape: IntArray = strides.shape
    val values: List<T> = storage.values

    constructor(array: Array<T>, strides: Strides) : this(MutableStorage(array), strides)
    constructor(array: IntArray, strides: Strides) : this(MutableStorage(array), strides)
    constructor(array: BooleanArray, strides: Strides) : this(MutableStorage(array), strides)

    fun getBy(index: IntArray): T {
        validate(index)
        return storage[strides.offset1(index)]
    }

    fun setBy(index: IntArray, value: T) {
        validate(index)
        storage[strides.offset1(index)] = value
    }

    fun get(vararg index: Int): T = getBy(index)
    fun set(vararg index: Int, value: T): Unit = setBy(index, value)

    override fun toString(): String {
        return "MultiArray<${storage.typeString}>(shape = ${shape.asList()})"
    }

    companion object {
        @JvmStatic
        inline fun <reified T> create(
            shape: IntArray,
            init: (IntArray) -> T
        ): MultiArray<T> {
            if (shape.isEmpty()) return MultiArray(intArrayOf(), Strides(intArrayOf()))

            val size = shape.reduce(Int::times)
            val strides = Strides(shape)

            return when (T::class) {
                Int::class -> MultiArray(
                    IntArray(size) { init(strides.index1(it)) as Int },
                    strides
                )
                Boolean::class -> MultiArray(
                    BooleanArray(size) { init(strides.index1(it)) as Boolean },
                    strides
                )
                else -> MultiArray(
                    Array(size) { init(strides.index1(it)) },
                    strides
                )
            }
        }

        @JvmStatic
        @JvmName("createVararg")
        inline fun <reified T> create(
            vararg shape: Int,
            noinline init: (IntArray) -> T
        ): MultiArray<T> = create(shape, init)

        @JvmStatic
        fun createInt(
            shape: IntArray,
            init: (IntArray) -> Int = { 0 }
        ): MultiArray<Int> = create(shape, init)

        @JvmStatic
        @JvmName("createIntVararg")
        fun createInt(
            vararg shape: Int,
            init: (IntArray) -> Int = { 0 }
        ): MultiArray<Int> = create(shape, init)

        @JvmStatic
        fun createBoolean(
            shape: IntArray,
            init: (IntArray) -> Boolean = { false }
        ): MultiArray<Boolean> = create(shape, init)

        @JvmStatic
        @JvmName("createBooleanVararg")
        fun createBoolean(
            vararg shape: Int,
            init: (IntArray) -> Boolean = { false }
        ): MultiArray<Boolean> = create(shape, init)
    }
}

private class MutableStorage<T> private constructor(
    private val type: Type,
    private val genericArray: Array<T>? = null,
    private val intArray: IntArray? = null,
    private val booleanArray: BooleanArray? = null
) {
    @Suppress("UNCHECKED_CAST")
    val values: List<T> = when (type) {
        Type.GenericArray -> genericArray!!.asList()
        Type.IntArray -> intArray!!.asList() as List<T>
        Type.BooleanArray -> booleanArray!!.asList() as List<T>
    }

    val typeString: String = when (type) {
        Type.GenericArray -> "T"
        Type.IntArray -> "Int"
        Type.BooleanArray -> "Boolean"
    }

    @Suppress("UNCHECKED_CAST")
    operator fun get(index: Int): T = when (type) {
        Type.GenericArray -> genericArray!![index]
        Type.IntArray -> intArray!![index] as T
        Type.BooleanArray -> booleanArray!![index] as T
    }

    operator fun set(index: Int, value: T): Unit = when (type) {
        Type.GenericArray -> genericArray!![index] = value
        Type.IntArray -> intArray!![index] = value as Int
        Type.BooleanArray -> booleanArray!![index] = value as Boolean
    }

    enum class Type {
        GenericArray,
        IntArray,
        BooleanArray,
    }

    companion object {
        operator fun <T> invoke(array: Array<T>): MutableStorage<T> =
            MutableStorage(Type.GenericArray, genericArray = array)

        operator fun <T> invoke(array: IntArray): MutableStorage<T> =
            MutableStorage(Type.IntArray, intArray = array)

        operator fun <T> invoke(array: BooleanArray): MutableStorage<T> =
            MutableStorage(Type.BooleanArray, booleanArray = array)
    }
}

class Strides(val shape: IntArray) {
    private val strides: IntArray =
        if (shape.isEmpty())
            intArrayOf()
        else
            IntArray(shape.size).apply {
                this[lastIndex] = 1
                for (i in lastIndex - 1 downTo 0) {
                    this[i] = this[i + 1] * shape[i + 1]
                }
            }

    fun offset0(index0: IntArray): Int {
        return strides.foldIndexed(0) { i, acc, s -> acc + index0[i] * s }
    }

    fun offset1(index1: IntArray): Int {
        return strides.foldIndexed(0) { i, acc, s -> acc + (index1[i] - 1) * s }
    }

    fun index0(offset: Int): IntArray {
        val result = IntArray(strides.size)
        var current = offset
        for ((i, s) in strides.withIndex()) {
            result[i] = current / s // 0-based
            current %= s
            if (current == 0) break
        }
        return result
    }

    fun index1(offset: Int): IntArray {
        val result = IntArray(strides.size) { 1 }
        var current = offset
        for ((i, s) in strides.withIndex()) {
            result[i] = current / s + 1 // 1-based
            current %= s
            if (current == 0) break
        }
        return result
    }
}

private fun <T> MultiArray<T>.validate(index: IntArray) {
    require(index.size == shape.size) {
        "Invalid number of dimensions passed: index.size = ${index.size}, shape.size = ${shape.size}"
    }
    for (i in index.indices) {
        val ix = index[i]
        val domain = 1..shape[i]
        if (ix !in domain)
            throw IndexOutOfBoundsException("Index $ix (${i + 1}-th) out of bounds ($domain)")
    }
}

fun main() {
    val xs = MultiArray.create(2, 3) { 42 }
    println("xs = $xs")
    println("xs::class = ${xs::class}")
    println("xs.javaClass = ${xs.javaClass}")
    // println("xs is IntMultiArray = ${xs is IntMultiArray}")
    println("xs.values = ${xs.values}")
}
