package com.github.lipen.multiarray.internal

internal interface WithDims {
    val dims: Int
}

internal class WithDimsImpl(
    shape: IntArray
) : WithDims {
    override val dims: Int = shape.size
}
