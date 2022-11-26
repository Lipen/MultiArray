package com.github.lipen.multiarray

//region ===[ setting ]===

operator fun <T> MutableMultiArray<T>.set(vararg index: Int, value: T) {
    setAt(Index(index), value)
}

//endregion

//region ===[ filling ]===

inline fun <T> MutableMultiArray<T>.fillBy(init: (Index) -> T) {
    for (index in indices) {
        setAt(index, init(index))
    }
}

inline fun <M : MutableMultiArray<T>, T> M.filledBy(init: (Index) -> T): M = apply { fillBy(init) }

inline fun <T> MutableMultiArray<T>.mapInPlace(transform: (Index, T) -> T) {
    for ((index, value) in withIndex()) {
        setAt(index, transform(index, value))
    }
}

//endregion

//region ===[ mapping ]===

inline fun <T, reified R> MutableMultiArray<T>.map(transform: (T) -> R): MutableMultiArray<R> =
    mapToMut(transform)

inline fun <T, reified R> MutableMultiArray<T>.mapIndexed(transform: (Index, T) -> R): MutableMultiArray<R> =
    mapIndexedToMut(transform)

//endregion
