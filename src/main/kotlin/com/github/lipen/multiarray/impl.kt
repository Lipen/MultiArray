package com.github.lipen.multiarray

import com.github.lipen.genikos.GenericArray
import com.github.lipen.multiarray.internal.Offset

private class MutableMultiArrayImpl<T>(
    val array: GenericArray<T>,
    override val shape: Shape,
    val offset: Offset,
) : MutableMultiArray<T> {
    override val values: List<T> = array.data

    override val domains: List<IntRange> = offset.domains
    override val indices: Sequence<Index> =
        values.indices.asSequence().map { offset.unsafeIndex(it) }
    override val indicesReversed: Sequence<Index> =
        values.indices.reversed().asSequence().map { offset.unsafeIndex(it) }

    constructor(
        array: GenericArray<T>,
        shape: Shape,
        zerobased: Boolean,
    ) : this(array, shape, Offset.from(shape, zerobased))

    private fun _get(index: Int): T {
        return array[index]
    }

    private fun _set(index: Int, value: T) {
        array[index] = value
    }

    override fun getAt(index: Index): T = _get(offset.offset(index))
    override operator fun get(i: Int): T = _get(offset.offset(i))
    override operator fun get(i: Int, j: Int): T = _get(offset.offset(i, j))
    override operator fun get(i: Int, j: Int, k: Int): T = _get(offset.offset(i, j, k))

    override fun setAt(index: Index, value: T): Unit = _set(offset.offset(index), value)
    override operator fun set(i: Int, value: T): Unit = _set(offset.offset(i), value)
    override operator fun set(i: Int, j: Int, value: T): Unit = _set(offset.offset(i, j), value)
    override operator fun set(i: Int, j: Int, k: Int, value: T): Unit = _set(offset.offset(i, j, k), value)

    override fun toString(): String {
        return "MultiArray(shape = $shape, values = $values)"
    }
}

//region [MutableMultiArray]

fun <T> MutableMultiArray.Factory.from(
    data: GenericArray<T>,
    shape: Shape,
    zerobased: Boolean = false,
): MutableMultiArray<T> = MutableMultiArrayImpl(data, shape, zerobased)

fun <T> MutableMultiArray.Factory.from(
    data: MutableList<T>,
    shape: Shape,
    zerobased: Boolean = false,
): MutableMultiArray<T> = from(GenericArray.from(data), shape, zerobased)

fun <T> MutableMultiArray.Factory.from(
    data: Array<T>,
    shape: Shape,
    zerobased: Boolean = false,
): MutableMultiArray<T> = from(GenericArray.from(data), shape, zerobased)

fun MutableMultiArray.Factory.from(
    data: IntArray,
    shape: Shape,
    zerobased: Boolean = false,
): MutableIntMultiArray = from(GenericArray.from(data), shape, zerobased)

fun MutableMultiArray.Factory.from(
    data: BooleanArray,
    shape: Shape,
    zerobased: Boolean = false,
): MutableBooleanMultiArray = from(GenericArray.from(data), shape, zerobased)

//endregion

//region [MultiArray]

fun <T> MultiArray.Factory.from(
    data: GenericArray<out T>,
    shape: Shape,
    zerobased: Boolean = false,
): MultiArray<T> = MutableMultiArray.from(data, shape, zerobased)

fun <T> MultiArray.Factory.from(
    data: MutableList<T>,
    shape: Shape,
    zerobased: Boolean = false,
): MultiArray<T> = MutableMultiArray.from(data, shape, zerobased)

fun <T> MultiArray.Factory.from(
    data: Array<T>,
    shape: Shape,
    zerobased: Boolean = false,
): MultiArray<T> = MutableMultiArray.from(data, shape, zerobased)

fun MultiArray.Factory.from(
    data: IntArray,
    shape: Shape,
    zerobased: Boolean = false,
): IntMultiArray = MutableMultiArray.from(data, shape, zerobased)

fun MultiArray.Factory.from(
    data: BooleanArray,
    shape: Shape,
    zerobased: Boolean = false,
): BooleanMultiArray = MutableMultiArray.from(data, shape, zerobased)

//endregion
