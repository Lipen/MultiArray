package com.github.lipen.multiarray.internal

import com.github.lipen.multiarray.Index

interface Mutate<T> {
    val offsetDelegate: Offset

    fun setAt(index: Index, value: T)
    operator fun set(i: Int, value: T)
    operator fun set(i: Int, j: Int, value: T)
    operator fun set(i: Int, j: Int, k: Int, value: T)

    companion object {
        fun <T> from(
            data: MutableList<T>,
            offsetDelegate: Offset,
        ): Mutate<T> = MutateImplMutableList(data, offsetDelegate)

        fun <T> from(
            data: Array<T>,
            offsetDelegate: Offset,
        ): Mutate<T> = MutateImplArray(data, offsetDelegate)

        fun from(
            data: IntArray,
            offsetDelegate: Offset,
        ): Mutate<Int> = MutateImplIntArray(data, offsetDelegate)

        fun from(
            data: BooleanArray,
            offsetDelegate: Offset,
        ): Mutate<Boolean> = MutateImplBooleanArray(data, offsetDelegate)
    }
}

abstract class AbstractMutate<T>(
    final override val offsetDelegate: Offset,
) : Mutate<T>

class MutateImplMutableList<T>(
    private val data: MutableList<T>,
    offsetDelegate: Offset,
) : AbstractMutate<T>(offsetDelegate) {
    override fun setAt(index: Index, value: T) {
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

class MutateImplArray<T>(
    private val data: Array<T>,
    offsetDelegate: Offset,
) : AbstractMutate<T>(offsetDelegate) {
    override fun setAt(index: Index, value: T) {
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

class MutateImplIntArray(
    private val data: IntArray,
    offsetDelegate: Offset,
) : AbstractMutate<Int>(offsetDelegate) {
    override fun setAt(index: Index, value: Int) {
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

class MutateImplBooleanArray(
    private val data: BooleanArray,
    offsetDelegate: Offset,
) : AbstractMutate<Boolean>(offsetDelegate) {
    override fun setAt(index: Index, value: Boolean) {
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
