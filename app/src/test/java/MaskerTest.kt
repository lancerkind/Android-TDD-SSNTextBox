package com.example.texteditbasicactivity

import mywidgets.Masker
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class MaskerTest {
    private lateinit var masker : Masker

    @Before
    fun setup() {
        masker = Masker()
    }

    @Test
    fun computeMask_firstDigitInput()    {
        // Arrange, Act, and Assert
        assertEquals("xx-xx-xxx", masker.computeMaskForInputCase("xxx-xx-xxx"))
    }

    @Test
    fun computeMask_secondDigit() {
        // Arrange
        val currentMask = "xx-xx-xxx"

        // Act and Assert
        assertEquals("x-xx-xxx", masker.computeMaskForInputCase(currentMask))
    }

    @Test
    fun computeMask_thirdDigit() {
        // Arrange, Act, and Assert
        assertEquals("-xx-xxx", masker.computeMaskForInputCase("x-xx-xxx"))
    }

    @Test
    fun computeMask_fourthDigitDashCollision(){
        assertEquals("x-xxx", masker.computeMaskForInputCase("-xx-xxx"))
    }

    @Test
    fun computeMask_fifth(){
        assertEquals("-xxx", masker.computeMaskForInputCase("x-xxx"))
    }

    @Test
    fun computeMask_sixth(){
        assertEquals("xx", masker.computeMaskForInputCase("xxx"))
    }

    @Test
    fun firstDigitInputThenDeleted(){
        assertEquals("xxx-xx-xxx",masker.computeMaskForDeleteCase("xx-xx-xxx"))
    }
}