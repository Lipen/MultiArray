package com.github.lipen.multiarray

import com.github.lipen.multiarray.internal.Offset

internal abstract class AbstractMultiArray<out T> internal constructor(
    final override val shape: IntArray,
    final override val values: List<T>,
    private val offsetDelegate: Offset
) : MultiArray<T> {
    final override val domains: List<IntRange> = offsetDelegate.domains
    final override val indices: Set<IntArray> by lazy {
        values.indices.map { offsetDelegate.unsafeIndex(it) }.toSet()
    }

    final override fun getAt(index: IntArray): T = values[offsetDelegate.offset(index)]
    final override operator fun get(i: Int): T = values[offsetDelegate.offset(i)]
    final override operator fun get(i: Int, j: Int): T = values[offsetDelegate.offset(i, j)]
    final override operator fun get(i: Int, j: Int, k: Int): T = values[offsetDelegate.offset(i, j, k)]
    final override operator fun get(vararg index: Int): T = getAt(index)
}
