package com.github.lipen.multiarray.internal

@PublishedApi
internal fun IntArray.reduceIfNotEmpty(default: Int = 0): Int =
    if (isNotEmpty()) reduce(Int::times) else default
