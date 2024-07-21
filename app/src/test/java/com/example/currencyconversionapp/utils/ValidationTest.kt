package com.example.currencyconversionapp.utils

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ValidationTest {

    @Test
    fun testValidInteger() {
        assertTrue(checkNumberFormat("123"))
    }

    @Test
    fun testValidDouble() {
        assertTrue(checkNumberFormat("123.456"))
    }

    @Test
    fun testInvalidFormat() {
        assertFalse(checkNumberFormat("123abc"))
        assertFalse(checkNumberFormat("abc123"))
        assertFalse(checkNumberFormat("123.45.67"))
        assertFalse(checkNumberFormat(""))
    }

    @Test
    fun testEdgeCases() {
        assertTrue(checkNumberFormat("0"))
        assertTrue(checkNumberFormat("0.0"))
    }
}