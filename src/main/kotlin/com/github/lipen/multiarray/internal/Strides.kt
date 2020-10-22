package com.github.lipen.multiarray.internal

internal class Strides(shape: IntArray) {
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
