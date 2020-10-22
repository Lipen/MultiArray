package com.github.lipen.multiarray.internal

internal interface Offset {
    val domains: List<IntRange>

    fun index(offset: Int): IntArray
    fun offset(index: IntArray): Int
    fun offset(i: Int): Int
    fun offset(i: Int, j: Int): Int
    fun offset(i: Int, j: Int, k: Int): Int

    fun unsafeIndex(offset: Int): IntArray
    fun unsafeOffset(index: IntArray): Int
    fun unsafeOffset(i: Int): Int
    fun unsafeOffset(i: Int, j: Int): Int
    fun unsafeOffset(i: Int, j: Int, k: Int): Int

    companion object {
        fun from(shape: IntArray, zerobased: Boolean): Offset = OffsetImpl(shape, zerobased)
    }
}

private class OffsetImpl(
    shape: IntArray,
    zerobased: Boolean
) : Offset {
    private val strides: Strides = Strides.from(shape, zerobased)
    private val dims: Int = shape.size
    override val domains: List<IntRange> = strides.domains
    private val offsetBounds: IntRange =
        0 until domains.map { it.last - it.first + 1 }.fold(1, Int::times)

    override fun unsafeIndex(offset: Int): IntArray = strides.index(offset)
    override fun unsafeOffset(index: IntArray): Int = strides.offset(index)
    override fun unsafeOffset(i: Int): Int = strides.offset(i)
    override fun unsafeOffset(i: Int, j: Int): Int = strides.offset(i, j)
    override fun unsafeOffset(i: Int, j: Int, k: Int): Int = strides.offset(i, j, k)

    override fun index(offset: Int): IntArray {
        checkOffset(offset)
        return unsafeIndex(offset)
    }

    override fun offset(index: IntArray): Int {
        checkIndex(index)
        return unsafeOffset(index)
    }

    override fun offset(i: Int): Int {
        checkIndex(i)
        return unsafeOffset(i)
    }

    override fun offset(i: Int, j: Int): Int {
        checkIndex(i, j)
        return unsafeOffset(i, j)
    }

    override fun offset(i: Int, j: Int, k: Int): Int {
        checkIndex(i, j, k)
        return unsafeOffset(i, j, k)
    }

    private fun checkOffset(offset: Int) {
        require(offset in offsetBounds) { "Offset $offset is out of bounds ($offsetBounds)" }
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
}
