package com.github.lipen.multiarray.impl

import com.github.lipen.multiarray.BooleanMultiArray
import com.github.lipen.multiarray.IntMultiArray
import com.github.lipen.multiarray.MultiArray
import com.github.lipen.multiarray.MutableMultiArray

fun <T> MultiArray.Factory.from(
    data: Array<T>,
    shape: IntArray,
    zerobased: Boolean = false,
): MultiArray<T> = MutableMultiArray.from(data, shape, zerobased)

fun MultiArray.Factory.from(
    data: IntArray,
    shape: IntArray,
    zerobased: Boolean = false,
): IntMultiArray = MutableMultiArray.from(data, shape, zerobased)

fun MultiArray.Factory.from(
    data: BooleanArray,
    shape: IntArray,
    zerobased: Boolean = false,
): BooleanMultiArray = MutableMultiArray.from(data, shape, zerobased)
