package com.github.lipen.multiarray

import com.github.lipen.genikos.GenericArray

// typealias Index = IntArray

@JvmInline
value class Index(val inner: IntArray) : GenericArray<Int> {
    override val data: List<Int> get() = inner.asList()
    override val size: Int get() = inner.size

    override operator fun get(index: Int): Int {
        return inner[index]
    }

    override operator fun set(index: Int, value: Int) {
        inner[index] = value
    }

    override fun iterator(): Iterator<Int> {
        return inner.iterator()
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
}

// vararg-constructor
fun Index(vararg index: Int): Index = Index(index)
