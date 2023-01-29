@file:Suppress("FunctionName")

package com.github.lipen.multiarray

import com.github.lipen.genikos.GenericArray

typealias MutableIntMultiArray = MutableMultiArray<Int>
typealias MutableBooleanMultiArray = MutableMultiArray<Boolean>

interface MutableMultiArray<T> : MultiArray<T> {
    operator fun set(index: Index, value: T)
    operator fun set(i: Int, value: T)
    operator fun set(i: Int, j: Int, value: T)
    operator fun set(i: Int, j: Int, k: Int, value: T)

    companion object Factory {
        //region ===[ Smart constructors ]===

        inline fun <reified T> newUninitialized(
            shape: Shape,
            zerobased: Boolean = false,
        ): MutableMultiArray<T> {
            val size = shape.productIfNotEmpty()
            val array = GenericArray.new<T>(size)
            return from(array, shape, zerobased)
        }

        inline fun <reified T> new(
            shape: Shape,
            zerobased: Boolean = false,
            init: (Index) -> T,
        ): MutableMultiArray<T> = newUninitialized<T>(shape, zerobased).filledBy(init)

        //endregion

        //region ===[ Generic constructors ]===

        inline fun <reified T> newGenericUninitialized(
            shape: Shape,
            zerobased: Boolean = false,
        ): MutableMultiArray<T> {
            val size = shape.productIfNotEmpty()

            @Suppress("UNCHECKED_CAST")
            val data = arrayOfNulls<T>(size) as Array<T>

            return from(data, shape, zerobased)
        }

        inline fun <reified T> newGeneric(
            shape: Shape,
            zerobased: Boolean = false,
            init: (Index) -> T,
        ): MutableMultiArray<T> = newGenericUninitialized<T>(shape, zerobased).filledBy(init)

        //endregion

        //region ===[ Int constructors ]===

        fun newInt(
            shape: Shape,
            zerobased: Boolean = false,
        ): MutableIntMultiArray {
            val size = shape.productIfNotEmpty()
            val data = IntArray(size)
            return from(data, shape, zerobased)
        }

        inline fun newInt(
            shape: Shape,
            zerobased: Boolean = false,
            init: (Index) -> Int,
        ): MutableIntMultiArray = newInt(shape, zerobased).filledBy(init)

        //endregion

        //region ===[ Boolean constructors ]===

        fun newBoolean(
            shape: Shape,
            zerobased: Boolean = false,
        ): MutableBooleanMultiArray {
            val size = shape.productIfNotEmpty()
            val data = BooleanArray(size)
            return from(data, shape, zerobased)
        }

        inline fun newBoolean(
            shape: Shape,
            zerobased: Boolean = false,
            init: (Index) -> Boolean,
        ): MutableBooleanMultiArray = newBoolean(shape, zerobased).filledBy(init)

        //endregion
    }
}

inline fun <reified T> MutableMultiArray.Factory.newUninitialized(
    vararg shape: Int,
    zerobased: Boolean = false,
): MutableMultiArray<T> = newUninitialized(Shape(shape), zerobased)

inline fun <reified T> MutableMultiArray.Factory.new(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (Index) -> T,
): MutableMultiArray<T> = new(Shape(shape), zerobased, init)

inline fun <reified T> MutableMultiArray.Factory.newGenericUninitialized(
    vararg shape: Int,
    zerobased: Boolean = false,
): MutableMultiArray<T> = newGenericUninitialized(Shape(shape), zerobased)

inline fun <reified T> MutableMultiArray.Factory.newGeneric(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (Index) -> T,
): MutableMultiArray<T> = newGeneric(Shape(shape), zerobased, init)

fun MutableMultiArray.Factory.newInt(
    vararg shape: Int,
    zerobased: Boolean = false,
): MutableIntMultiArray = newInt(Shape(shape), zerobased)

inline fun MutableMultiArray.Factory.newInt(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (Index) -> Int,
): MutableIntMultiArray = newInt(Shape(shape), zerobased, init)

fun MutableMultiArray.Factory.newBoolean(
    vararg shape: Int,
    zerobased: Boolean = false,
): MutableBooleanMultiArray = newBoolean(Shape(shape), zerobased)

inline fun MutableMultiArray.Factory.newBoolean(
    vararg shape: Int,
    zerobased: Boolean = false,
    init: (Index) -> Boolean,
): MutableBooleanMultiArray = newBoolean(Shape(shape), zerobased, init)
