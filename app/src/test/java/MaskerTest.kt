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
    fun computeMask_FirstDigitInput()    {
        // Arrange, Act, and Assert
        assertEquals("xx-xx-xxx", masker.computeMaskForInputCase("xxx-xx-xxx"))
    }

    @Test
    fun computeMask_SecondDigit() {
        // Arrange
        val currentMask = "xx-xx-xxx"

        // Act and Assert
        assertEquals("x-xx-xxx", masker.computeMaskForInputCase(currentMask))
    }

    @Test
    fun computeMask_ThirdDigit() {
        // Arrange, Act, and Assert
        assertEquals("-xx-xxx", masker.computeMaskForInputCase("x-xx-xxx"))
    }

    @Test
    fun computeMask_FourthDigitDashCollision(){
        assertEquals("x-xxx", masker.computeMaskForInputCase("-xx-xxx"))
    }

    @Test
    fun computeMask_Fifth(){
        assertEquals("-xxx", masker.computeMaskForInputCase("x-xxx"))
    }

    @Test
    fun computeMask_Sixth(){
        assertEquals("xx", masker.computeMaskForInputCase("xxx"))
    }

    @Test
    fun firstDigitInputThenDeleted(){
        assertEquals("xxx-xx-xxx",masker.computeMaskForDeleteCase("xx-xx-xxx"))
    }

    @Test
    fun secondDigitInputThenDeleted(){
        assertEquals("xx-xx-xxx",masker.computeMaskForDeleteCase("x-xx-xxx"))
    }

    @Test
    fun thirdDigitInputThenDeleted(){
        assertEquals("x-xx-xxx",masker.computeMaskForDeleteCase("-xx-xxx"))
    }

    @Test
    fun forthDigitInputThenDeleted(){
        assertEquals("-xx-xxx",masker.computeMaskForDeleteCase("x-xxx"))
    }

    @Test
    fun fifthDigitInputThenDeleted(){
        assertEquals("x-xxx",masker.computeMaskForDeleteCase("-xxx"))
    }

    @Test
    fun sixthDigitInputThenDeleted(){
        assertEquals("xxx", masker.computeMaskForDeleteCase("xx"))
    }

    @Test
    fun seventhDigitInputThenDeleted(){
        assertEquals("xx", masker.computeMaskForDeleteCase("x"))
    }

    @Test
    fun eightDigitInputThenDeleted(){
        assertEquals("x", masker.computeMaskForDeleteCase(""))
    }
}