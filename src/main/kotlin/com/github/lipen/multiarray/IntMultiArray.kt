package com.github.lipen.multiarray

/**
 * One-based multi-dimensional array of `Int`s.
 */
class IntMultiArray(
    val data: IntArray,
    shape: IntArray
) : AbstractMultiArray1<Int>(shape) {
    override val values: List<Int> = data.asList()

    override fun setAt(index: IntArray, value: Int) {
        data[offset(index)] = value
    }

    override operator fun set(i: Int, value: Int) {
        data[offset(i)] = value
    }

    override operator fun set(i: Int, j: Int, value: Int) {
        data[offset(i, j)] = value
    }

    override operator fun set(i: Int, j: Int, k: Int, value: Int) {
        data[offset(i, j, k)] = value
    }

    override fun toString(): String {
        return "IntMultiArray(shape = ${shape.asList()}, values = $values)"
    }

    companion object {
        @JvmStatic
        fun create(shape: IntArray): IntMultiArray =
            IntMultiArray(IntArray(if (shape.isNotEmpty()) shape.reduce(Int::times) else 0), shape)

        @JvmStatic
        inline fun create(shape: IntArray, init: (IntArray) -> Int): IntMultiArray =
            create(shape).apply { fillBy(init) }

        @JvmStatic
        @JvmName("createVararg")
        fun create(vararg shape: Int): IntMultiArray = create(shape)

        @JvmName("createVararg")
        inline fun create(vararg shape: Int, init: (IntArray) -> Int): IntMultiArray = create(shape, init)
    }
}
