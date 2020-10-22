package com.github.lipen.multiarray.internal

internal interface Offset {
    val domains: List<IntRange>

    fun offset(index: IntArray): Int
    fun offset(i: Int): Int
    fun offset(i: Int, j: Int): Int
    fun offset(i: Int, j: Int, k: Int): Int
    fun index(offset: Int): IntArray

    fun unsafeOffset(index: IntArray): Int
    fun unsafeOffset(i: Int): Int
    fun unsafeOffset(i: Int, j: Int): Int
    fun unsafeOffset(i: Int, j: Int, k: Int): Int
    fun unsafeIndex(offset: Int): IntArray
}

internal abstract class AbstractOffset(shape: IntArray) : Offset {
    protected val strides: Strides = Strides(shape)
    private val offsetBounds: IntRange by lazy {
        0 until domains.map { it.last - it.first + 1 }.fold(1, Int::times)
    }
    private val dims: Int = shape.size

    final override fun offset(index: IntArray): Int {
        checkIndex(index)
        return unsafeOffset(index)
    }

    final override fun offset(i: Int): Int {
        checkIndex(i)
        return unsafeOffset(i)
    }

    final override fun offset(i: Int, j: Int): Int {
        checkIndex(i, j)
        return unsafeOffset(i, j)
    }

    final override fun offset(i: Int, j: Int, k: Int): Int {
        checkIndex(i, j, k)
        return unsafeOffset(i, j, k)
    }

    final override fun index(offset: Int): IntArray {
        checkOffset(offset)
        return unsafeIndex(offset)
    }

    private fun checkIndex(index: IntArray) {
        require(index.size == dims) {
            "Invalid number of dimensions passed (index.size = ${index.size}, dims = $dims)"
        }
        for (i in index.indices) {
            val ix = index[i]
            val domain = domains[i]
            check(ix in domain) {
                "${i + 1}-th index $ix is out of bounds ($domain)"
            }
        }
    }

    private fun checkIndex(i: Int) {
        require(dims == 1) { "Invalid number (1) of dimensions passed (dims = $dims)" }
        check(i in domains[0]) { "Index i = $i is out of bounds (${domains[0]})" }
    }

    private fun checkIndex(i: Int, j: Int) {
        require(dims == 2) { "Invalid number (2) of dimensions passed (dims = $dims)" }
        check(i in domains[0]) { "Index i = $i is out of bounds (${domains[0]})" }
        check(j in domains[1]) { "Index j = $j is out of bounds (${domains[1]})" }
    }

    private fun checkIndex(i: Int, j: Int, k: Int) {
        require(dims == 3) { "Invalid number (3) of dimensions passed (dims = $dims)" }
        check(i in domains[0]) { "Index i = $i is out of bounds (${domains[0]})" }
        check(j in domains[1]) { "Index j = $j is out of bounds (${domains[1]})" }
        check(k in domains[2]) { "Index k = $k is out of bounds (${domains[2]})" }
    }

    private fun checkOffset(offset: Int) {
        require(offset in offsetBounds) { "Offset $offset is out of bounds (${offsetBounds})" }
    }
}

internal class OffsetImpl1(shape: IntArray) : AbstractOffset(shape) {
    override val domains: List<IntRange> = shape.map { 1..it }

    override fun unsafeOffset(index: IntArray): Int = strides.offset1(index)
    override fun unsafeOffset(i: Int): Int = strides.offset1(i)
    override fun unsafeOffset(i: Int, j: Int): Int = strides.offset1(i, j)
    override fun unsafeOffset(i: Int, j: Int, k: Int): Int = strides.offset1(i, j, k)
    override fun unsafeIndex(offset: Int): IntArray = strides.index1(offset)
}

internal class OffsetImpl0(shape: IntArray) : AbstractOffset(shape) {
    override val domains: List<IntRange> = shape.map { 0 until it }

    override fun unsafeOffset(index: IntArray): Int = strides.offset0(index)
    override fun unsafeOffset(i: Int): Int = strides.offset0(i)
    override fun unsafeOffset(i: Int, j: Int): Int = strides.offset0(i, j)
    override fun unsafeOffset(i: Int, j: Int, k: Int): Int = strides.offset0(i, j, k)
    override fun unsafeIndex(offset: Int): IntArray = strides.index0(offset)
}
