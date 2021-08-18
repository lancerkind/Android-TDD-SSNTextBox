package mywidgets
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

// I had to add the class construct due to demands from the compiler
class SSNField(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) , TextInterface  {
    val watcher = SSNTextWatcher(this) // We do TDD in this code because it isn't a view.

    init {
        addTextChangedListener(watcher)
    }

    override fun setSSNOnView(ssn: String) {
        this.setText(ssn)
    }
}