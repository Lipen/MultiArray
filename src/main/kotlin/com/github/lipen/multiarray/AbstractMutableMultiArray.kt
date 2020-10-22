package com.github.lipen.multiarray

import com.github.lipen.multiarray.internal.Mutate

internal abstract class AbstractMutableMultiArray<T> internal constructor(
    shape: IntArray,
    values: List<T>,
    private val mutateDelegate: Mutate<T>
) : MutableMultiArray<T>, AbstractMultiArray<T>(shape, values, mutateDelegate.offsetDelegate) {
    final override fun setAt(index: IntArray, value: T): Unit = mutateDelegate.setAt(index, value)
    final override fun set(i: Int, value: T): Unit = mutateDelegate.set(i, value)
    final override fun set(i: Int, j: Int, value: T): Unit = mutateDelegate.set(i, j, value)
    final override fun set(i: Int, j: Int, k: Int, value: T): Unit = mutateDelegate.set(i, k, value)
    final override fun set(vararg index: Int, value: T): Unit = setAt(index, value)

    final override fun asMut(): MutableMultiArray<T> = this
}
