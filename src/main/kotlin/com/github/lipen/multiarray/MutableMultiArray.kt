@file:Suppress("FunctionName")

package com.github.lipen.multiarray

import com.github.lipen.multiarray.internal.WithGet
import com.github.lipen.multiarray.internal.WithGetImpl
import com.github.lipen.multiarray.internal.WithOffset
import com.github.lipen.multiarray.internal.WithOffsetImpl0
import com.github.lipen.multiarray.internal.WithOffsetImpl1
import com.github.lipen.multiarray.internal.WithSet
import com.github.lipen.multiarray.internal.WithSetImplArray
import com.github.lipen.multiarray.internal.WithSetImplBooleanArray
import com.github.lipen.multiarray.internal.WithSetImplIntArray
import com.github.lipen.multiarray.internal.WithStrides
import com.github.lipen.multiarray.internal.WithStridesImpl
import com.github.lipen.multiarray.internal.WithValues
import com.github.lipen.multiarray.internal.WithValuesImpl

interface MutableMultiArray<T> : MultiArray<T> {
    fun setAt(index: IntArray, value: T)
    operator fun set(i: Int, value: T)
    operator fun set(i: Int, j: Int, value: T)
    operator fun set(i: Int, j: Int, k: Int, value: T)
    operator fun set(vararg index: Int, value: T)
}

typealias MutableIntMultiArray = MutableMultiArray<Int>
typealias MutableBooleanMultiArray = MutableMultiArray<Boolean>

internal class MutableMultiArrayImpl<T>(
    override val shape: IntArray,
    withValues: WithValues<T>,
    withOffset: WithOffset,
    withGet: WithGet<T>,
    withSet: WithSet<T>
) : MutableMultiArray<T>,
    WithValues<T> by withValues,
    WithOffset by withOffset,
    WithGet<T> by withGet,
    WithSet<T> by withSet {

    override val dims: Int = shape.size
    override val indices: Set<IntArray> by lazy {
        values.indices.map { unsafeIndex(it) }.toSet()
    }

    override fun toString(): String {
        return "MultiArray(shape = ${shape.asList()}, values = $values)"
    }
}

private fun <T> _createMutableMultiArray(
    values: List<T>,
    shape: IntArray,
    zerobased: Boolean,
    withSetFromWithOffset: (WithOffset) -> WithSet<T>
): MutableMultiArray<T> {
    val withStrides: WithStrides = WithStridesImpl(shape)
    val withOffset: WithOffset = if (zerobased) {
        WithOffsetImpl0(shape, /*withValues,*/ withStrides)
    } else {
        WithOffsetImpl1(shape, /*withValues,*/ withStrides)
    }
    val withValues: WithValues<T> = WithValuesImpl(values)
    val withGet: WithGet<T> = WithGetImpl(withValues, withOffset)
    val withSet: WithSet<T> = withSetFromWithOffset(withOffset)
    return MutableMultiArrayImpl(shape, withValues, withOffset, withGet, withSet)
}

/* Smart */

@Suppress("UNCHECKED_CAST")
inline fun <reified T> newMutableMultiArray(
    shape: IntArray,
    zerobased: Boolean = false,
    init: (IntArray) -> T
): MutableMultiArray<T> = when (T::class) {
    Int::class ->
        newMutableIntMultiArray(shape, zerobased) { init(it) as Int } as MutableMultiArray<T>
    Boolean::class ->
        newMutableBooleanMultiArray(shape, zerobased) { init(it) as Boolean } as MutableMultiArray<T>
    else ->
        newMutableGenericMultiArray(shape, zerobased, init)
}

@Suppress("UNCHECKED_CAST")
@JvmName("newMutableMultiArrayVararg")
inline fun <reified T> newMutableMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (IntArray) -> T
): MutableMultiArray<T> = newMutableMultiArray(shape, zerobased, init)

/* Generic */

fun <T> _newMutableGenericMultiArray(
    data: Array<T>,
    shape: IntArray,
    zerobased: Boolean = false
): MutableMultiArray<T> {
    return _createMutableMultiArray(data.asList(), shape, zerobased) { WithSetImplArray(data, it) }
}

inline fun <reified T> newMutableGenericMultiArray(
    shape: IntArray,
    zerobased: Boolean = false,
    init: (IntArray) -> T
): MutableMultiArray<T> {
    val size = shape.reduceIfNotEmpty()

    @Suppress("UNCHECKED_CAST")
    val data = arrayOfNulls<T>(size) as Array<T>

    return _newMutableGenericMultiArray(data, shape, zerobased).apply { fillBy(init) }
}

@JvmName("newMutableGenericMultiArrayVararg")
inline fun <reified T> newMutableGenericMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (IntArray) -> T
): MutableMultiArray<T> = newMutableGenericMultiArray(shape, zerobased, init)

/* Int */

fun _newMutableIntMultiArray(
    data: IntArray,
    shape: IntArray,
    zerobased: Boolean
): MutableIntMultiArray {
    return _createMutableMultiArray(data.asList(), shape, zerobased) { WithSetImplIntArray(data, it) }
}

inline fun newMutableIntMultiArray(
    shape: IntArray,
    zerobased: Boolean = false,
    init: (IntArray) -> Int
): MutableIntMultiArray {
    val size = shape.reduceIfNotEmpty()
    val data = IntArray(size)
    return _newMutableIntMultiArray(data, shape, zerobased).apply { fillBy(init) }
}

@JvmName("newMutableIntMultiArrayVararg")
inline fun newMutableIntMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (IntArray) -> Int
): MutableIntMultiArray = newMutableIntMultiArray(shape, zerobased, init)

/* Boolean */

fun _newMutableBooleanMultiArray(
    data: BooleanArray,
    shape: IntArray,
    zerobased: Boolean
): MutableBooleanMultiArray {
    return _createMutableMultiArray(data.asList(), shape, zerobased) { WithSetImplBooleanArray(data, it) }
}

inline fun newMutableBooleanMultiArray(
    shape: IntArray,
    zerobased: Boolean = false,
    init: (IntArray) -> Boolean
): MutableBooleanMultiArray {
    val size = shape.reduceIfNotEmpty()
    val data = BooleanArray(size)
    return _newMutableBooleanMultiArray(data, shape, zerobased).apply { fillBy(init) }
}

@JvmName("newMutableBooleanMultiArrayVararg")
inline fun newMutableBooleanMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (IntArray) -> Boolean
): MutableBooleanMultiArray = newMutableBooleanMultiArray(shape, zerobased, init)
