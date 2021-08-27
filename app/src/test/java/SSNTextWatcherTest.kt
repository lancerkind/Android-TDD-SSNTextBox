package com.example.texteditbasicactivity

import android.text.SpannableStringBuilder
import org.junit.Test
//import org.junit.jupiter.api.Test

import org.junit.Assert.*
import mywidgets.*
import org.junit.Ignore
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


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
 * Episode 34:
 * 1 Pop problems: Is Pop slowing us down? Why is Pop losing David's shift characters (and period)??
 * 2 Interfaces require an override?
 * 3 Interface with setText that overides View setText is a bad thing!!
 * 4 TDD on asynchronus feature using a spy.
 *
 * Episode 36:
 *  1 having difficulty doing TDD for flags that protect us from asynchronus re-entry.
 *    So we added a spy.
 *  2 Can add private to the class declaration so you don't get public properties.
 *  3 "Cant tell a computer what to do until you learn how to tell the computer what to do." -Lance
 *
 *
 * Off Camera session:
 * - In text watcher, afterTextChange, when it modifies the Editable, Android calls the Before, On, After before
 * it exits the afterTextChange.
 *
 *
 */

@RunWith(RobolectricTestRunner::class)
class SSNTextWatcherTest {

    @Test
    fun instantiateTheClass_initialStateIsNoDash() {
        val watcher = SSNTextWatcher()
        assertEquals(false, watcher.textWatcherActionState.getIsAddingDash())
    }


    @Test

    fun afterTextChanged_doesntChangeTheString() {
        val watcher = SSNTextWatcher()

        // can we override a class
        val textBoxText = SpannableStringBuilder()
        assertEquals(0, textBoxText.length)
        textBoxText.append("1")
        watcher.afterTextChanged(textBoxText)
        assertEquals(1, textBoxText.length)
    }

    @Test
    fun onTextChanged_whenThreeNumbersAreEnteredAddDash() {
        // Arrange
        val watcher = SSNTextWatcher()

        val textBoxText = SpannableStringBuilder()
        textBoxText.append("123")

        // Act
        watcher.afterTextChanged(textBoxText)
        watcher.onTextChanged(textBoxText, 2, 0, 1)

        // Assert
        // see comment about Android and events.
//        assertEquals(true, watcher.isAddingDash )
        assertEquals("123-", watcher.ssnNumber.toString())
    }

    @Test
    fun onTextChanged_addingDashIsFalseForFirstNumber() {
        // Arrange
        val watcher = SSNTextWatcher()

        val textBoxText = SpannableStringBuilder()
        textBoxText.append("1")

        // Act
        watcher.onTextChanged(textBoxText, 0, 0, 1)

        // Assert
        assertEquals(false, watcher.textWatcherActionState.getIsAddingDash())
    }

    @Test
    fun onTextChanged_addingDashIsFalseForSecondNumber() {
        // Arrange
        val watcher = SSNTextWatcher()

        val textBoxText = SpannableStringBuilder()
        textBoxText.append("12")

        // Act
        watcher.onTextChanged(textBoxText, 1, 0, 1)

        // Assert
        assertEquals(false, watcher.textWatcherActionState.getIsAddingDash())
    }

    @Test
    fun onTextChanged_addingDashIsTrueForThirdNumber() {
        // Arrange
        val actionState007 = object: SSNTextWatcher.TextWatcherActionState(){
            var addingDashRecorder  = ""
            override fun setIsAddingDash(state: Boolean) {
                addingDashRecorder += state.toString()
                super.setIsAddingDash(state)
            }
        }

        val watcher = SSNTextWatcher(actionState007)

        val textBoxText = SpannableStringBuilder()
        textBoxText.append("123")

        // Act
        watcher.onTextChanged(textBoxText, 2, 0, 1)

        assertEquals("truefalse",  actionState007.addingDashRecorder )
    }

    @Test
    fun onTextChanged_userDeletesTheDash_phoneDeletesCharacterBeforeDash() {
        // Arrange
        val watcher = SSNTextWatcher()

        val textBoxText = SpannableStringBuilder()
        textBoxText.append("123")
        //phone adds dash
        watcher.afterTextChanged(textBoxText)
        watcher.onTextChanged(textBoxText, 2, 0, 1)
        assertEquals("123-", textBoxText.toString())
        // Act
        textBoxText.delete(3, 4)  // user deletes dash
        assertEquals("123", watcher.ssnNumber.toString()) // dash deleted on phone
        watcher.onTextChanged(textBoxText, 3, 1, 0)
        watcher.afterTextChanged(textBoxText)

        //assert
        assertEquals("12", watcher.ssnNumber.toString())
    }


    @Test
    fun learn_delete() {
        var textBoxText = SpannableStringBuilder()
        textBoxText.append("123-")
        textBoxText.delete(2, 4)
        assertEquals("12", textBoxText.toString())
    }

    @Ignore
    @Test
    fun onTextChanged_wontDoAnythingIfIsDeleting() {
        // Arrange
        val watcher = SSNTextWatcher()

        val textBoxText = SpannableStringBuilder()
        textBoxText.append("123-")
        watcher.afterTextChanged(textBoxText)
        watcher.onTextChanged(textBoxText, 3, 1, 0)
        assertEquals("12", watcher.ssnNumber.toString())
   // how to test if can be called recursively????
        // Act
        watcher.onTextChanged(textBoxText, 2, 0, 1)
    }
}