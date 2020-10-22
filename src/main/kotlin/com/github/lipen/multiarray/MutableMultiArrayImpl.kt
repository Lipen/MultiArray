package com.github.lipen.multiarray

import com.github.lipen.multiarray.internal.Mutate
import com.github.lipen.multiarray.internal.Offset

private class MutableMultiArrayImpl<T>(
    values: List<T>,
    shape: IntArray,
    mutateDelegate: Mutate<T>
) : AbstractMutableMultiArray<T>(shape, values, mutateDelegate) {
    override fun toString(): String {
        return "MultiArray(shape = ${shape.asList()}, values = $values)"
    }
}

private fun <T> _createMutableMultiArray(
    values: List<T>,
    shape: IntArray,
    zerobased: Boolean,
    mutateForOffset: (Offset) -> Mutate<T>
): MutableMultiArray<T> {
    val offsetDelegate: Offset = Offset.from(shape, zerobased)
    val mutateDelegate: Mutate<T> = mutateForOffset(offsetDelegate)
    return MutableMultiArrayImpl(values, shape, mutateDelegate)
}

internal fun <T> _createMutableMultiArray(
    data: MutableList<T>,
    shape: IntArray,
    zerobased: Boolean
): MutableMultiArray<T> =
    _createMutableMultiArray(data, shape, zerobased) { Mutate.from(data, it) }

internal fun <T> _createMutableMultiArray(
    data: Array<T>,
    shape: IntArray,
    zerobased: Boolean
): MutableMultiArray<T> =
    _createMutableMultiArray(data.asList(), shape, zerobased) { Mutate.from(data, it) }

internal fun _createMutableMultiArray(
    data: IntArray,
    shape: IntArray,
    zerobased: Boolean
): MutableMultiArray<Int> =
    _createMutableMultiArray(data.asList(), shape, zerobased) { Mutate.from(data, it) }

internal fun _createMutableMultiArray(
    data: BooleanArray,
    shape: IntArray,
    zerobased: Boolean
): MutableMultiArray<Boolean> =
    _createMutableMultiArray(data.asList(), shape, zerobased) { Mutate.from(data, it) }
