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

        inline fun <reified T> newUninitialized(
            shape: IntArray,
            zerobased: Boolean = false,
        ): MultiArray<T> = MutableMultiArray.newUninitialized(shape, zerobased)

        @JvmName("newUninitializedVararg")
        inline fun <reified T> newUninitialized(
            vararg shape: Int,
            zerobased: Boolean = false,
        ): MultiArray<T> = newUninitialized(shape, zerobased)

        inline fun <reified T> new(
            shape: IntArray,
            zerobased: Boolean = false,
            init: (IntArray) -> T,
        ): MultiArray<T> = MutableMultiArray.new(shape, zerobased, init)

        @JvmName("newVararg")
        inline fun <reified T> new(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (IntArray) -> T,
        ): MultiArray<T> = new(shape, zerobased, init)

        // Generic

        fun <T> from(
            data: Array<T>,
            shape: IntArray,
            zerobased: Boolean = false,
        ): MultiArray<T> = MutableMultiArray.from(data, shape, zerobased)

        inline fun <reified T> newGenericUninitialized(
            shape: IntArray,
            zerobased: Boolean = false,
        ): MultiArray<T> = MutableMultiArray.newGenericUninitialized(shape, zerobased)

        @JvmName("newGenericUninitializedVararg")
        inline fun <reified T> newGenericUninitialized(
            vararg shape: Int,
            zerobased: Boolean = false,
        ): MultiArray<T> = newGenericUninitialized(shape, zerobased)

        inline fun <reified T> newGeneric(
            shape: IntArray,
            zerobased: Boolean = false,
            init: (IntArray) -> T,
        ): MultiArray<T> = MutableMultiArray.newGeneric(shape, zerobased, init)

        @JvmName("newGenericVararg")
        inline fun <reified T> newGeneric(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (IntArray) -> T,
        ): MultiArray<T> = newGeneric(shape, zerobased, init)

        // Int

        fun from(
            data: IntArray,
            shape: IntArray,
            zerobased: Boolean = false,
        ): IntMultiArray = MutableMultiArray.from(data, shape, zerobased)

        fun newInt(
            shape: IntArray,
            zerobased: Boolean = false,
        ): IntMultiArray = MutableMultiArray.newInt(shape, zerobased)

        @JvmName("newIntVararg")
        fun newInt(
            vararg shape: Int,
            zerobased: Boolean = false,
        ): IntMultiArray = newInt(shape, zerobased)

        inline fun newInt(
            shape: IntArray,
            zerobased: Boolean = false,
            init: (IntArray) -> Int,
        ): IntMultiArray = MutableMultiArray.newInt(shape, zerobased, init)

        @JvmName("newIntVararg")
        inline fun newInt(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (IntArray) -> Int,
        ): IntMultiArray = newInt(shape, zerobased, init)

        // Boolean

        fun from(
            data: BooleanArray,
            shape: IntArray,
            zerobased: Boolean = false,
        ): BooleanMultiArray = MutableMultiArray.from(data, shape, zerobased)

        fun newBoolean(
            shape: IntArray,
            zerobased: Boolean = false,
        ): BooleanMultiArray = MutableMultiArray.newBoolean(shape, zerobased)

        @JvmName("newBooleanVararg")
        fun newBoolean(
            vararg shape: Int,
            zerobased: Boolean = false,
        ): BooleanMultiArray = newBoolean(shape, zerobased)

        inline fun newBoolean(
            shape: IntArray,
            zerobased: Boolean = false,
            init: (IntArray) -> Boolean,
        ): BooleanMultiArray = MutableMultiArray.newBoolean(shape, zerobased, init)

        @JvmName("newBooleanVararg")
        inline fun newBoolean(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (IntArray) -> Boolean,
        ): BooleanMultiArray = newBoolean(shape, zerobased, init)
    }
}
