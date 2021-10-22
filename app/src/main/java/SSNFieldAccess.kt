package mywidgets


/**
* Since this interface is implemented by a View, each method needs to be
* unique from methods inherited by SSNField. Otherwise, when the runtime
* makes calls on the view, it'll get the methods overshadowed by the interface
* rather than what it expects. So the naming pattern is xyzOfTextEdit.
 */
interface SSNFieldAccess {
    fun setSelectionOfTextEdit(position: Int)
    fun getSelectionEndOfTextEdit() : Int
    fun getCurrentColorOfTextEdit(): Int
}