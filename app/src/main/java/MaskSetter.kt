package mywidgets

import kotlin.reflect.KMutableProperty0

class MaskSetter {
    //target api for TextEdit.hint is: propertyReference: (CharSequence) -> Unit
    fun setMask(propertyReference : (CharSequence) -> Unit, mask: String) {
        propertyReference(mask)
    }


    /*
    // Other ways we could have done the above.
    fun setMask(propertyReference: () -> KMutableProperty0<CharSequence>, mask: String) {
        propertyReference().set(mask)
    }*/
}