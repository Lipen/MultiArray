@file:Suppress("FunctionName")

package com.github.lipen.multiarray

import com.github.lipen.multiarray.internal.reduceIfNotEmpty

typealias MutableIntMultiArray = MutableMultiArray<Int>
typealias MutableBooleanMultiArray = MutableMultiArray<Boolean>

interface MutableMultiArray<T> : MultiArray<T> {
    fun setAt(index: IntArray, value: T)
    operator fun set(i: Int, value: T)
    operator fun set(i: Int, j: Int, value: T)
    operator fun set(i: Int, j: Int, k: Int, value: T)
    operator fun set(vararg index: Int, value: T): Unit = setAt(index, value)

    companion object Factory {
        // Smart

        @Suppress("UNCHECKED_CAST")
        inline fun <reified T> newUninitialized_(
            shape: IntArray,
            zerobased: Boolean = false
        ): MutableMultiArray<T> = when (T::class) {
            Int::class -> newInt_(shape, zerobased) as MutableMultiArray<T>
            Boolean::class -> newBoolean_(shape, zerobased) as MutableMultiArray<T>
            else -> newGenericUninitialized_(shape, zerobased)
        }

        inline fun <reified T> newUninitialized(
            vararg shape: Int,
            zerobased: Boolean = false
        ): MutableMultiArray<T> = newUninitialized_(shape, zerobased)

        @Suppress("UNCHECKED_CAST")
        inline fun <reified T> new(
            shape: IntArray,
            zerobased: Boolean = false,
            init: (IntArray) -> T
        ): MutableMultiArray<T> = newUninitialized_<T>(shape, zerobased).filledBy(init)

        @JvmName("newVararg")
        inline fun <reified T> new(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (IntArray) -> T
        ): MutableMultiArray<T> = new(shape, zerobased, init)

        // Generic

        fun <T> from(
            data: Array<T>,
            shape: IntArray,
            zerobased: Boolean = false
        ): MutableMultiArray<T> = _createMutableMultiArray(data, shape, zerobased)

        inline fun <reified T> newGenericUninitialized_(
            shape: IntArray,
            zerobased: Boolean = false
        ): MutableMultiArray<T> {
            val size = shape.reduceIfNotEmpty()

            @Suppress("UNCHECKED_CAST")
            val data = arrayOfNulls<T>(size) as Array<T>

            return from(data, shape, zerobased)
        }

        inline fun <reified T> newGenericUninitialized(
            vararg shape: Int,
            zerobased: Boolean = false
        ): MutableMultiArray<T> = newGenericUninitialized_(shape, zerobased)

        inline fun <reified T> newGeneric_(
            shape: IntArray,
            zerobased: Boolean = false,
            init: (IntArray) -> T
        ): MutableMultiArray<T> = newGenericUninitialized_<T>(shape, zerobased).filledBy(init)

        inline fun <reified T> newGeneric(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (IntArray) -> T
        ): MutableMultiArray<T> = newGeneric_(shape, zerobased, init)

        // Int

        fun from(
            data: IntArray,
            shape: IntArray,
            zerobased: Boolean
        ): MutableIntMultiArray = _createMutableMultiArray(data, shape, zerobased)

        fun newInt_(
            shape: IntArray,
            zerobased: Boolean = false
        ): MutableIntMultiArray {
            val size = shape.reduceIfNotEmpty()
            val data = IntArray(size)
            return from(data, shape, zerobased)
        }

        fun newInt(
            vararg shape: Int,
            zerobased: Boolean = false
        ): MutableIntMultiArray = newInt_(shape, zerobased)

        inline fun newInt_(
            shape: IntArray,
            zerobased: Boolean = false,
            init: (IntArray) -> Int
        ): MutableIntMultiArray = newInt_(shape, zerobased).filledBy(init)

        inline fun newInt(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (IntArray) -> Int
        ): MutableIntMultiArray = newInt_(shape, zerobased, init)

        // Boolean

        fun from(
            data: BooleanArray,
            shape: IntArray,
            zerobased: Boolean
        ): MutableBooleanMultiArray = _createMutableMultiArray(data, shape, zerobased)

        fun newBoolean_(
            shape: IntArray,
            zerobased: Boolean = false
        ): MutableBooleanMultiArray {
            val size = shape.reduceIfNotEmpty()
            val data = BooleanArray(size)
            return from(data, shape, zerobased)
        }

        fun newBoolean(
            vararg shape: Int,
            zerobased: Boolean = false
        ): MutableBooleanMultiArray = newBoolean_(shape, zerobased)

        inline fun newBoolean_(
            shape: IntArray,
            zerobased: Boolean = false,
            init: (IntArray) -> Boolean
        ): MutableBooleanMultiArray = newBoolean_(shape, zerobased).filledBy(init)

        inline fun newBoolean(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (IntArray) -> Boolean
        ): MutableBooleanMultiArray = newBoolean_(shape, zerobased, init)
    }
}
