package com.github.lipen.multiarray

import com.github.lipen.multiarray.internal.Mutate
import com.github.lipen.multiarray.internal.Offset
import com.github.lipen.multiarray.internal.OffsetImpl0
import com.github.lipen.multiarray.internal.OffsetImpl1

private class MutableMultiArrayImpl<T>(
    values: List<T>,
    shape: IntArray,
    mutateDelegate: Mutate<T>
) : AbstractMutableMultiArray<T>(shape, values, mutateDelegate) {
    override fun toString(): String {
        return "MultiArray(shape = ${shape.asList()}, values = $values)"
    }
}

internal fun <T> _createMutableMultiArray(
    values: List<T>,
    shape: IntArray,
    zerobased: Boolean,
    mutateForOffset: (Offset) -> Mutate<T>
): MutableMultiArray<T> {
    val offsetDelegate: Offset = if (zerobased) {
        OffsetImpl0(shape)
    } else {
        OffsetImpl1(shape)
    }
    val mutateDelegate: Mutate<T> = mutateForOffset(offsetDelegate)
    return MutableMultiArrayImpl(values, shape, mutateDelegate)
}
