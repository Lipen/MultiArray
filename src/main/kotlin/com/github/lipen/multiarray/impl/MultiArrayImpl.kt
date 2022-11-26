package com.github.lipen.multiarray.impl

import com.github.lipen.multiarray.BooleanMultiArray
import com.github.lipen.multiarray.IntMultiArray
import com.github.lipen.multiarray.MultiArray
import com.github.lipen.multiarray.MutableMultiArray
import com.github.lipen.multiarray.Shape

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
