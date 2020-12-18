package com.github.lipen.multiarray.internal

import com.github.lipen.multiarray.Index

interface Strides {
    val domains: List<IntRange>

    fun offset(index: Index): Int
    fun offset(i: Int): Int
    fun offset(i: Int, j: Int): Int
    fun offset(i: Int, j: Int, k: Int): Int
    fun index(offset: Int): Index

    companion object {
        fun from(shape: IntArray, zerobased: Boolean): Strides =
            if (zerobased) StridesImpl0(shape) else StridesImpl1(shape)
    }
}

abstract class AbstractStrides(shape: IntArray) : Strides {
    protected val strides: IntArray =
        if (shape.isEmpty()) {
            intArrayOf()
        } else {
            IntArray(shape.size).apply {
                this[lastIndex] = 1
                for (i in lastIndex - 1 downTo 0) {
                    this[i] = this[i + 1] * shape[i + 1]
                }
            }
        }
}

private class StridesImpl1(shape: IntArray) : AbstractStrides(shape) {
    override val domains: List<IntRange> = shape.map { 1..it }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun offset(index1: Index): Int {
        return strides.foldIndexed(0) { i, acc, s -> acc + (index1[i] - 1) * s }
    }

    override fun offset(i: Int): Int {
        return (i - 1) * strides[0]
    }

    override fun offset(i: Int, j: Int): Int {
        return (i - 1) * strides[0] + (j - 1) * strides[1]
    }

    override fun offset(i: Int, j: Int, k: Int): Int {
        return (i - 1) * strides[0] + (j - 1) * strides[1] + (k - 1) * strides[2]
    }

    override fun index(offset: Int): Index {
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

private class StridesImpl0(shape: IntArray) : AbstractStrides(shape) {
    override val domains: List<IntRange> = shape.map { 0 until it }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun offset(index0: Index): Int {
        return strides.foldIndexed(0) { i, acc, s -> acc + index0[i] * s }
    }

    override fun offset(i: Int): Int {
        return i * strides[0]
    }

    override fun offset(i: Int, j: Int): Int {
        return i * strides[0] + j * strides[1]
    }

    override fun offset(i: Int, j: Int, k: Int): Int {
        return i * strides[0] + j * strides[1] + k * strides[2]
    }

    override fun index(offset: Int): Index {
        val result = IntArray(strides.size)
        var current = offset
        for ((i, s) in strides.withIndex()) {
            result[i] = current / s // 0-based
            current %= s
            if (current == 0) break
        }
        return result
    }
}
