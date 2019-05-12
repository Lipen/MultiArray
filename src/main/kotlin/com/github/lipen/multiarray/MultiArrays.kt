package com.github.lipen.multiarray

/**
 * Multi-dimensional array inspired by [kmath](https://github.com/altavir/kmath).
 *
 * @param[T] element type.
 */
interface MultiArray<T> : Collection<T> {
    val shape: IntArray
    val values: Collection<T>

    operator fun get(vararg index: Int): T
    operator fun set(vararg index: Int, value: T)

    companion object {
        @JvmStatic
        fun <T> create(shape: IntArray, init: (IntArray) -> T): MultiArray<T> = DefaultMultiArray.create(shape, init)

        @JvmStatic
        @JvmName("createVararg")
        fun <T> create(vararg shape: Int, init: (IntArray) -> T): MultiArray<T> = create(shape, init)
    }
}

/**
 * One-based multi-dimensional array.
 *
 * @param[T] element type.
 */
private class DefaultMultiArray<T> private constructor(
    private val strides: Strides,
    private val buffer: MutableList<T>
) : MultiArray<T>, Collection<T> by buffer {
    override val shape: IntArray = strides.shape
    override val values: Collection<T> = buffer

    override fun get(vararg index: Int): T {
        validate(index)
        return buffer[strides.offset1(index)]
    }

    override fun set(vararg index: Int, value: T) {
        validate(index)
        buffer[strides.offset1(index)] = value
    }

    override fun toString(): String {
        return "MultiArray(shape = ${shape.asList()})"
    }

    companion object {
        @JvmStatic
        fun <T> create(shape: IntArray, init: (IntArray) -> T): DefaultMultiArray<T> {
            val size = shape.reduceIfNotEmpty()
            val strides = Strides(shape)
            val buffer = MutableList(size) { init(strides.index1(it)) }
            return DefaultMultiArray(strides, buffer)
        }
    }
}

/**
 * One-based `Int` multi-dimensional array.
 */
class IntMultiArray private constructor(
    private val strides: Strides,
    private val buffer: IntArray
) : MultiArray<Int>, Collection<Int> by buffer.asList() {
    override val shape: IntArray = strides.shape
    override val values: Collection<Int> = buffer.asList()

    override operator fun get(vararg index: Int): Int {
        validate(index)
        return buffer[strides.offset1(index)]
    }

    override operator fun set(vararg index: Int, value: Int) {
        validate(index)
        buffer[strides.offset1(index)] = value
    }

    override fun toString(): String {
        return "IntMultiArray(shape = ${shape.asList()})"
    }

    companion object {
        @JvmStatic
        @JvmOverloads
        fun create(shape: IntArray, init: (IntArray) -> Int = { 0 }): IntMultiArray {
            val size = shape.reduceIfNotEmpty()
            val strides = Strides(shape)
            val buffer = IntArray(size) { init(strides.index1(it)) }
            return IntMultiArray(strides, buffer)
        }

        @JvmStatic
        @JvmOverloads
        @JvmName("createVararg")
        fun create(vararg shape: Int, init: (IntArray) -> Int = { 0 }): IntMultiArray = create(shape, init)
    }
}

/**
 * One-based `Boolean` multi-dimensional array.
 */
class BooleanMultiArray private constructor(
    private val strides: Strides,
    private val buffer: BooleanArray
) : MultiArray<Boolean>, Collection<Boolean> by buffer.asList() {
    override val shape: IntArray = strides.shape
    override val values: Collection<Boolean> = buffer.asList()

    override operator fun get(vararg index: Int): Boolean {
        validate(index)
        return buffer[strides.offset1(index)]
    }

    override operator fun set(vararg index: Int, value: Boolean) {
        validate(index)
        buffer[strides.offset1(index)] = value
    }

    override fun toString(): String {
        return "BooleanMultiArray(shape = ${shape.asList()})"
    }

    companion object {
        @JvmStatic
        @JvmOverloads
        fun create(shape: IntArray, init: (IntArray) -> Boolean = { false }): BooleanMultiArray {
            val size = shape.reduceIfNotEmpty()
            val strides = Strides(shape)
            val buffer = BooleanArray(size) { init(strides.index1(it)) }
            return BooleanMultiArray(strides, buffer)
        }

        @JvmStatic
        @JvmOverloads
        @JvmName("createVararg")
        fun create(vararg shape: Int, init: (IntArray) -> Boolean = { false }): BooleanMultiArray = create(shape, init)
    }
}

private class Strides(val shape: IntArray) {
    private val strides: List<Int> by lazy {
        sequence {
            yield(1)
            var cur = 1
            for (i in (shape.size - 1) downTo 1) {
                cur *= shape[i]
                yield(cur)
            }
        }.toList().reversed()
    }

    @Suppress("unused")
    fun offset0(index0: IntArray): Int {
        return index0.asSequence().zip(strides.asSequence()) { i, s -> i * s }.sum()
    }

    fun offset1(index1: IntArray): Int {
        return index1.asSequence().zip(strides.asSequence()) { i, s -> (i - 1) * s }.sum()
    }

    @Suppress("unused")
    fun index0(offset: Int): IntArray {
        val result = IntArray(shape.size)
        var current = offset
        for ((i, s) in strides.withIndex()) {
            result[i] = current / s // 0-based
            current %= s
            if (current == 0) break
        }
        return result
    }

    fun index1(offset: Int): IntArray {
        val result = IntArray(shape.size) { 1 }
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

private fun IntArray.reduceIfNotEmpty(default: Int = 0): Int =
    if (isNotEmpty()) reduce(Int::times) else default
