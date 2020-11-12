@file:Suppress("FunctionName")

package com.github.lipen.multiarray

fun <T> MultiArray<T>.getOrDefault(vararg index: Int, defaultValue: T): T = getOrDefault_(index, defaultValue)
fun <T> MultiArray<T>.getOrDefault_(index: IntArray, defaultValue: T): T =
    if (index in indices) getAt(index) else defaultValue

fun <T> MultiArray<T>.getOrNull(vararg index: Int): T? = getOrNull_(index)
fun <T> MultiArray<T>.getOrNull_(index: IntArray): T? =
    if (index in indices) getAt(index) else null

inline fun <T> MultiArray<T>.getOrElse(vararg index: Int, defaultValue: () -> T): T = getOrElse_(index, defaultValue)
inline fun <T> MultiArray<T>.getOrElse_(index: IntArray, defaultValue: () -> T): T =
    if (index in indices) getAt(index) else defaultValue()

inline fun <T, reified R> MultiArray<T>.map(transform: (T) -> R): MultiArray<R> =
    MultiArray.new_(shape) { index -> transform(getAt(index)) }

inline fun <T, reified R> MultiArray<T>.mapIndexed(transform: (IntArray, T) -> R): MultiArray<R> =
    MultiArray.new_(shape) { index -> transform(index, getAt(index)) }

fun <T> MultiArray<T>.withIndex(): Sequence<Pair<IntArray, T>> =
    indices.zip(values.asSequence())

fun <T> MultiArray<T>.withIndexReversed(): Sequence<Pair<IntArray, T>> =
    indicesReversed.zip(values.asReversed().asSequence())

@PublishedApi
internal val indexNotFound: IntArray = intArrayOf()

fun <T : Any> MultiArray<T>.indexOf(element: T): IntArray = indexOfFirst { it == element }

inline fun <T : Any> MultiArray<T>.indexOfFirst(predicate: (T) -> Boolean): IntArray =
    withIndex().firstOrNull { (_, item) -> predicate(item) }?.first ?: indexNotFound

inline fun <T : Any> MultiArray<T>.indexOfLast(predicate: (T) -> Boolean): IntArray =
    withIndex().lastOrNull { (_, item) -> predicate(item) }?.first ?: indexNotFound

inline fun <T, R> MultiArray<T>.foldIndexed(
    initial: R,
    operation: (index: IntArray, acc: R, elem: T) -> R
): R {
    var accumulator = initial
    for ((index, element) in withIndex())
        accumulator = operation(index, accumulator, element)
    return accumulator
}

inline fun <T, R> MultiArray<T>.foldRightIndexed(
    initial: R,
    operation: (index: IntArray, elem: T, acc: R) -> R
): R {
    var accumulator = initial
    for ((index, element) in withIndexReversed())
        accumulator = operation(index, element, accumulator)
    return accumulator
}

fun <T> MultiArray<T>.forEachIndexed(action: (IntArray, T) -> Unit) {
    withIndex().forEach { (index, value) -> action(index, value) }
}
