package com.github.lipen.multiarray

inline fun <T> MutableMultiArray<T>.fillBy(init: (IntArray) -> T) {
    for (index in indices)
        setAt(index, init(index))
}
