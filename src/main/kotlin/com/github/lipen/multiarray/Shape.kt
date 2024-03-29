package com.github.lipen.multiarray

// typealias Shape = IntArray

@JvmInline
value class Shape(val inner: IntArray) {
    val size: Int get() = inner.size

    operator fun get(index: Int): Int {
        return inner[index]
    }

    operator fun set(index: Int, value: Int) {
        inner[index] = value
    }

    operator fun component1(): Int = get(0)
    operator fun component2(): Int = get(1)
    operator fun component3(): Int = get(2)
    operator fun component4(): Int = get(3)
    operator fun component5(): Int = get(4)
    operator fun component6(): Int = get(5)
    operator fun component7(): Int = get(6)
    operator fun component8(): Int = get(7)
    operator fun component9(): Int = get(8)

    override fun toString(): String {
        return inner.asList().toString()
    }

    companion object {
        // vararg-constructor
        operator fun invoke(vararg dimensions: Int): Shape = Shape(dimensions)
    }
}

@PublishedApi
internal fun Shape.productIfNotEmpty(default: Int = 0): Int =
    if (inner.isNotEmpty()) inner.reduce(Int::times) else default
