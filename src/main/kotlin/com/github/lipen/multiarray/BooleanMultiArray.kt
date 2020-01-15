package com.github.lipen.multiarray

/**
 * One-based multi-dimensional array of `Boolean`s.
 */
class BooleanMultiArray(
    val data: BooleanArray,
    shape: IntArray
) : AbstractMultiArray1<Boolean>(shape) {
    override val values: List<Boolean> = data.asList()

    override fun setAt(index: IntArray, value: Boolean) {
        data[offset(index)] = value
    }

    override operator fun set(i: Int, value: Boolean) {
        data[offset(i)] = value
    }

    override operator fun set(i: Int, j: Int, value: Boolean) {
        data[offset(i, j)] = value
    }

    override operator fun set(i: Int, j: Int, k: Int, value: Boolean) {
        data[offset(i, j, k)] = value
    }

    override fun toString(): String {
        return "BooleanMultiArray(shape = ${shape.asList()}, values = $values)"
    }

    companion object {
        @JvmStatic
        fun create(shape: IntArray): BooleanMultiArray =
            BooleanMultiArray(BooleanArray(if (shape.isNotEmpty()) shape.reduce(Int::times) else 0), shape)

        @JvmStatic
        fun create(shape: IntArray, init: (IntArray) -> Boolean): BooleanMultiArray =
            create(shape).apply { fillBy(init) }

        @JvmStatic
        @JvmName("createVararg")
        fun create(vararg shape: Int): BooleanMultiArray = create(shape)

        @JvmName("createVararg")
        fun create(vararg shape: Int, init: (IntArray) -> Boolean): BooleanMultiArray = create(shape, init)
    }
}
