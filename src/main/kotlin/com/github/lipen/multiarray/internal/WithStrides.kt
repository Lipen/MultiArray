package com.github.lipen.multiarray.internal

internal interface WithStrides {
    val strides: Strides
}

internal class WithStridesImpl(
    shape: IntArray
) : WithStrides {
    override val strides: Strides = Strides(shape)
}
