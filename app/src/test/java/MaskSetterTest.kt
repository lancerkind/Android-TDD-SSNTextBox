package com.example.texteditbasicactivity

import mywidgets.MaskSetter
import org.junit.Assert
import org.junit.Test

class MaskSetterTest {

    @Test
    fun setMask()    {
        // Arrange
        val maskSetter = MaskSetter()
        var hasMutableProperty = WithMutableProperty()
        val mask = "xx-xx"

        // Act
        maskSetter.setMask(hasMutableProperty::setAnotherProperty, mask)

        // Assert
        Assert.assertEquals(mask, hasMutableProperty.getAnotherProperty())
    }

    class WithMutableProperty {
        private var anotherProperty : CharSequence = ""

        public fun setAnotherProperty( update : CharSequence){
            anotherProperty = update
        }

        public fun getAnotherProperty() : String {return anotherProperty.toString()}

        public var myProperty : CharSequence = ""
    }
}