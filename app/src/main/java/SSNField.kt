package mywidgets
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

// I had to add the class construct due to demands from the compiler
class SSNField(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs)/*, SSNFieldInterface*/ {
    val watcher = SSNTextWatcher(SSNTextWatcher.TextWatcherActionState())
    val maskSetter = MaskSetter()

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