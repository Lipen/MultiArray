package com.github.lipen.multiarray

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MultiArraysTest {

    // MultiArray tests

    @Test
    fun `create empty MultiArray of Strings`() {
        val array: MultiArray<String> = MultiArray.new { "" }
        assertEquals(0, array.values.size)
        assertTrue(array.shape.inner.isEmpty())
        assertTrue(array.values.isEmpty())
    }

    @Test
    fun `create filled MultiArray of Strings via array factory method`() {
        val array: MultiArray<String> = MultiArray.new(Shape(6, 2, 4)) { (i, j, k) -> "($i,$j,$k)" }
        assertEquals(48, array.values.size)
        assertArrayEquals(intArrayOf(6, 2, 4), array.shape.inner)
        assertEquals(
            sequence {
                for (i in 1..6) for (j in 1..2) for (k in 1..4)
                    yield("($i,$j,$k)")
            }.toList(),
            array.values
        )
    }

    @Test
    fun `create filled MultiArray of Strings via vararg factory method`() {
        val array: MultiArray<String> = MultiArray.new(2, 3) { (i, j) -> "($i,$j)" }
        assertEquals(6, array.values.size)
        assertArrayEquals(intArrayOf(2, 3), array.shape.inner)
        assertEquals(listOf("(1,1)", "(1,2)", "(1,3)", "(2,1)", "(2,2)", "(2,3)"), array.values)
    }

    @Test
    fun `mutate MutableMultiArray of Strings`() {
        val array: MutableMultiArray<String> = MutableMultiArray.new(2, 3) { (i, j) -> "$i-$j" }
        array[1, 1] = "first"
        array[1, 2] = "cat"
        array[2, 1] = "dog"
        array[2, 3] = "last"
        assertEquals(listOf("first", "cat", "1-3", "dog", "2-2", "last"), array.values)
    }

    // IntMultiArray tests

    @Test
    fun `create empty IntMultiArray`() {
        val array: IntMultiArray = MultiArray.newInt()
        assertEquals(0, array.values.size)
        assertTrue(array.shape.inner.isEmpty())
        assertTrue(array.values.isEmpty())
    }

    @Test
    fun `create filled IntMultiArray via array factory method`() {
        val array: IntMultiArray = MultiArray.newInt(Shape(6, 2, 4)) { (i, j, k) -> i + j + k }
        assertEquals(48, array.values.size)
        assertArrayEquals(intArrayOf(6, 2, 4), array.shape.inner)
        assertEquals(
            sequence {
                for (i in 1..6) for (j in 1..2) for (k in 1..4)
                    yield(i + j + k)
            }.toList(),
            array.values
        )
    }

    @Test
    fun `create filled IntMultiArray via vararg factory method`() {
        val array: IntMultiArray = MultiArray.newInt(3, 2) { (i, j) -> i + j }
        assertEquals(6, array.values.size)
        assertArrayEquals(intArrayOf(3, 2), array.shape.inner)
        assertEquals(listOf(2, 3, 3, 4, 4, 5), array.values)
    }

    @Test
    fun `mutate MutableIntMultiArray`() {
        val array: MutableIntMultiArray = MutableMultiArray.newInt(4, 2) { (i, j) -> i + j }
        array[1, 1] = 0
        array[1, 2] = 12
        array[2, 1] = 21
        array[4, 2] = 42
        assertEquals(listOf(0, 12, 21, 4, 4, 5, 5, 42), array.values)
    }

    // BooleanMultiArray tests

    @Test
    fun `create empty BooleanMultiArray`() {
        val array: BooleanMultiArray = MultiArray.newBoolean()
        assertTrue(array.shape.inner.isEmpty())
        assertTrue(array.values.isEmpty())
    }

    @Test
    fun `create filled BooleanMultiArray via array factory method`() {
        val array: BooleanMultiArray = MultiArray.newBoolean(Shape(6, 2, 4)) { true }
        assertArrayEquals(intArrayOf(6, 2, 4), array.shape.inner)
        array.values.forEach { assertTrue(it) }
    }

    @Test
    fun `create filled BooleanMultiArray via vararg factory method`() {
        val array: BooleanMultiArray = MultiArray.newBoolean(4, 2) { (i, j) -> i == j }
        assertArrayEquals(intArrayOf(4, 2), array.shape.inner)
        @Suppress("BooleanLiteralArgument")
        assertEquals(listOf(true, false, false, true, false, false, false, false), array.values)
    }

    @Test
    fun `mutate MutableBooleanMultiArray`() {
        val array: MutableBooleanMultiArray = MutableMultiArray.newBoolean(3, 2) { (i, j) -> i == j }
        array[1, 1] = false
        array[1, 2] = true
        array[2, 1] = true
        assertEquals(listOf(false, true, true, true, false, false), array.values)
    }
}
