package test.library

import android.text.SpannableStringBuilder

class MySpannableStringBuilder : SpannableStringBuilder() {
    private var buffer = StringBuffer()

    override val length: Int
        get() = buffer.length

    override fun delete(start: Int, end: Int): SpannableStringBuilder {
        buffer.delete(start, end)
        return this
    }

    override fun append(text: CharSequence): SpannableStringBuilder {
        buffer.append(text)
        return this
    }

    override fun append(text: Char): SpannableStringBuilder {
        buffer.append(text)
        return this
    }

    override fun append(text: CharSequence, start: Int, end: Int) : SpannableStringBuilder {
        buffer.append(text, start, end)
        return this
    }

    override fun toString(): String {
        return buffer.toString()
    }

    override fun insert(where: Int, tb: CharSequence?): SpannableStringBuilder {
        buffer.insert(where, tb)
        return this
    }

    override fun insert(where: Int, tb: CharSequence?, start: Int, end: Int): SpannableStringBuilder {
        buffer.insert(where, tb, start, end)
        return this
    }
}