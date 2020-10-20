@file:Suppress("FunctionName")

package com.github.lipen.multiarray

interface MultiArray<out T> {
    val values: List<T>
    val shape: IntArray
    val dims: Int
    val indices: Set<IntArray>
    val domains: List<IntRange>

    fun getAt(index: IntArray): T
    operator fun get(i: Int): T
    operator fun get(i: Int, j: Int): T
    operator fun get(i: Int, j: Int, k: Int): T
    operator fun get(vararg index: Int): T
}

typealias IntMultiArray = MultiArray<Int>
typealias BooleanMultiArray = MultiArray<Boolean>

// internal class MultiArrayImpl<out T>(
//     delegate: MutableMultiArray<T>
// ): MultiArray<T> by delegate {
//     override fun toString(): String {
//         return "MultiArray(shape = ${shape.asList()}, values = $values)"
//     }
// }
//
// fun <T> MutableMultiArray<T>.toImmutable() : MultiArray<T> = MultiArrayImpl(this)

/* Smart */

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

/* Generic */

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

/* Int */

inline fun newIntMultiArray(
    shape: IntArray,
    zerobased: Boolean = false,
    init: (IntArray) -> Int = { 0 }
): IntMultiArray = newMutableIntMultiArray(shape, zerobased, init)

@JvmName("newIntMultiArrayVararg")
inline fun newIntMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (IntArray) -> Int = { 0 }
): IntMultiArray = newIntMultiArray(shape, zerobased, init)

/* Boolean */

inline fun newBooleanMultiArray(
    shape: IntArray,
    zerobased: Boolean = false,
    init: (IntArray) -> Boolean = { false }
): BooleanMultiArray = newMutableBooleanMultiArray(shape, zerobased, init)

@JvmName("newBooleanMultiArrayVararg")
inline fun newBooleanMultiArray(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (IntArray) -> Boolean = { false }
): BooleanMultiArray = newBooleanMultiArray(shape, zerobased, init)
