package mywidgets

import android.text.SpannableString


/**
* Since this interface is implemented by a View, each method needs to be
* unique from methods inherited by SSNField. Otherwise, when the runtime
* makes calls on the view, it'll get the methods overshadowed by the interface
* rather than what it expects. So the naming pattern is xyzOfTextEdit.
 */
interface SSNFieldAccess {
    fun setSelectionOfTextEdit(position: Int)
    fun getSelectionEndOfTextEdit() : Int
    fun getSpannable() : SpannableString

    // The below can be gotten rid of
    fun getCurrentColorOfTextEdit(position: Int): Int
}