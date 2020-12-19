@file:Suppress("FunctionName")

package com.github.lipen.multiarray

typealias IntMultiArray = MultiArray<Int>
typealias BooleanMultiArray = MultiArray<Boolean>

interface MultiArray<out T> {
    val values: List<T>
    val shape: IntArray
    val domains: List<IntRange>
    val indices: Sequence<Index>
    val indicesReversed: Sequence<Index>

    fun getAt(index: Index): T
    operator fun get(i: Int): T
    operator fun get(i: Int, j: Int): T
    operator fun get(i: Int, j: Int, k: Int): T
    operator fun get(vararg index: Int): T

    fun asMut(): MutableMultiArray<@UnsafeVariance T> =
        error("This MultiArray cannot be converted to MutableMultiArray")

    companion object Factory {
        //region ===[ Smart constructors ]===

        inline fun <reified T : Any> newUninitializedNotNull(
            shape: IntArray,
            zerobased: Boolean = false,
        ): MultiArray<T> = MutableMultiArray.newUninitializedNotNull(shape, zerobased)

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
            init: (Index) -> T,
        ): MultiArray<T> = MutableMultiArray.new(shape, zerobased, init)

        @JvmName("newVararg")
        inline fun <reified T> new(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (Index) -> T,
        ): MultiArray<T> = new(shape, zerobased, init)

        //endregion

        //region ===[ Generic constructors ]===

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
            init: (Index) -> T,
        ): MultiArray<T> = MutableMultiArray.newGeneric(shape, zerobased, init)

        @JvmName("newGenericVararg")
        inline fun <reified T> newGeneric(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (Index) -> T,
        ): MultiArray<T> = newGeneric(shape, zerobased, init)

        //endregion

        //region ===[ Int constructors ]===

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
            init: (Index) -> Int,
        ): IntMultiArray = MutableMultiArray.newInt(shape, zerobased, init)

        @JvmName("newIntVararg")
        inline fun newInt(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (Index) -> Int,
        ): IntMultiArray = newInt(shape, zerobased, init)

        //endregion

        //region ===[ Boolean constructors ]===

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
            init: (Index) -> Boolean,
        ): BooleanMultiArray = MutableMultiArray.newBoolean(shape, zerobased, init)

        @JvmName("newBooleanVararg")
        inline fun newBoolean(
            vararg shape: Int,
            zerobased: Boolean = false,
            init: (Index) -> Boolean,
        ): BooleanMultiArray = newBoolean(shape, zerobased, init)

        //endregion
    }
}
