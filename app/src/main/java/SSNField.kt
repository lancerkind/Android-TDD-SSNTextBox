package mywidgets
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

// I had to add the class construct due to demands from the compiler
class SSNField(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs), SSNFieldAccess {
    val watcher = SSNTextWatcher(this, SSNTextWatcher.TextWatcherActionState())
    val maskSetter = MaskSetter()

    override fun setSelectionOfTextEdit(position: Int) {this.setSelection(position)}
    override fun getSelectionEndOfTextEdit() : Int { return this.selectionEnd
    }

    override fun getCurrentColorOfTextEdit(): Int {
        return currentTextColor
    }

    init {
        addTextChangedListener(watcher)
    }

    fun setMask(){
        // Needed to use a variable in order to give the IDE the hint as to which
        // setHint to use (there are mare than one).
        val hintPropertyReference : (CharSequence) -> Unit = this::setHint

        maskSetter.setMask(hintPropertyReference, SSNTextWatcher.mask)
    }
}