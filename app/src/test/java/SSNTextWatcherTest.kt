package com.example.texteditbasicactivity

import android.text.SpannableStringBuilder
import org.junit.Test
//import org.junit.jupiter.api.Test

import org.junit.Assert.*
import mywidgets.*
import org.junit.Before
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
 * 4 Editable -> implements characterSequence, which has a length.
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
 * 3 Interface with setText that overrides View setText is a bad thing!!
 * 4 TDD on asynchronous feature using a spy.
 *
 * Episode 36:
 *  1 having difficulty doing TDD for flags that protect us from asynchronous re-entry.
 *    So we added a spy.
 *  2 Can add private to the class declaration so you don't get public properties.
 *  3 "Cant tell a computer what to do until you learn how to tell the computer what to do." -Lance
 *
 * Episode 37:
 *  1 Didn't get Pop to connect.
 *  2 A lot of Reverse TDD to see what code isn't covered by an assert.
 *  3 Stopped and looked at the code and came up with a simpler solution (one bool instead of 2, if statements
 *  getting more complicated).
 *
 * Episode 38:
 *  1 Unit test's launched in 7s. I wasn't using Zoom or Pop.  Did Roboelectic improve? Did Kotlin reflection improve?
 *  2 Really easy to add functionality when you know what to do.
 *
 *
 * Off Camera session:
 * - In text watcher, afterTextChange, when it modifies the Editable, Android calls the Before, On, After before
 * it exits the afterTextChange.
 *
 * 9/13:
 * - Able to override value properties (see MySpannableStringBuilder):
 * Class Foo : Bar {
 *   override val length: Int
 *    get() = buffer.length
 * }
 * - Not using Roboelectric speed up micro test execution.  Roboelectric uses dynamicly generated mocks which
 * are slower than a static mock
 *- By getting of Roboelectric, we can use the latest JUnit.
 *
 * Episode 39:
 * 1 don't change widget's properties such as setHint until it's ready, ie, use onCreate event
 * 2 use MainActivity.kt 's onStart, and working with Android lifecycles: https://developer.android.com/guide/components/activities/activity-lifecycle
 * 3 Learned how to use findViewById (R.id.editTextText). Widget IDs are stored in res/layout/activity_main.xml, R.id.<widget ID as set in XML file>
 *     Q: R.id??? How does that magic work?
 *
 * Episode 40:
 * 1 Can override functions in Final classes by using extension: https://stackoverflow.com/questions/45254766/kotlin-extension-for-final-class
 * 2 Can instantiate MainActivity via Roboelectric, but calling some methods will
 * throw exceptions.
 * 3 Can have static class members using "companion object" pattern: https://medium.com/@waqarul/kotlin-static-member-fields-and-singletons-b79fd65aaf9b
 */

//@RunWith(RobolectricTestRunner::class)
class SSNTextWatcherTest {

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

        override fun toString(): String {
            return buffer.toString()
        }
    }

    private lateinit var watcher : SSNTextWatcher
    private lateinit var textBox : MySpannableStringBuilder
    private lateinit var mockSSNField : MockSSNField

    @Before
    fun initializeWatcherAndTextBox(){
        mockSSNField = MockSSNField()
        watcher =  SSNTextWatcher(mockSSNField)
        textBox = MySpannableStringBuilder()

        assertEquals(false, watcher.textWatcherActionState.getSkipOnTextChanged())
    }

    @Test
    fun mask_correctFormat(){
        assertEquals("xxx-xx-xxx", SSNTextWatcher.mask)
    }

    @Test
    fun afterTextChanged_doesntChangeTheString() {
        // Arrange
        assertEquals(0, textBox.length)
        textBox.append("1")
        // Act
        watcher.afterTextChanged(textBox)
        // Assert
        assertEquals(1, textBox.length)
    }

    @Test
    fun onTextChanged_whenThreeNumbersAreEnteredAddDash() {
        // Arrange
        textBox.append("123")

        // Act
        watcher.afterTextChanged(textBox)
        watcher.onTextChanged(textBox, 2, 0, 1)

        // Assert
        assertEquals("123-", watcher.ssnNumber.toString())
    }

    @Test
    fun onTextChanged_addingDashIsFalseForFirstNumber() {
        // Arrange
        textBox.append("1")

        // Act
        watcher.onTextChanged(textBox, 0, 0, 1)

        // Assert
        assertEquals(false, watcher.textWatcherActionState.getSkipOnTextChanged())
    }

    @Test
    fun onTextChanged_addingDashIsFalseForSecondNumber() {
        // Arrange
        textBox.append("12")

        // Act
        watcher.onTextChanged(textBox, 1, 0, 1)

        // Assert
        assertEquals(false, watcher.textWatcherActionState.getSkipOnTextChanged())
    }

    @Test
    fun onTextChanged_addingDashIsTrueForThirdNumber() {
        // Arrange
        val actionState007 = object: SSNTextWatcher.TextWatcherActionState(){
            var addingDashRecorder  = ""
            override fun setSkipOnTextChanged(state: Boolean) {
                addingDashRecorder += state.toString()
                super.setSkipOnTextChanged(state)
            }
        }
        watcher = SSNTextWatcher(MockSSNField(), actionState007)
        assertEquals("",  actionState007.addingDashRecorder )

        textBox.append("123")
        assertEquals("",  actionState007.addingDashRecorder )

        // Act
        watcher.onTextChanged(textBox, 2, 0, 1)

        // Assert
        assertEquals("truefalse",  actionState007.addingDashRecorder )
    }

    @Test
    fun onTextChanged_whenAddingDashDoesntRecurse() {
        // Arrange
        val actionState007 = object: SSNTextWatcher.TextWatcherActionState(){
            override fun getSkipOnTextChanged(): Boolean {
                return true
            }
        }

        val watcher = SSNTextWatcher(MockSSNField(), actionState007)
        textBox.append("123-")
        watcher.afterTextChanged(textBox)

        // Act
        watcher.onTextChanged(textBox, 2, 0, 1)

        // Assert
        assertEquals("123-",  textBox.toString())
    }



    @Test
    fun onTextChanged_userDeletesTheDash_phoneDeletesCharacterBeforeDash() {
        // Arrange
        textBox.append("123")
        //phone adds dash
        watcher.afterTextChanged(textBox)
        watcher.onTextChanged(textBox, 2, 0, 1)
        assertEquals("123-", textBox.toString())

        // Act
        textBox.delete(3, 4)  // user deletes dash
        assertEquals("123", watcher.ssnNumber.toString()) // dash deleted on phone
        watcher.onTextChanged(textBox, 3, 1, 0)
        watcher.afterTextChanged(textBox)

        //assert
        assertEquals("12", watcher.ssnNumber.toString())
    }

    @Test
    fun onTextChanged_userDeletesTheDash_phoneDeletesCharacterBeforeDash_userAddsNumberSoDashAddedAgain() {
        // Arrange
        textBox.append("123")
        //phone adds dash
        watcher.afterTextChanged(textBox)
        watcher.onTextChanged(textBox, 2, 0, 1)
        assertEquals("123-", textBox.toString())

        textBox.delete(3, 4)  // user deletes dash
        assertEquals("123", watcher.ssnNumber.toString()) // dash deleted on phone
        watcher.onTextChanged(textBox, 3, 1, 0)
        watcher.afterTextChanged(textBox)

        assertEquals("12", watcher.ssnNumber.toString())
        textBox.append("3") // user adds number

        // Act
        watcher.onTextChanged(textBox, 2, 0, 1)
        watcher.afterTextChanged(textBox)

        //assert
        assertEquals("123-", watcher.ssnNumber.toString())
    }

    @Test
    fun learn_howToUsedelete() {
        textBox.append("123-")

        // Act
        textBox.delete(2, 4)  // the boundry conditions are odd with this API.

        assertEquals("12", textBox.toString())
    }

    @Test
    fun onTextChanged_wontRecurseWhenUserDeletesDash() {
        // Arrange
        textBox.append("123-")
        watcher.afterTextChanged(textBox)
        textBox.delete(3,4) // user deletes dash

        // Act
        watcher.onTextChanged(textBox, 3, 1, 0)

        // Assert
        assertEquals("12", watcher.ssnNumber.toString())
    }

    @Test
    fun onTextChanged_userAdds4thDigit() {
        // Arrange
        textBox.append("123-4")
        watcher.afterTextChanged(textBox)

        // Act
        watcher.onTextChanged(textBox,4, 0,1)

        //Assert
        assertEquals("123-4",textBox.toString())
    }

    @Test
    fun onTextChanged_dashAddedWhenUserAdds5thDigit() {
        //Arrange
        textBox.append("123-45")
        watcher.afterTextChanged(textBox)

        //Act
        watcher.onTextChanged(textBox,5, 0,1)

        //Assert
        assertEquals("123-45-", textBox.toString())
    }

    @Test
    fun onTextChanged_deletingSecondDashDeletesNumberToo(){
        //Arrange
        val booleanSpy007 = object: SSNTextWatcher.TextWatcherActionState(){
            var dashRecorder  = ""
            override fun setSkipOnTextChanged(state: Boolean) {
                dashRecorder += state.toString()
                super.setSkipOnTextChanged(state)
            }
        }

        watcher = SSNTextWatcher(MockSSNField(), booleanSpy007)

        textBox.append("123-45-")
        watcher.afterTextChanged(textBox)
        textBox.delete(6,7)
        assertEquals("123-45", textBox.toString())

        //Act
        watcher.onTextChanged(textBox,6,1,0)

        //Assert
        assertEquals("123-4", textBox.toString())
        assertEquals("truefalse",booleanSpy007.dashRecorder)
    }

    class MockSSNField constructor() : SSNFieldInterface {
        var ssnNumber : String = ""
        override fun setHint2(ssnNumber: String) {
            this.ssnNumber = ssnNumber
        }
     }

}