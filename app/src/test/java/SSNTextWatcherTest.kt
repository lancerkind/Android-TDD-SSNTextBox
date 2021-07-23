package com.example.texteditbasicactivity

import android.content.*
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.UserHandle
import android.text.Editable
import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.view.Display
import androidx.constraintlayout.utils.widget.MockView
import androidx.core.text.set
import mywidgets.SSNField
import org.junit.Test

import org.junit.Assert.*
import mywidgets.*
import org.junit.runner.RunWith
import org.junit.runner.manipulation.Ordering
import org.robolectric.RobolectricTestRunner
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import android.util.AttributeSet as AttributeSet


/**
 * What we learned Episode 29:
 * - Look carefully for the K symbol on the class you create.
 * - turn on the phone in the simulator
 * - Kotlin has single inheritance.
 * - Refactoring - if you feel like you did too much manual in your refactoring, and
 * you don't fast micro tests to confirm correctness, then backup and start again.
 * - Undo affects multiple files.
 *
 * Episode 30:
 * 1 Tried to avoid making Mock if you can.
 * 2 Failing the above, then make a simple Mock using YOUR OOD skills.
 * 3 Failing the above (maybe it's not simple) then think of a design solution.
 *
 * Episode 31:
 * 1 When starting up and executing unit tests, if Java says a class is
 * - missing and yet the application runs, do a command line: $ gradlew clean test
 * 2 Zoom isn't good at switching drivers for some reason. Next show, let's use Pop.
 * 3 afterTextChanged (before could work too) event does what we need: length, editable.
 * 4 Editable -> implents characterSequence, which has a length.
 *
 * Episode 32:
 * 1 "Stub!" - means that a stub implementation is executing, and not the android jar.
 * 2 Due to the above, we got Roboelectric so we can run fast unit tests on the Mac.
 * 3 Roboelectric execution is a *little* slow.  I bet a hand rolled mock will be bunches faster.
 * 4 SpannableStringBuilder is an implementation of Editable
 * 5 String implements  CharSequence, GetChars,...
 *
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
class SSNTextWatcherTest {

    @Test
    fun instiateTheClass() {

        var textField =  object: TextInterface {
           override fun setText(string: String) {}
        }

        var watcher  = SSNTextWatcher( textField)
    }

    /*
    class MockEditable(override val length: Int) : Editable {
        //override public open val length: Int =  42

        override fun get(index: Int): Char {
            TODO("Not yet implemented")
        }

        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
            TODO("Not yet implemented")
        }

        override fun getChars(p0: Int, p1: Int, p2: CharArray?, p3: Int) {
        }

        override fun <T : Any?> getSpans(p0: Int, p1: Int, p2: Class<T>?): Array<T> {
            TODO("Not yet implemented")
        }

        override fun getSpanStart(p0: Any?): Int {
            TODO("Not yet implemented")
        }

        override fun getSpanEnd(p0: Any?): Int {
            TODO("Not yet implemented")
        }

        override fun getSpanFlags(p0: Any?): Int {
            TODO("Not yet implemented")
        }

        override fun nextSpanTransition(p0: Int, p1: Int, p2: Class<*>?): Int {
            TODO("Not yet implemented")
        }

        override fun setSpan(p0: Any?, p1: Int, p2: Int, p3: Int) {
        }

        override fun removeSpan(p0: Any?) {
            TODO("Not yet implemented")
        }

        override fun append(p0: CharSequence?): Editable {
            TODO("Not yet implemented")
        }

        override fun append(p0: CharSequence?, p1: Int, p2: Int): Editable {
            TODO("Not yet implemented")
        }

        override fun append(p0: Char): Editable {
            TODO("Not yet implemented")
        }

        override fun replace(p0: Int, p1: Int, p2: CharSequence?, p3: Int, p4: Int): Editable {
            TODO("Not yet implemented")
        }

        override fun replace(p0: Int, p1: Int, p2: CharSequence?): Editable {
            TODO("Not yet implemented")
        }

        override fun insert(p0: Int, p1: CharSequence?, p2: Int, p3: Int): Editable {
            TODO("Not yet implemented")
        }

        override fun insert(p0: Int, p1: CharSequence?): Editable {
            TODO("Not yet implemented")
        }

        override fun delete(p0: Int, p1: Int): Editable {
            TODO("Not yet implemented")
        }

        override fun clear() {
            TODO("Not yet implemented")
        }

        override fun clearSpans() {
            TODO("Not yet implemented")
        }

        override fun setFilters(p0: Array<out InputFilter>?) {
            TODO("Not yet implemented")
        }

        override fun getFilters(): Array<InputFilter> {
            TODO("Not yet implemented")
        }
    }
*/

    @Test
    fun afterTextChanged_doesntChangeTheString(){
        var textField =  object: TextInterface {
            override fun setText(string: String) {}
        }
       var watcher = SSNTextWatcher(textField)

        // can we override a class
        var textBoxText = SpannableStringBuilder()
        assertEquals(0, textBoxText.length)
        textBoxText.append("1")
        watcher.afterTextChanged( textBoxText)
        assertEquals(1, textBoxText.length)
    }
}