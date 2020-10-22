@file:Suppress("FunctionName")

package com.github.lipen.multiarray

typealias IntMultiArray = MultiArray<Int>
typealias BooleanMultiArray = MultiArray<Boolean>

interface MultiArray<out T> {
    val values: List<T>
    val shape: IntArray
    val domains: List<IntRange>
    val indices: Sequence<IntArray>
    val indicesReversed: Sequence<IntArray>

    fun getAt(index: IntArray): T
    operator fun get(i: Int): T
    operator fun get(i: Int, j: Int): T
    operator fun get(i: Int, j: Int, k: Int): T
    operator fun get(vararg index: Int): T

    fun asMut(): MutableMultiArray<@UnsafeVariance T> =
        error("This MultiArray cannot be converted to MutableMultiArray")

    companion object Factory {
        // Smart

        inline fun <reified T> newUninitialized_(
            shape: IntArray,
            zerobased: Boolean = false
        ): MultiArray<T> = MutableMultiArray.newUninitialized_(shape, zerobased)

        inline fun <reified T> newUninitialized(
            vararg shape: Int,
            zerobased: Boolean = false
        ): MultiArray<T> = newUninitialized_(shape, zerobased)

        inline fun <reified T> new_(
            shape: IntArray,
            zerobased: Boolean = false,
            init: (IntArray) -> T
        ): MultiArray<T> = MutableMultiArray.new(shape, zerobased, init)

        inline fun <reified T> new(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (IntArray) -> T
        ): MultiArray<T> = new_(shape, zerobased, init)

        // Generic

        fun <T> from(
            data: Array<T>,
            shape: IntArray,
            zerobased: Boolean = false
        ): MultiArray<T> = MutableMultiArray.from(data, shape, zerobased)

        inline fun <reified T> newGenericUninitialized_(
            shape: IntArray,
            zerobased: Boolean = false
        ): MultiArray<T> = MutableMultiArray.newGenericUninitialized_(shape, zerobased)

        inline fun <reified T> newGenericUninitialized(
            vararg shape: Int,
            zerobased: Boolean = false
        ): MultiArray<T> = newGenericUninitialized_(shape, zerobased)

        inline fun <reified T> newGeneric_(
            shape: IntArray,
            zerobased: Boolean = false,
            init: (IntArray) -> T
        ): MultiArray<T> = MutableMultiArray.newGeneric_(shape, zerobased, init)

        inline fun <reified T> newGeneric(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (IntArray) -> T
        ): MultiArray<T> = newGeneric_(shape, zerobased, init)

        // Int

        fun from(
            data: IntArray,
            shape: IntArray,
            zerobased: Boolean = false
        ): IntMultiArray = MutableMultiArray.from(data, shape, zerobased)

        fun newInt_(
            shape: IntArray,
            zerobased: Boolean = false
        ): IntMultiArray = MutableMultiArray.newInt_(shape, zerobased)

        fun newInt(
            vararg shape: Int,
            zerobased: Boolean = false
        ): IntMultiArray = newInt_(shape, zerobased)

        inline fun newInt_(
            shape: IntArray,
            zerobased: Boolean = false,
            init: (IntArray) -> Int
        ): IntMultiArray = MutableMultiArray.newInt_(shape, zerobased, init)

        inline fun newInt(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (IntArray) -> Int
        ): IntMultiArray = newInt_(shape, zerobased, init)

        // Boolean

        fun from(
            data: BooleanArray,
            shape: IntArray,
            zerobased: Boolean = false
        ): BooleanMultiArray = MutableMultiArray.from(data, shape, zerobased)

        fun newBoolean_(
            shape: IntArray,
            zerobased: Boolean = false
        ): BooleanMultiArray = MutableMultiArray.newBoolean_(shape, zerobased)

        fun newBoolean(
            vararg shape: Int,
            zerobased: Boolean = false
        ): BooleanMultiArray = newBoolean_(shape, zerobased)

        inline fun newBoolean_(
            shape: IntArray,
            zerobased: Boolean = false,
            init: (IntArray) -> Boolean
        ): BooleanMultiArray = MutableMultiArray.newBoolean_(shape, zerobased, init)

        inline fun newBoolean(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (IntArray) -> Boolean
        ): BooleanMultiArray = newBoolean_(shape, zerobased, init)
    }
}
