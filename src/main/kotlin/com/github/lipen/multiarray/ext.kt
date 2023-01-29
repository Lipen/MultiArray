package com.github.lipen.multiarray

//region ===[ getting ]===

operator fun <T> MultiArray<T>.get(vararg index: Int): T = get(Index(index))

fun <T> MultiArray<T>.getOrDefault(index: Index, defaultValue: T): T =
    if (index in indices) get(index) else defaultValue

@JvmName("getOrDefaultVararg")
fun <T> MultiArray<T>.getOrDefault(vararg index: Int, defaultValue: T): T =
    getOrDefault(Index(index), defaultValue)

fun <T> MultiArray<T>.getOrNull(index: Index): T? =
    if (index in indices) get(index) else null

@JvmName("getOrNullVararg")
fun <T> MultiArray<T>.getOrNull(vararg index: Int): T? =
    getOrNull(Index(index))

inline fun <T> MultiArray<T>.getOrElse(
    index: Index,
    defaultValue: () -> T,
): T = if (index in indices) get(index) else defaultValue()

@JvmName("getOrElseVararg")
inline fun <T> MultiArray<T>.getOrElse(
    vararg index: Int,
    defaultValue: () -> T,
): T = getOrElse(Index(index), defaultValue)

//endregion

//region ===[ mapping ]===

inline fun <T, reified R> MultiArray<T>.map(transform: (T) -> R): MultiArray<R> =
    MultiArray.new(shape) { index -> transform(get(index)) }

inline fun <T, reified R> MultiArray<T>.mapIndexed(transform: (Index, T) -> R): MultiArray<R> =
    MultiArray.new(shape) { index -> transform(index, get(index)) }

inline fun <T, reified R> MultiArray<T>.mapToMut(transform: (T) -> R): MutableMultiArray<R> =
    MutableMultiArray.new(shape) { index -> transform(get(index)) }

inline fun <T, reified R> MultiArray<T>.mapIndexedToMut(transform: (Index, T) -> R): MutableMultiArray<R> =
    MutableMultiArray.new(shape) { index -> transform(index, get(index)) }

//endregion

//region ===[ indexing ]===

fun <T> MultiArray<T>.withIndex(): Sequence<Pair<Index, T>> =
    indices.zip(values.asSequence())

fun <T> MultiArray<T>.withIndexReversed(): Sequence<Pair<Index, T>> =
    indicesReversed.zip(values.asReversed().asSequence())

fun <T> MultiArray<T>.indexOf(element: T): Index? =
    indexOfFirst { it == element }

inline fun <T> MultiArray<T>.indexOfFirst(predicate: (T) -> Boolean): Index? =
    withIndex().firstOrNull { (_, item) -> predicate(item) }?.first

inline fun <T> MultiArray<T>.indexOfLast(predicate: (T) -> Boolean): Index? =
    withIndex().lastOrNull { (_, item) -> predicate(item) }?.first

//endregion

//region ===[ folding ]===

inline fun <T, R> MultiArray<T>.foldIndexed(
    initial: R,
    operation: (index: Index, acc: R, elem: T) -> R,
): R {
    var accumulator = initial
    for ((index, element) in withIndex()) {
        accumulator = operation(index, accumulator, element)
    }
    return accumulator
}

inline fun <T, R> MultiArray<T>.foldRightIndexed(
    initial: R,
    operation: (index: Index, elem: T, acc: R) -> R,
): R {
    var accumulator = initial
    for ((index, element) in withIndexReversed()) {
        accumulator = operation(index, element, accumulator)
    }
    return accumulator
}

//endregion

//region ===[ iterating ]===

inline fun <T> MultiArray<T>.forEachIndexed(action: (Index, T) -> Unit) {
    withIndex().forEach { (index, value) -> action(index, value) }
}

//endregion

//region ===[ other extensions ]===

fun <T> MultiArray<T>.asMut(): MutableMultiArray<T> = when (this) {
    is MutableMultiArray<T> -> this
    else -> error("This MultiArray cannot be converted to MutableMultiArray")
}

inline fun <reified T> MultiArray<T>.toMut(zerobased: Boolean = false): MutableMultiArray<T> =
    MutableMultiArray.from(values.toTypedArray(), shape, zerobased)

//endregion
