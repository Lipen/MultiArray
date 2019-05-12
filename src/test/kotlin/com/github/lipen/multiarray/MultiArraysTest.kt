package com.github.lipen.multiarray

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MultiArraysTest {

    // MultiArray tests

    @Test
    fun `create empty MultiArray of Strings`() {
        val array: MultiArray<String> = MultiArray.create { "" }
        assertEquals(0, array.size)
        assertTrue(array.shape.isEmpty())
        assertTrue(array.values.isEmpty())
    }

    @Test
    fun `create filled MultiArray of Strings`() {
        val array: MultiArray<String> =
            MultiArray.create(2, 3) { (i, j) -> "($i,$j)" }
        assertEquals(6, array.size)
        assertArrayEquals(intArrayOf(2, 3), array.shape)
        assertEquals(listOf("(1,1)", "(1,2)", "(1,3)", "(2,1)", "(2,2)", "(2,3)"), array.values.toList())
    }

    // IntMultiArray tests

    @Test
    fun `create empty IntMultiArray`() {
        val array = IntMultiArray.create()
        assertEquals(0, array.size)
        assertTrue(array.shape.isEmpty())
        assertTrue(array.values.isEmpty())
    }

    @Test
    fun `create filled IntMultiArray via array factory method`() {
        val array = IntMultiArray.create(intArrayOf(6, 2, 4)) { (i, j, k) -> i + j + k }
        assertEquals(48, array.size)
        assertArrayEquals(intArrayOf(6, 2, 4), array.shape)
        assertEquals(sequence {
            for (i in 1..6) for (j in 1..2) for (k in 1..4)
                yield(i + j + k)
        }.toList(), array.values.toList())
    }

    @Test
    fun `create filled IntMultiArray via vararg factory method`() {
        val array: IntMultiArray =
            IntMultiArray.create(3, 2) { (i, j) -> i + j }
        assertEquals(6, array.size)
        assertArrayEquals(intArrayOf(3, 2), array.shape)
        assertEquals(listOf(2, 3, 3, 4, 4, 5), array.values.toList())
    }

    // BooleanMultiArray tests

    @Test
    fun `create empty BooleanMultiArray`() {
        val array = BooleanMultiArray.create()
        assertTrue(array.shape.isEmpty())
        assertTrue(array.values.isEmpty())
    }

    @Test
    fun `create filled BooleanMultiArray via array factory method`() {
        val array: BooleanMultiArray =
            BooleanMultiArray.create(intArrayOf(6, 2, 4)) { true }
        assertArrayEquals(intArrayOf(6, 2, 4), array.shape)
        array.values.forEach { assertTrue(it) }
    }

    @Test
    fun `create filled BooleanMultiArray via vararg factory method`() {
        val array: BooleanMultiArray =
            BooleanMultiArray.create(4, 2) { (i, j) -> i == j }
        assertArrayEquals(intArrayOf(4, 2), array.shape)
        @Suppress("BooleanLiteralArgument")
        assertEquals(listOf(true, false, false, true, false, false, false, false), array.values.toList())
    }
}
