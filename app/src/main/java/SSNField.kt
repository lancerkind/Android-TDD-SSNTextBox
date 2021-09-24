package mywidgets
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

// I had to add the class construct due to demands from the compiler
class SSNField(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs), SSNFieldInterface {
    val watcher = SSNTextWatcher(this, SSNTextWatcher.TextWatcherActionState())

    init {
        addTextChangedListener(watcher)
    }

    override fun setHint2(ssnNumber: String) {
        this.setHint2(ssnNumber)
    }
}