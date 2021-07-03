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
import android.view.Display
import androidx.constraintlayout.utils.widget.MockView
import mywidgets.SSNField
import org.junit.Test

import org.junit.Assert.*
import mywidgets.*
import org.junit.runner.manipulation.Ordering
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
 *
 * Strong Style Pairing -> next week?
 *
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class SSNTextWatcherTest {

    @Test
    fun instiateTheClass() {

        var textField =  object: TextInterface {
           override fun setText(string: String) {}
        }

        var watcher  = SSNTextWatcher( textField)
    }

    @Test
    fun afterTextChanged_dashIsAddedAfterThirdCharacter(){
        fail()
     //   var watcher = SSNTextWatcher()
        //watcher.
    }
}