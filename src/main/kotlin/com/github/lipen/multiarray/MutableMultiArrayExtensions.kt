package com.github.lipen.multiarray

//region ===[ filling ]===

inline fun <T> MutableMultiArray<T>.fillBy(init: (IntArray) -> T) {
    for (index in indices) {
        setAt(index, init(index))
    }
}

inline fun <M : MutableMultiArray<T>, T> M.filledBy(init: (IntArray) -> T): M = apply { fillBy(init) }

inline fun <T> MutableMultiArray<T>.mapInPlace(transform: (IntArray, T) -> T) {
    for ((index, value) in withIndex()) {
        setAt(index, transform(index, value))
    }
}

//endregion

//region ===[ mapping ]===

inline fun <T, reified R> MutableMultiArray<T>.map(transform: (T) -> R): MutableMultiArray<R> =
    mapToMut(transform)

inline fun <T, reified R> MutableMultiArray<T>.mapIndexed(transform: (IntArray, T) -> R): MutableMultiArray<R> =
    mapIndexedToMut(transform)

//endregion
