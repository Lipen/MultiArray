package com.github.lipen.multiarray.internal

internal interface WithValues<out T> {
    val values: List<T>
}

internal class WithValuesImpl<out T>(
    override val values: List<T>
) : WithValues<T>
