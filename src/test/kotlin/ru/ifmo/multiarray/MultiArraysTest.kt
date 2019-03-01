package ru.ifmo.multiarray

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MultiArraysTest {

    // MultiArray tests

    @Test
    fun `create empty MultiArray of Strings`() {
        val array = MultiArray.new<String> { "" }
        assertEquals(emptyList<Int>(), array.shape.asList())
    }

    @Test
    fun `create filled MultiArray of Strings`() {
        val array = MultiArray.new<String>(2, 3) { (i, j) -> "($i,$j)" }
        assertEquals(listOf(2, 3), array.shape.asList())
        assertEquals(listOf("(1,1)", "(1,2)", "(1,3)", "(2,1)", "(2,2)", "(2,3)"), array.values)
    }

    // IntMultiArray tests

    @Test
    fun `create empty IntMultiArray`() {
        val array = IntMultiArray.new()
        assertEquals(emptyList<Int>(), array.shape.asList())
    }

    @Test
    fun `create filled IntMultiArray via vararg ctor`() {
        val array = IntMultiArray.new(3, 5, 4) { 42 }
        assertEquals(listOf(3, 5, 4), array.shape.asList())
    }

    @Test
    fun `create filled IntMultiArray via array ctor`() {
        val shape = intArrayOf(6, 2, 4)
        val array = IntMultiArray(shape) { 42 }
        assertEquals(shape.toList(), array.shape.asList())
    }

    @Test
    fun `create filled IntMultiArray via vararg factory method`() {
        val init: (IntArray) -> Int = { (i, j) -> i + j }
        val array: IntMultiArray = MultiArray.new(3, 2, init = init)
        assertEquals(listOf(3, 2), array.shape.asList())
        assertEquals(listOf(2, 3, 3, 4, 4, 5), array.values)
    }

    // BooleanMultiArray tests

    @Test
    fun `create empty BooleanMultiArray`() {
        val array = BooleanMultiArray.new()
        assertEquals(emptyList<Int>(), array.shape.asList())
    }

    @Test
    fun `create filled BooleanMultiArray via vararg ctor`() {
        val array = BooleanMultiArray.new(3, 5, 4) { true }
        assertEquals(listOf(3, 5, 4), array.shape.asList())
    }

    @Test
    fun `create filled BooleanMultiArray via array ctor`() {
        val shape = intArrayOf(6, 2, 4)
        val array = BooleanMultiArray(shape) { true }
        assertEquals(shape, array.shape)
        array.values.forEach { assertTrue(it) }
    }

    @Test
    fun `create filled BooleanMultiArray via vararg factory method`() {
        val init: (IntArray) -> Boolean = { (i, j) -> i == j }
        val array: BooleanMultiArray = MultiArray.new(4, 2, init = init)
        assertEquals(listOf(4, 2), array.shape.asList())
        @Suppress("BooleanLiteralArgument")
        assertEquals(listOf(true, false, false, true, false, false, false, false), array.values)
    }
}
