package com.example.texteditbasicactivity

import mywidgets.MaskSetter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MaskSetterTest {

    @Test
    fun setMask()    {
        // Arrange
        val maskSetter = MaskSetter()
        val hasMutableProperty = WithMutableProperty()
        val mask = "xx-xx"

        // Act
        maskSetter.setMask(hasMutableProperty::setAnotherProperty, mask)

        // Assert
        assertEquals(mask, hasMutableProperty.getAnotherProperty())
    }

    class WithMutableProperty {
        private var anotherProperty : CharSequence = ""

        fun setAnotherProperty( update : CharSequence){
            anotherProperty = update
        }

        fun getAnotherProperty() : String {return anotherProperty.toString()}

        var myProperty : CharSequence = ""
    }
}