package com.github.lipen.multiarray.internal

internal interface WithSet<T> {
    fun setAt(index: IntArray, value: T)
    operator fun set(i: Int, value: T)
    operator fun set(i: Int, j: Int, value: T)
    operator fun set(i: Int, j: Int, k: Int, value: T)
    operator fun set(vararg index: Int, value: T)
}

internal abstract class AbstractWithSet<T>(
    withOffset: WithOffset
) : WithSet<T>,
    WithOffset by withOffset {

    final override operator fun set(vararg index: Int, value: T): Unit = setAt(index, value)
}

internal class WithSetImplMutableList<T>(
    val data: MutableList<T>,
    withOffset: WithOffset
) : AbstractWithSet<T>(withOffset) {

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
}

internal class WithSetImplArray<T>(
    val data: Array<T>,
    withOffset: WithOffset
) : AbstractWithSet<T>(withOffset) {

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
}

internal class WithSetImplIntArray(
    val data: IntArray,
    withOffset: WithOffset
) : AbstractWithSet<Int>(withOffset) {

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
}

internal class WithSetImplBooleanArray(
    val data: BooleanArray,
    withOffset: WithOffset
) : AbstractWithSet<Boolean>(withOffset) {

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
}
