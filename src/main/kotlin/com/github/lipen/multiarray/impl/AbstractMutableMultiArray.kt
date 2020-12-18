package com.github.lipen.multiarray.impl

import com.github.lipen.multiarray.Index
import com.github.lipen.multiarray.MutableMultiArray
import com.github.lipen.multiarray.internal.Mutate

abstract class AbstractMutableMultiArray<T>(
    shape: IntArray,
    values: List<T>,
    private val mutateDelegate: Mutate<T>,
) : MutableMultiArray<T>, AbstractMultiArray<T>(shape, values, mutateDelegate.offsetDelegate) {
    final override fun setAt(index: Index, value: T): Unit = mutateDelegate.setAt(index, value)
    final override fun set(i: Int, value: T): Unit = mutateDelegate.set(i, value)
    final override fun set(i: Int, j: Int, value: T): Unit = mutateDelegate.set(i, j, value)
    final override fun set(i: Int, j: Int, k: Int, value: T): Unit = mutateDelegate.set(i, j, k, value)
    final override fun set(vararg index: Int, value: T): Unit = setAt(index, value)

    final override fun asMut(): MutableMultiArray<T> = this
}
