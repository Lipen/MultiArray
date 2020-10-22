package com.github.lipen.multiarray

interface MultiArray<out T> {
    val values: List<T>
    val shape: IntArray
    val domains: List<IntRange>
    val indices: Set<IntArray>

    fun getAt(index: IntArray): T
    operator fun get(i: Int): T
    operator fun get(i: Int, j: Int): T
    operator fun get(i: Int, j: Int, k: Int): T
    operator fun get(vararg index: Int): T

    fun asMut(): MutableMultiArray<@UnsafeVariance T> =
        error("This MultiArray cannot be converted to MutableMultiArray")
}

/// Smart

inline fun <reified T> newUninitializedMultiArray(
    shape: IntArray,
    zerobased: Boolean = false
): MultiArray<T> = newUninitializedMutableMultiArray(shape, zerobased)

@JvmName("newUninitializedMultiArrayVararg")
inline fun <reified T> newUninitializedMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false
): MultiArray<T> = newUninitializedMultiArray(shape, zerobased)

inline fun <reified T> newMultiArray(
    shape: IntArray,
    zerobased: Boolean = false,
    init: (IntArray) -> T
): MultiArray<T> = newMutableMultiArray(shape, zerobased, init)

@JvmName("newMultiArrayVararg")
inline fun <reified T> newMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (IntArray) -> T
): MultiArray<T> = newMultiArray(shape, zerobased, init)

/// Generic

inline fun <reified T> newUninitializedGenericMultiArray(
    shape: IntArray,
    zerobased: Boolean = false
): MultiArray<T> = newUninitializedMutableGenericMultiArray(shape, zerobased)

@JvmName("newGenericMultiArrayVararg")
inline fun <reified T> newUninitializedGenericMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false
): MultiArray<T> = newUninitializedGenericMultiArray(shape, zerobased)

inline fun <reified T> newGenericMultiArray(
    shape: IntArray,
    zerobased: Boolean = false,
    init: (IntArray) -> T
): MultiArray<T> = newMutableGenericMultiArray(shape, zerobased, init)

@JvmName("newGenericMultiArrayVararg")
inline fun <reified T> newGenericMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (IntArray) -> T
): MultiArray<T> = newGenericMultiArray(shape, zerobased, init)

/// Int

typealias IntMultiArray = MultiArray<Int>

fun newIntMultiArray(
    shape: IntArray,
    zerobased: Boolean = false
): IntMultiArray = newMutableIntMultiArray(shape, zerobased)

@JvmName("newIntMultiArrayVararg")
fun newIntMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false
): IntMultiArray = newIntMultiArray(shape, zerobased)

inline fun newIntMultiArray(
    shape: IntArray,
    zerobased: Boolean = false,
    init: (IntArray) -> Int
): IntMultiArray = newMutableIntMultiArray(shape, zerobased, init)

@JvmName("newIntMultiArrayVararg")
inline fun newIntMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (IntArray) -> Int
): IntMultiArray = newIntMultiArray(shape, zerobased, init)

/// Boolean

typealias BooleanMultiArray = MultiArray<Boolean>

fun newBooleanMultiArray(
    shape: IntArray,
    zerobased: Boolean = false
): BooleanMultiArray = newMutableBooleanMultiArray(shape, zerobased)

@JvmName("newBooleanMultiArrayVararg")
fun newBooleanMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false
): BooleanMultiArray = newBooleanMultiArray(shape, zerobased)

inline fun newBooleanMultiArray(
    shape: IntArray,
    zerobased: Boolean = false,
    init: (IntArray) -> Boolean
): BooleanMultiArray = newMutableBooleanMultiArray(shape, zerobased, init)

@JvmName("newBooleanMultiArrayVararg")
inline fun newBooleanMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (IntArray) -> Boolean
): BooleanMultiArray = newBooleanMultiArray(shape, zerobased, init)
