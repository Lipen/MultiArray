package com.github.lipen.multiarray.impl

import com.github.lipen.multiarray.Index
import com.github.lipen.multiarray.MultiArray
import com.github.lipen.multiarray.Shape
import com.github.lipen.multiarray.internal.Offset

abstract class AbstractMultiArray<out T>(
    final override val shape: Shape,
    final override val values: List<T>,
    private val offsetDelegate: Offset,
) : MultiArray<T> {
    final override val domains: List<IntRange> =
        offsetDelegate.domains
    final override val indices: Sequence<Index> =
        values.indices.asSequence().map { offsetDelegate.unsafeIndex(it) }
    final override val indicesReversed: Sequence<Index> =
        values.indices.reversed().asSequence().map { offsetDelegate.unsafeIndex(it) }

    final override fun getAt(index: Index): T = values[offsetDelegate.offset(index)]
    final override operator fun get(i: Int): T = values[offsetDelegate.offset(i)]
    final override operator fun get(i: Int, j: Int): T = values[offsetDelegate.offset(i, j)]
    final override operator fun get(i: Int, j: Int, k: Int): T = values[offsetDelegate.offset(i, j, k)]
    final override operator fun get(vararg index: Int): T = getAt(Index(index))

    override fun toString(): String {
        return "MultiArray(shape = ${shape.inner.asList()}, values = $values)"
    }
}
