package mywidgets
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

// I had to add the class construct due to demands from the compiler
class EditTextObserver(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs){
    val watcher = SSNTextWatcher()
    init {
        addTextChangedListener(watcher)
    }
}