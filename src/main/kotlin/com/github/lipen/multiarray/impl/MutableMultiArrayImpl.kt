package com.github.lipen.multiarray.impl

import com.github.lipen.multiarray.MutableBooleanMultiArray
import com.github.lipen.multiarray.MutableIntMultiArray
import com.github.lipen.multiarray.MutableMultiArray
import com.github.lipen.multiarray.internal.Mutate
import com.github.lipen.multiarray.internal.Offset

private class MutableMultiArrayImpl<T>(
    values: List<T>,
    shape: IntArray,
    mutateDelegate: Mutate<T>,
) : AbstractMutableMultiArray<T>(shape, values, mutateDelegate)

private inline fun <T> _createMutableMultiArray(
    values: List<T>,
    shape: IntArray,
    zerobased: Boolean,
    mutateForOffset: (Offset) -> Mutate<T>,
): MutableMultiArray<T> {
    val offsetDelegate: Offset = Offset.from(shape, zerobased)
    val mutateDelegate: Mutate<T> = mutateForOffset(offsetDelegate)
    return MutableMultiArrayImpl(values, shape, mutateDelegate)
}

fun <T> _createMutableMultiArray(
    data: MutableList<T>,
    shape: IntArray,
    zerobased: Boolean,
): MutableMultiArray<T> {
    return _createMutableMultiArray(data, shape, zerobased) { Mutate.from(data, it) }
}

fun <T> _createMutableMultiArray(
    data: Array<T>,
    shape: IntArray,
    zerobased: Boolean,
): MutableMultiArray<T> {
    return _createMutableMultiArray(data.asList(), shape, zerobased) { Mutate.from(data, it) }
}

fun _createMutableMultiArray(
    data: IntArray,
    shape: IntArray,
    zerobased: Boolean,
): MutableIntMultiArray {
    return _createMutableMultiArray(data.asList(), shape, zerobased) { Mutate.from(data, it) }
}

fun _createMutableMultiArray(
    data: BooleanArray,
    shape: IntArray,
    zerobased: Boolean,
): MutableBooleanMultiArray {
    return _createMutableMultiArray(data.asList(), shape, zerobased) { Mutate.from(data, it) }
}
