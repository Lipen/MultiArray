@file:Suppress("FunctionName")

package com.github.lipen.multiarray

//region ===[ getting ]===

fun <T> MultiArray<T>.getOrDefault(index: IntArray, defaultValue: T): T =
    if (index in indices) getAt(index) else defaultValue

@JvmName("getOrDefaultVararg")
fun <T> MultiArray<T>.getOrDefault(vararg index: Int, defaultValue: T): T =
    getOrDefault(index, defaultValue)

fun <T> MultiArray<T>.getOrNull(index: IntArray): T? =
    if (index in indices) getAt(index) else null

@JvmName("getOrNullVararg")
fun <T> MultiArray<T>.getOrNull(vararg index: Int): T? =
    getOrNull(index)

inline fun <T> MultiArray<T>.getOrElse(
    index: IntArray,
    defaultValue: () -> T,
): T = if (index in indices) getAt(index) else defaultValue()

@JvmName("getOrElseVararg")
inline fun <T> MultiArray<T>.getOrElse(vararg index: Int, defaultValue: () -> T): T =
    getOrElse(index, defaultValue)

//endregion

//region ===[ mapping ]===

inline fun <T, reified R> MultiArray<T>.map(transform: (T) -> R): MultiArray<R> =
    MultiArray.new(shape) { index -> transform(getAt(index)) }

inline fun <T, reified R> MultiArray<T>.mapIndexed(transform: (IntArray, T) -> R): MultiArray<R> =
    MultiArray.new(shape) { index -> transform(index, getAt(index)) }

inline fun <T, reified R> MultiArray<T>.mapToMut(transform: (T) -> R): MutableMultiArray<R> =
    MutableMultiArray.new(shape) { index -> transform(getAt(index)) }

inline fun <T, reified R> MultiArray<T>.mapIndexedToMut(transform: (IntArray, T) -> R): MutableMultiArray<R> =
    MutableMultiArray.new(shape) { index -> transform(index, getAt(index)) }

//endregion

//region ===[ indexing ]===

fun <T> MultiArray<T>.withIndex(): Sequence<Pair<IntArray, T>> =
    indices.zip(values.asSequence())

fun <T> MultiArray<T>.withIndexReversed(): Sequence<Pair<IntArray, T>> =
    indicesReversed.zip(values.asReversed().asSequence())

fun <T : Any> MultiArray<T>.indexOf(element: T): IntArray? =
    indexOfFirst { it == element }

inline fun <T : Any> MultiArray<T>.indexOfFirst(predicate: (T) -> Boolean): IntArray? =
    withIndex().firstOrNull { (_, item) -> predicate(item) }?.first

inline fun <T : Any> MultiArray<T>.indexOfLast(predicate: (T) -> Boolean): IntArray? =
    withIndex().lastOrNull { (_, item) -> predicate(item) }?.first

//endregion

//region ===[ folding ]===

inline fun <T, R> MultiArray<T>.foldIndexed(
    initial: R,
    operation: (index: IntArray, acc: R, elem: T) -> R,
): R {
    var accumulator = initial
    for ((index, element) in withIndex()) {
        accumulator = operation(index, accumulator, element)
    }
    return accumulator
}

inline fun <T, R> MultiArray<T>.foldRightIndexed(
    initial: R,
    operation: (index: IntArray, elem: T, acc: R) -> R,
): R {
    var accumulator = initial
    for ((index, element) in withIndexReversed()) {
        accumulator = operation(index, element, accumulator)
    }
    return accumulator
}

//endregion

//region ===[ iterating ]===

inline fun <T> MultiArray<T>.forEachIndexed(action: (IntArray, T) -> Unit) {
    withIndex().forEach { (index, value) -> action(index, value) }
}

//endregion
