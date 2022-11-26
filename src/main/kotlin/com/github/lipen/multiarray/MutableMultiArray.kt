@file:Suppress("FunctionName")

package com.github.lipen.multiarray

import com.github.lipen.multiarray.impl.from
import kotlin.reflect.typeOf

typealias MutableIntMultiArray = MutableMultiArray<Int>
typealias MutableBooleanMultiArray = MutableMultiArray<Boolean>

interface MutableMultiArray<T> : MultiArray<T> {
    fun setAt(index: Index, value: T)
    operator fun set(i: Int, value: T)
    operator fun set(i: Int, j: Int, value: T)
    operator fun set(i: Int, j: Int, k: Int, value: T)
    operator fun set(vararg index: Int, value: T): Unit = setAt(Index(index), value)

    companion object Factory {
        //region ===[ Smart constructors ]===

        @Suppress("UNCHECKED_CAST")
        inline fun <reified T : Any> newUninitializedNotNull(
            shape: Shape,
            zerobased: Boolean = false,
        ): MutableMultiArray<T> = when (T::class) {
            Int::class -> newInt(shape, zerobased) as MutableMultiArray<T>
            Boolean::class -> newBoolean(shape, zerobased) as MutableMultiArray<T>
            else -> newGenericUninitialized(shape, zerobased)
        }

        @Suppress("UNCHECKED_CAST")
        inline fun <reified T> newUninitialized(
            shape: Shape,
            zerobased: Boolean = false,
        ): MutableMultiArray<T> = when (typeOf<T>()) {
            typeOf<Int>() -> newInt(shape, zerobased) as MutableMultiArray<T>
            typeOf<Boolean>() -> newBoolean(shape, zerobased) as MutableMultiArray<T>
            else -> newGenericUninitialized(shape, zerobased)
        }

        inline fun <reified T> newUninitialized(
            vararg shape: Int,
            zerobased: Boolean = false,
        ): MutableMultiArray<T> = newUninitialized(Shape(shape), zerobased)

        inline fun <reified T> new(
            shape: Shape,
            zerobased: Boolean = false,
            init: (Index) -> T,
        ): MutableMultiArray<T> = newUninitialized<T>(shape, zerobased).filledBy(init)

        inline fun <reified T> new(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (Index) -> T,
        ): MutableMultiArray<T> = new(Shape(shape), zerobased, init)

        //endregion

        //region ===[ Generic constructors ]===

        inline fun <reified T> newGenericUninitialized(
            shape: Shape,
            zerobased: Boolean = false,
        ): MutableMultiArray<T> {
            val size = shape.reduceIfNotEmpty()

            @Suppress("UNCHECKED_CAST")
            val data = arrayOfNulls<T>(size) as Array<T>

            return from(data, shape, zerobased)
        }

        inline fun <reified T> newGenericUninitialized(
            vararg shape: Int,
            zerobased: Boolean = false,
        ): MutableMultiArray<T> = newGenericUninitialized(Shape(shape), zerobased)

        inline fun <reified T> newGeneric(
            shape: Shape,
            zerobased: Boolean = false,
            init: (Index) -> T,
        ): MutableMultiArray<T> = newGenericUninitialized<T>(shape, zerobased).filledBy(init)

        inline fun <reified T> newGeneric(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (Index) -> T,
        ): MutableMultiArray<T> = newGeneric(Shape(shape), zerobased, init)

        //endregion

        //region ===[ Int constructors ]===

        fun newInt(
            shape: Shape,
            zerobased: Boolean = false,
        ): MutableIntMultiArray {
            val size = shape.reduceIfNotEmpty()
            val data = IntArray(size)
            return from(data, shape, zerobased)
        }

        fun newInt(
            vararg shape: Int,
            zerobased: Boolean = false,
        ): MutableIntMultiArray = newInt(Shape(shape), zerobased)

        inline fun newInt(
            shape: Shape,
            zerobased: Boolean = false,
            init: (Index) -> Int,
        ): MutableIntMultiArray = newInt(shape, zerobased).filledBy(init)

        inline fun newInt(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (Index) -> Int,
        ): MutableIntMultiArray = newInt(Shape(shape), zerobased, init)

        //endregion

        //region ===[ Boolean constructors ]===

        fun newBoolean(
            shape: Shape,
            zerobased: Boolean = false,
        ): MutableBooleanMultiArray {
            val size = shape.reduceIfNotEmpty()
            val data = BooleanArray(size)
            return from(data, shape, zerobased)
        }

        fun newBoolean(
            vararg shape: Int,
            zerobased: Boolean = false,
        ): MutableBooleanMultiArray = newBoolean(Shape(shape), zerobased)

        inline fun newBoolean(
            shape: Shape,
            zerobased: Boolean = false,
            init: (Index) -> Boolean,
        ): MutableBooleanMultiArray = newBoolean(shape, zerobased).filledBy(init)

        inline fun newBoolean(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (Index) -> Boolean,
        ): MutableBooleanMultiArray = newBoolean(Shape(shape), zerobased, init)

        //endregion
    }
}
