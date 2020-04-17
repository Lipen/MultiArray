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

inline fun <T> MultiArray<T>.fillBy(init: (IntArray) -> T) {
    for (index in indices)
        setAt(index, init(index))
}

inline fun <T, reified R> MultiArray<T>.map(transform: (T) -> R): MultiArray<R> =
    MultiArray.create(shape) { index -> transform(getAt(index)) }

// Note: Implementation could be just `map(transform) as IntMultiArray`,
// because `MultiArray.create` smartly dispatches over input type `T`,
// and produces necessary `IntMultiArray` when `T` is `Int`.
inline fun <T> MultiArray<T>.mapToInt(transform: (T) -> Int): IntMultiArray =
    IntMultiArray.create(shape) { index -> transform(getAt(index)) }

inline fun <T> MultiArray<T>.mapToBoolean(transform: (T) -> Boolean): BooleanMultiArray =
    BooleanMultiArray.create(shape) { index -> transform(getAt(index)) }

inline fun <T, reified R> MultiArray<T>.mapIndexed(transform: (IntArray, T) -> R): MultiArray<R> =
    MultiArray.create(shape) { index -> transform(index, getAt(index)) }

inline fun <T> MultiArray<T>.mapIndexedToInt(transform: (IntArray, T) -> Int): IntMultiArray =
    IntMultiArray.create(shape) { index -> transform(index, getAt(index)) }

inline fun <T> MultiArray<T>.mapIndexedToBoolean(transform: (IntArray, T) -> Boolean): BooleanMultiArray =
    BooleanMultiArray.create(shape) { index -> transform(index, getAt(index)) }

fun <T> MultiArray<T>.withIndex(): Sequence<Pair<IntArray, T>> =
    indices.asSequence().zip(values.asSequence())

fun <T> MultiArray<T>.withIndexReversed(): Sequence<Pair<IntArray, T>> =
    indices.zip(values).asReversed().asSequence()

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
