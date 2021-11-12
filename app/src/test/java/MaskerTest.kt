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
        assertEquals("xx-xx-xxx", masker.computeMask("xxx-xx-xxx"))
    }

    @Test
    fun computeMask_secondDigit() {
        // Arrange
        val currentMask = "xx-xx-xxx"

        // Act and Assert
        assertEquals("x-xx-xxx", masker.computeMask(currentMask))
    }

    @Test
    fun computeMask_thirdDigit() {
        // Arrange, Act, and Assert
        assertEquals("-xx-xxx", masker.computeMask("x-xx-xxx"))
    }

    @Test
    fun computeMask_fourthDigitDashCollision(){
        assertEquals("x-xxx", masker.computeMask("-xx-xxx"))
    }
}