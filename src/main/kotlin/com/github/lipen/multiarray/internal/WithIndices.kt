package com.github.lipen.multiarray.internal

internal interface WithIndices {
    val indices: Set<IntArray>
}

internal class WithIndicesImpl<T>(
    withValues: WithValues<T>,
    withOffset: WithOffset
) : WithIndices,
    WithValues<T> by withValues,
    WithOffset by withOffset {

    override val indices: Set<IntArray> by lazy {
        values.indices.map { unsafeIndex(it) }.toSet()
    }
}
