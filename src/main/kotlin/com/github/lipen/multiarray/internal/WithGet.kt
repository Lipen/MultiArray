package com.github.lipen.multiarray.internal

internal interface WithGet<out T> {
    fun getAt(index: IntArray): T
    operator fun get(i: Int): T
    operator fun get(i: Int, j: Int): T
    operator fun get(i: Int, j: Int, k: Int): T
    operator fun get(vararg index: Int): T
}

internal class WithGetImpl<out T>(
    withValues: WithValues<T>,
    withOffset: WithOffset
) : WithGet<T>,
    WithValues<T> by withValues,
    WithOffset by withOffset {

    override fun getAt(index: IntArray): T = values[offset(index)]
    override operator fun get(i: Int): T = values[offset(i)]
    override operator fun get(i: Int, j: Int): T = values[offset(i, j)]
    override operator fun get(i: Int, j: Int, k: Int): T = values[offset(i, j, k)]
    override operator fun get(vararg index: Int): T = getAt(index)
}
