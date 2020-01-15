package com.github.lipen.multiarray
/**
 * Multidimensional array inspired by [kmath](https://github.com/altavir/kmath)
 * and [victor](https://github.com/JetBrains-Research/viktor).
 *
 * @param[T] Element type.
 */
interface MultiArray<T> {
    val values: List<T>
    val shape: IntArray
    val dims: Int
    val indices: Sequence<IntArray>
    val domains: List<IntRange>

    fun getAt(index: IntArray): T
    operator fun get(i: Int): T
    operator fun get(i: Int, j: Int): T
    operator fun get(i: Int, j: Int, k: Int): T

    fun setAt(index: IntArray, value: T)
    operator fun set(i: Int, value: T)
    operator fun set(i: Int, j: Int, value: T)
    operator fun set(i: Int, j: Int, k: Int, value: T)

    operator fun get(vararg index: Int): T = getAt(index)
    operator fun set(vararg index: Int, value: T): Unit = setAt(index, value)

    companion object {
        /**
         * Smart constructor. Resulting `MultiArray<T>` is backed by `IntMultiArray` if [T] is `Int`,
         * by `BooleanMultiArray` if [T] is `Boolean`, and by `GenericMultiArray<T>` otherwise.
         */
        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        inline fun <reified T> create(
            shape: IntArray,
            noinline init: (IntArray) -> T
        ): MultiArray<T> = when (T::class) {
            Int::class -> IntMultiArray.create(shape, init as (IntArray) -> Int) as MultiArray<T>
            Boolean::class -> BooleanMultiArray.create(shape, init as (IntArray) -> Boolean) as MultiArray<T>
            else -> GenericMultiArray.create(shape, init)
        }

        @JvmName("createVararg")
        inline fun <reified T> create(
            vararg shape: Int,
            noinline init: (IntArray) -> T
        ): MultiArray<T> = create(shape, init)
    }
}

fun <T> MultiArray<T>.fillBy(init: (IntArray) -> T) {
    for (index in indices) {
        setAt(index, init(index))
    }
}

inline fun <T, reified R> MultiArray<T>.mapValues(crossinline transform: (T) -> R): MultiArray<R> =
    MultiArray.create(shape) { index -> transform(getAt(index)) }

inline fun <T> MultiArray<T>.mapValuesToInt(crossinline transform: (T) -> Int): IntMultiArray =
    IntMultiArray.create(shape) { index -> transform(getAt(index)) }

inline fun <T> MultiArray<T>.mapValuesToBoolean(crossinline transform: (T) -> Boolean): BooleanMultiArray =
    BooleanMultiArray.create(shape) { index -> transform(getAt(index)) }
