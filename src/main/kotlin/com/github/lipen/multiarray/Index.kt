package com.github.lipen.multiarray

// typealias Index = IntArray

@JvmInline
value class Index(val inner: IntArray) {
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
}

// vararg-constructor
fun Index(vararg index: Int): Index = Index(index)
