package mywidgets
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

// I had to add the class construct due to demands from the compiler
class SSNField(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs)  {
    val watcher = SSNTextWatcher(SSNTextWatcher.TextWatcherActionState())

    init {
        addTextChangedListener(watcher)
    }
}