package com.github.lipen.multiarray.internal

internal interface Mutate<T> {
    val offsetDelegate: Offset

    fun setAt(index: IntArray, value: T)
    operator fun set(i: Int, value: T)
    operator fun set(i: Int, j: Int, value: T)
    operator fun set(i: Int, j: Int, k: Int, value: T)
}

internal abstract class AbstractMutate<T>(
    final override val offsetDelegate: Offset
) : Mutate<T>

internal class MutateImplMutableList<T>(
    private val data: MutableList<T>,
    offsetDelegate: Offset
) : AbstractMutate<T>(offsetDelegate) {
    override fun setAt(index: IntArray, value: T) {
        data[offsetDelegate.offset(index)] = value
    }

    override operator fun set(i: Int, value: T) {
        data[offsetDelegate.offset(i)] = value
    }

    override operator fun set(i: Int, j: Int, value: T) {
        data[offsetDelegate.offset(i, j)] = value
    }

    override operator fun set(i: Int, j: Int, k: Int, value: T) {
        data[offsetDelegate.offset(i, j, k)] = value
    }
}

internal class MutateImplArray<T>(
    private val data: Array<T>,
    offsetDelegate: Offset
) : AbstractMutate<T>(offsetDelegate) {
    override fun setAt(index: IntArray, value: T) {
        data[offsetDelegate.offset(index)] = value
    }

    override operator fun set(i: Int, value: T) {
        data[offsetDelegate.offset(i)] = value
    }

    override operator fun set(i: Int, j: Int, value: T) {
        data[offsetDelegate.offset(i, j)] = value
    }

    override operator fun set(i: Int, j: Int, k: Int, value: T) {
        data[offsetDelegate.offset(i, j, k)] = value
    }
}

internal class MutateImplIntArray(
    private val data: IntArray,
    offsetDelegate: Offset
) : AbstractMutate<Int>(offsetDelegate) {
    override fun setAt(index: IntArray, value: Int) {
        data[offsetDelegate.offset(index)] = value
    }

    override operator fun set(i: Int, value: Int) {
        data[offsetDelegate.offset(i)] = value
    }

    override operator fun set(i: Int, j: Int, value: Int) {
        data[offsetDelegate.offset(i, j)] = value
    }

    override operator fun set(i: Int, j: Int, k: Int, value: Int) {
        data[offsetDelegate.offset(i, j, k)] = value
    }
}

internal class MutateImplBooleanArray(
    private val data: BooleanArray,
    offsetDelegate: Offset
) : AbstractMutate<Boolean>(offsetDelegate) {
    override fun setAt(index: IntArray, value: Boolean) {
        data[offsetDelegate.offset(index)] = value
    }

    override operator fun set(i: Int, value: Boolean) {
        data[offsetDelegate.offset(i)] = value
    }

    override operator fun set(i: Int, j: Int, value: Boolean) {
        data[offsetDelegate.offset(i, j)] = value
    }

    override operator fun set(i: Int, j: Int, k: Int, value: Boolean) {
        data[offsetDelegate.offset(i, j, k)] = value
    }
}
