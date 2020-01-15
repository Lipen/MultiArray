package com.github.lipen.multiarray

/**
 * MultiArray base class.
 */
abstract class AbstractMultiArray<T>(
    final override val shape: IntArray
) : MultiArray<T> {
    final override val dims: Int = shape.size
    final override val indices: Sequence<IntArray> by lazy {
        values.indices.asSequence().map { unsafeIndex(it) }
    }

    protected fun offset(index: IntArray): Int {
        checkIndex(index)
        return unsafeOffset(index)
    }

    protected fun offset(i: Int): Int {
        checkIndex(i)
        return unsafeOffset(i)
    }

    protected fun offset(i: Int, j: Int): Int {
        checkIndex(i, j)
        return unsafeOffset(i, j)
    }

    protected fun offset(i: Int, j: Int, k: Int): Int {
        checkIndex(i, j, k)
        return unsafeOffset(i, j, k)
    }

    protected fun index(offset: Int): IntArray {
        checkOffset(offset)
        return unsafeIndex(offset)
    }

    protected abstract fun unsafeOffset(index: IntArray): Int
    protected abstract fun unsafeOffset(i: Int): Int
    protected abstract fun unsafeOffset(i: Int, j: Int): Int
    protected abstract fun unsafeOffset(i: Int, j: Int, k: Int): Int
    protected abstract fun unsafeIndex(offset: Int): IntArray

    final override fun getAt(index: IntArray): T = values[offset(index)]
    final override fun get(i: Int): T = values[offset(i)]
    final override fun get(i: Int, j: Int): T = values[offset(i, j)]
    final override fun get(i: Int, j: Int, k: Int): T = values[offset(i, j, k)]
    final override fun get(vararg index: Int): T = getAt(index)
    final override fun set(vararg index: Int, value: T): Unit = setAt(index, value)

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
        require(offset in values.indices) { "Offset $offset is out of bounds (${values.indices})" }
    }
}

/**
 * One-based MultiArray base class.
 */
abstract class AbstractMultiArray1<T>(shape: IntArray) : AbstractMultiArray<T>(shape) {
    private val strides: Strides = Strides(shape)
    final override val domains: List<IntRange> = shape.map { 1..it }

    final override fun unsafeOffset(index: IntArray): Int = strides.offset1(index)
    final override fun unsafeOffset(i: Int): Int = strides.offset1(i)
    final override fun unsafeOffset(i: Int, j: Int): Int = strides.offset1(i, j)
    final override fun unsafeOffset(i: Int, j: Int, k: Int): Int = strides.offset1(i, j, k)
    final override fun unsafeIndex(offset: Int): IntArray = strides.index1(offset)
}

/**
 * Zero-based MultiArray base class.
 */
abstract class AbstractMultiArray0<T>(shape: IntArray) : AbstractMultiArray<T>(shape) {
    private val strides: Strides = Strides(shape)
    final override val domains: List<IntRange> = shape.map { 0 until it }

    final override fun unsafeOffset(index: IntArray): Int = strides.offset0(index)
    final override fun unsafeOffset(i: Int): Int = strides.offset0(i)
    final override fun unsafeOffset(i: Int, j: Int): Int = strides.offset0(i, j)
    final override fun unsafeOffset(i: Int, j: Int, k: Int): Int = strides.offset0(i, j, k)
    final override fun unsafeIndex(offset: Int): IntArray = strides.index0(offset)
}

private class Strides(shape: IntArray) {
    private val strides: IntArray =
        if (shape.isEmpty())
            intArrayOf()
        else
            IntArray(shape.size).apply {
                this[lastIndex] = 1
                for (i in lastIndex - 1 downTo 0) {
                    this[i] = this[i + 1] * shape[i + 1]
                }
            }

    fun offset0(index0: IntArray): Int {
        return strides.foldIndexed(0) { i, acc, s -> acc + index0[i] * s }
    }

    fun offset0(i: Int): Int {
        return i * strides[0]
    }

    fun offset0(i: Int, j: Int): Int {
        return i * strides[0] + j * strides[1]
    }

    fun offset0(i: Int, j: Int, k: Int): Int {
        return i * strides[0] + j * strides[1] + k * strides[2]
    }

    fun offset1(index1: IntArray): Int {
        return strides.foldIndexed(0) { i, acc, s -> acc + (index1[i] - 1) * s }
    }

    fun offset1(i: Int): Int {
        return (i - 1) * strides[0]
    }

    fun offset1(i: Int, j: Int): Int {
        return (i - 1) * strides[0] + (j - 1) * strides[1]
    }

    fun offset1(i: Int, j: Int, k: Int): Int {
        return (i - 1) * strides[0] + (j - 1) * strides[1] + (k - 1) * strides[2]
    }

    fun index0(offset: Int): IntArray {
        val result = IntArray(strides.size)
        var current = offset
        for ((i, s) in strides.withIndex()) {
            result[i] = current / s // 0-based
            current %= s
            if (current == 0) break
        }
        return result
    }

    fun index1(offset: Int): IntArray {
        val result = IntArray(strides.size) { 1 }
        var current = offset
        for ((i, s) in strides.withIndex()) {
            result[i] = current / s + 1 // 1-based
            current %= s
            if (current == 0) break
        }
        return result
    }
}
