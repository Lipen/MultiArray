package com.github.lipen.multiarray

/**
 * Generic one-based multi-dimensional array.
 *
 * @param[T] Element type.
 */
class GenericMultiArray<T>(
    val data: Array<T>,
    shape: IntArray
) : AbstractMultiArray1<T>(shape) {
    override val values: List<T> = data.asList()

    override fun setAt(index: IntArray, value: T) {
        data[offset(index)] = value
    }

    override operator fun set(i: Int, value: T) {
        data[offset(i)] = value
    }

    override operator fun set(i: Int, j: Int, value: T) {
        data[offset(i, j)] = value
    }

    override operator fun set(i: Int, j: Int, k: Int, value: T) {
        data[offset(i, j, k)] = value
    }

    override fun toString(): String {
        return "MultiArray(shape = ${shape.asList()}, values = ${values})"
    }

    companion object {
        @JvmStatic
        inline fun <reified T> create(
            shape: IntArray,
            noinline init: (IntArray) -> T
        ): MultiArray<T> {
            val size = if (shape.isNotEmpty()) shape.reduce(Int::times) else 0
            @Suppress("UNCHECKED_CAST")
            val data = arrayOfNulls<T>(size) as Array<T>
            return GenericMultiArray(data, shape).apply { fillBy(init) }
        }

        @JvmName("createVararg")
        inline fun <reified T> create(
            vararg shape: Int,
            noinline init: (IntArray) -> T
        ): MultiArray<T> = create(shape, init)
    }
}
