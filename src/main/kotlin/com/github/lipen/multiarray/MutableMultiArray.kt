package com.github.lipen.multiarray

import com.github.lipen.multiarray.internal.reduceIfNotEmpty

interface MutableMultiArray<T> : MultiArray<T> {
    fun setAt(index: IntArray, value: T)
    operator fun set(i: Int, value: T)
    operator fun set(i: Int, j: Int, value: T)
    operator fun set(i: Int, j: Int, k: Int, value: T)
    operator fun set(vararg index: Int, value: T): Unit = setAt(index, value)
}

/// Smart

@Suppress("UNCHECKED_CAST")
inline fun <reified T> newUninitializedMutableMultiArray(
    shape: IntArray,
    zerobased: Boolean = false
): MutableMultiArray<T> = when (T::class) {
    Int::class -> newMutableIntMultiArray(shape, zerobased) as MutableMultiArray<T>
    Boolean::class -> newMutableBooleanMultiArray(shape, zerobased) as MutableMultiArray<T>
    else -> newUninitializedMutableGenericMultiArray(shape, zerobased)
}

@JvmName("newUninitializedMutableMultiArrayVararg")
inline fun <reified T> newUninitializedMutableMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false
): MutableMultiArray<T> = newUninitializedMutableMultiArray(shape, zerobased)

@Suppress("UNCHECKED_CAST")
inline fun <reified T> newMutableMultiArray(
    shape: IntArray,
    zerobased: Boolean = false,
    init: (IntArray) -> T
): MutableMultiArray<T> = newUninitializedMutableMultiArray<T>(shape, zerobased).filledBy(init)

@JvmName("newMutableMultiArrayVararg")
inline fun <reified T> newMutableMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (IntArray) -> T
): MutableMultiArray<T> = newMutableMultiArray(shape, zerobased, init)

/// Generic

fun <T> _newMutableGenericMultiArray(
    data: Array<T>,
    shape: IntArray,
    zerobased: Boolean = false
): MutableMultiArray<T> = _createMutableMultiArray(data, shape, zerobased)

inline fun <reified T> newUninitializedMutableGenericMultiArray(
    shape: IntArray,
    zerobased: Boolean = false
): MutableMultiArray<T> {
    val size = shape.reduceIfNotEmpty()

    @Suppress("UNCHECKED_CAST")
    val data = arrayOfNulls<T>(size) as Array<T>

    return _newMutableGenericMultiArray(data, shape, zerobased)
}

@JvmName("newUninitializedMutableGenericMultiArrayVararg")
inline fun <reified T> newUninitializedMutableGenericMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false
): MutableMultiArray<T> = newUninitializedMutableGenericMultiArray(shape, zerobased)

inline fun <reified T> newMutableGenericMultiArray(
    shape: IntArray,
    zerobased: Boolean = false,
    init: (IntArray) -> T
): MutableMultiArray<T> = newUninitializedMutableGenericMultiArray<T>(shape, zerobased).filledBy(init)

@JvmName("newMutableGenericMultiArrayVararg")
inline fun <reified T> newMutableGenericMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (IntArray) -> T
): MutableMultiArray<T> = newMutableGenericMultiArray(shape, zerobased, init)

/// Int

typealias MutableIntMultiArray = MutableMultiArray<Int>

fun _newMutableIntMultiArray(
    data: IntArray,
    shape: IntArray,
    zerobased: Boolean
): MutableIntMultiArray =
    _createMutableMultiArray(data, shape, zerobased)

fun newMutableIntMultiArray(
    shape: IntArray,
    zerobased: Boolean = false
): MutableIntMultiArray {
    val size = shape.reduceIfNotEmpty()
    val data = IntArray(size)
    return _newMutableIntMultiArray(data, shape, zerobased)
}

@JvmName("newMutableIntMultiArrayVararg")
fun newMutableIntMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false
): MutableIntMultiArray = newMutableIntMultiArray(shape, zerobased)

inline fun newMutableIntMultiArray(
    shape: IntArray,
    zerobased: Boolean = false,
    init: (IntArray) -> Int
): MutableIntMultiArray = newMutableIntMultiArray(shape, zerobased).filledBy(init)

@JvmName("newMutableIntMultiArrayVararg")
inline fun newMutableIntMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (IntArray) -> Int
): MutableIntMultiArray = newMutableIntMultiArray(shape, zerobased, init)

/// Boolean

typealias MutableBooleanMultiArray = MutableMultiArray<Boolean>

fun _newMutableBooleanMultiArray(
    data: BooleanArray,
    shape: IntArray,
    zerobased: Boolean
): MutableBooleanMultiArray =
    _createMutableMultiArray(data, shape, zerobased)

fun newMutableBooleanMultiArray(
    shape: IntArray,
    zerobased: Boolean = false
): MutableBooleanMultiArray {
    val size = shape.reduceIfNotEmpty()
    val data = BooleanArray(size)
    return _newMutableBooleanMultiArray(data, shape, zerobased)
}

@JvmName("newMutableBooleanMultiArrayVararg")
fun newMutableBooleanMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false
): MutableBooleanMultiArray = newMutableBooleanMultiArray(shape, zerobased)

inline fun newMutableBooleanMultiArray(
    shape: IntArray,
    zerobased: Boolean = false,
    init: (IntArray) -> Boolean
): MutableBooleanMultiArray = newMutableBooleanMultiArray(shape, zerobased).filledBy(init)

@JvmName("newMutableBooleanMultiArrayVararg")
inline fun newMutableBooleanMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (IntArray) -> Boolean
): MutableBooleanMultiArray = newMutableBooleanMultiArray(shape, zerobased, init)
