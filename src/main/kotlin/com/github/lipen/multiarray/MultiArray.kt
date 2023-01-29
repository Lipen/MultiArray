@file:Suppress("FunctionName")

package com.github.lipen.multiarray

typealias IntMultiArray = MultiArray<Int>
typealias BooleanMultiArray = MultiArray<Boolean>

interface MultiArray<out T> {
    val values: List<T>
    val shape: Shape
    val domains: List<IntRange>
    val indices: Sequence<Index>
    val indicesReversed: Sequence<Index>

    operator fun get(index: Index): T
    operator fun get(i: Int): T
    operator fun get(i: Int, j: Int): T
    operator fun get(i: Int, j: Int, k: Int): T

    companion object Factory {
        //region ===[ Smart constructors ]===

        inline fun <reified T> newUninitialized(
            shape: Shape,
            zerobased: Boolean = false,
        ): MultiArray<T> = MutableMultiArray.newUninitialized(shape, zerobased)

        inline fun <reified T> newUninitialized(
            vararg shape: Int,
            zerobased: Boolean = false,
        ): MultiArray<T> = newUninitialized(Shape(shape), zerobased)

        inline fun <reified T> new(
            shape: Shape,
            zerobased: Boolean = false,
            init: (Index) -> T,
        ): MultiArray<T> = MutableMultiArray.new(shape, zerobased, init)

        inline fun <reified T> new(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (Index) -> T,
        ): MultiArray<T> = new(Shape(shape), zerobased, init)

        //endregion

        //region ===[ Generic constructors ]===

        inline fun <reified T> newGenericUninitialized(
            shape: Shape,
            zerobased: Boolean = false,
        ): MultiArray<T> = MutableMultiArray.newGenericUninitialized(shape, zerobased)

        inline fun <reified T> newGenericUninitialized(
            vararg shape: Int,
            zerobased: Boolean = false,
        ): MultiArray<T> = newGenericUninitialized(Shape(shape), zerobased)

        inline fun <reified T> newGeneric(
            shape: Shape,
            zerobased: Boolean = false,
            init: (Index) -> T,
        ): MultiArray<T> = MutableMultiArray.newGeneric(shape, zerobased, init)

        inline fun <reified T> newGeneric(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (Index) -> T,
        ): MultiArray<T> = newGeneric(Shape(shape), zerobased, init)

        //endregion

        //region ===[ Int constructors ]===

        fun newInt(
            shape: Shape,
            zerobased: Boolean = false,
        ): IntMultiArray = MutableMultiArray.newInt(shape, zerobased)

        fun newInt(
            vararg shape: Int,
            zerobased: Boolean = false,
        ): IntMultiArray = newInt(Shape(shape), zerobased)

        inline fun newInt(
            shape: Shape,
            zerobased: Boolean = false,
            init: (Index) -> Int,
        ): IntMultiArray = MutableMultiArray.newInt(shape, zerobased, init)

        inline fun newInt(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (Index) -> Int,
        ): IntMultiArray = newInt(Shape(shape), zerobased, init)

        //endregion

        //region ===[ Boolean constructors ]===

        fun newBoolean(
            shape: Shape,
            zerobased: Boolean = false,
        ): BooleanMultiArray = MutableMultiArray.newBoolean(shape, zerobased)

        fun newBoolean(
            vararg shape: Int,
            zerobased: Boolean = false,
        ): BooleanMultiArray = newBoolean(Shape(shape), zerobased)

        inline fun newBoolean(
            shape: Shape,
            zerobased: Boolean = false,
            init: (Index) -> Boolean,
        ): BooleanMultiArray = MutableMultiArray.newBoolean(shape, zerobased, init)

        inline fun newBoolean(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (Index) -> Boolean,
        ): BooleanMultiArray = newBoolean(Shape(shape), zerobased, init)

        //endregion
    }
}
