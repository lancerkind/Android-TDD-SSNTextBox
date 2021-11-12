package com.example.texteditbasicactivity

import android.text.SpannableString
import org.junit.Test
//import org.junit.jupiter.api.Test

import org.junit.Assert.*
import mywidgets.*
import org.junit.Before
import org.junit.Ignore
import test.library.MySpannableStringBuilder
import kotlin.reflect.KMutableProperty0

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
 *
 * Episode 41:
 * 1 functional and reflection programming in kotlin to test an algorithim in
 * order to avoid untestable objects (view and AndroidCompat). https://stackoverflow.com/questions/69326501/how-to-use-kotlin-functional-programming-to-access-a-property
 * 2 When calling a method that takes a closure, don't do this: aFunc(foo.property)
 * Instead do aFunc(foo::property)!! Otherwise you invoke the function/property and end up
 * working with the return type.
 * 3 When working with a parameter that references a closure, don't forget to dereference the closure:
 * aFunc(block: ()->(CharSequence) -> Unit){
 *  block.property <-- NO! Don't do this!
 *  block().property <-- YES!
 *  }
 *  4 question I still have: is it important to pass the closure or pass the function?
 *
 *  Episode 42:
0 *  2
 *
 * Episode 43:
 * 1 Interface declaration for properties: https://stackoverflow.com/questions/56026971/kotlin-interface-property-only-require-public-getter-without-public-setter/56027637
 *    Interface Foo { fun val Age : Int}
 * 2 Kotlin constructor arguments: Class Foo (something: Something) {} <- something isn't a class field, in which case I can't refer to "something" in the class as there's no reference.
 *   Class Foo( var something: Something){} <- something is a class property.
 *   Class Foo( private var something: Something){} <- something is a private field.
 * 3 Whenever I use an interface with methods that are the same name as
 *   what the implementing class already has, I have problems! (infinit loops
 *   from android calling them and getting *my methods?*.
 *
 *  Episode 44:
 *  1 Use StringBuilder in prod code instead of MutableStringBuilder so that code can be unit tested. https://stackoverflow.com/questions/62011255/does-kotlin-support-mutable-strings/62011416
 *  1.1 How to get cursor position: https://stackoverflow.com/questions/8035107/how-to-set-cursor-position-in-edittext
 *  2 Learned how to hit the tiny post button in linked in so I can chat with all my fans.
 *  2.5 Possible new class to add: data structure for mask and "add" or "delete" is all we need.
 *  3 cursor position in the text watcher API represents where the cursor was when the user entered data.
 *
 *  Episode 45:
 *  1 Editable.append API is goofy! editable.append(thing, start, end) <- start and end are for *thing*.
 *  editable.insert(position, thing, start, end) <- position is where in editable to append
 *  2 When test don't compile, you can find what the problem is by the doing the following:
 *      A) run the unit tests
 *      B) click on problems.
 *  3 Add classes like you're paid by the class, even if it hurts your brain. Because, your
 *    brain is wrong and if you're going to transcend your limitations, you can't trust your brain.
 *
 *  Episode 46:
 *  1 Next show: we need to solve who handles: given: "1234xx-xxx" then: "123-4x-xxx"
 *  - ideas: masker handle it?, create a class to handle "dash work"?, embed in SSNTextWatcher (the unit testable class but a little difficult due to all the mock objects)
 *  0
 */

//@RunWith(RobolectricTestRunner::class)
class SSNTextWatcherTest {
    private lateinit var watcher : SSNTextWatcher
    private lateinit var textBox : MySpannableStringBuilder

    class SSNFieldMock (private var textBox : MySpannableStringBuilder): SSNFieldAccess {
        private var selectionPosition = -1


        override fun setSelectionOfTextEdit(position: Int) {
            selectionPosition = position
        }

        override fun getSelectionEndOfTextEdit(): Int {
            return if (selectionPosition == -1 ) {textBox.length}
            else {selectionPosition}
        }

        override fun getSpannable(): SpannableString {
            TODO("Not yet implemented")
        }

        override fun getCurrentColorOfTextEdit(position: Int): Int {
            TODO("Not yet implemented")
        }
    }

    @Before
    fun initializeWatcherAndTextBox(){
        textBox = MySpannableStringBuilder()
        watcher =  SSNTextWatcher(SSNFieldMock(textBox) )

        assertEquals(false, watcher.reentryGuard.appIsAddingAMask())
    }

    @Test
    fun mask_correctFormat(){
        assertEquals("xxx-xx-xxx", SSNTextWatcher.initialMask)
    }

    @Test
    fun afterTextChanged_guardsFromReentry(){
        // Arrange
        val appAddingMaskMock = object: SSNTextWatcher.TextWatcherActionState(){
            override fun appIsAddingAMask(): Boolean {
                return true
            }
        }
        watcher = SSNTextWatcher( SSNFieldMock(textBox), appAddingMaskMock)

        // Act
        watcher.afterTextChanged(textBox)

        //Assert
        assertEquals("",textBox.toString())
    }

    class Spy : SSNTextWatcher.TextWatcherActionState()
    {
        var methodRecorder :String =""
        override fun setAppIsAddingAMask(state: Boolean) {
            methodRecorder += state.toString()
        }
    }

    @Test
    fun afterTextChanged_userEntersSingleDigit(){
        // Arrange
        val spy007 = Spy()
        watcher = SSNTextWatcher( SSNFieldMock(textBox), spy007)
        userTypesOnPhone("1")
        watcher.beforeTextChanged(textBox,0,0,1)

        // Act
        watcher.afterTextChanged(textBox)

        // Assert
        assertBooleanStateCorrect(spy007)
        assertMaskCorrect("1xx-xx-xxx", textBox)
        assertEquals(1, positionOfInsertionPoint())
    }

    /**
     * In order to support alpha-numeric SSNs or handle copy from "111-111-111" into
     * SSNField, we need two datastructures to handle this.
     *
     * whatUserEntered <- this is the numbers the user types
     * mask <- this is what the program added as a hint to the user
     *
     * Take the above two Strings, and merge them into the view's Editable
     *
     * example:
     * step 1
     * whatUserEntered: ""
     * mask: xxx-xx-xxx
     * Editable: xxx-xx-xxx
     *
     * step 2
     * whatUserEntered: "1"
     * mask: xx-xx-xxx
     * merge the above into our Editable
     * Editable: 1xx-xx-xxx
     *
     * step 3
     * whatUserEntered: "2"
     * mask: x-xx-xxx
     * Editable: 12x-xx-xxx
     *
     * step 4 delete #2
     * whatUserEntered: delete
     * mask: xx-xx-xxx
     * Editable: 1xx-xx-xxx
     *
     * The below seems simpler!!!!!!!!!!!!!!!
     * Other design option:
     * mask: xx-xx-xxx
     * Editable: 1xx-xx-xxx
     * I can derive userEntered: 1
     *
     * The mask grows when user deletes. The mask shrink when the user adds.
     * Because the mask characters are constant throughout, and the user input is not,
     * I can shrink and grow the mask and assume everything outside the mask is
     * user entry.
     *
     *
     */

    @Test
    fun afterTextChanged_userEntersTwoDigits(){
        // Arrange
        userTypesOnPhone("1")

        watcher.beforeTextChanged(textBox,0,0,1)
        watcher.afterTextChanged(textBox)
        assertMaskCorrect("1xx-xx-xxx", textBox)
        assertEquals(1, positionOfInsertionPoint())

        userTypesOnPhone("2",1)
        assertEquals("12xx-xx-xxx",textBox.toString()) // extra X until text watcher events clean up the mask

        watcher.beforeTextChanged(textBox,1,0,1)
        watcher.afterTextChanged(textBox)
        assertMaskCorrect("12x-xx-xxx", textBox)
        assertEquals(2, positionOfInsertionPoint())

/*        assertEquals(Color.BLACK, watcher.getColor(0))
        assertEquals(Color.LTGRAY, watcher.getColor(2))
 */
    }

    // Probably refector my interface to the below.
    private fun positionOfInsertionPoint() = watcher.getSelectionEnd()

    private fun assertMaskCorrect(expectedTextInTextBox: String, textBox: MySpannableStringBuilder) {
        assertEquals(expectedTextInTextBox, textBox.toString())
    }

    private fun assertBooleanStateCorrect(spy:Spy ) {
        assertEquals("truefalse", spy.methodRecorder)
    }

    @Ignore
    @Test
    fun afterTextChanged_maskIsFaded(){
        userTypesOnPhone("1")
        watcher.afterTextChanged(textBox)
     //   assertEquals(textBox.get())
    }


/*
    @Test
    fun learn_ColoredText(){
        var textView: SpannableStringBuilder = SpannableStringBuilder()
        val WordtoSpan: Spannable = SpannableString("partial colored text")
        WordtoSpan.setSpan(
            ForegroundColorSpan(Color.BLUE),
            2,
            4,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        TextView
        textView.setText(WordtoSpan)
    }
*/
    @Ignore
    @Test
    fun onTextChanged_userAddsFirstNumber_appAddsMask() {
        // Arrange
        userTypesOnPhone("1")


        // Act
        watcher.afterTextChanged(textBox)

        // Assert

        //assertEquals("1xx-xx-xxx", textBox.toString())
    }

    private fun userTypesOnPhone(text : String) {
        userTypesOnPhone(text, 0)
    }

    private fun userTypesOnPhone(text : String, insertionPoint: Int) {
        textBox.insert(insertionPoint, text)
    }


    @Test
    fun learn_howAppendWorks(){
        assertEquals("", textBox.toString())
        textBox.append("1",0,1)
        assertEquals("1", textBox.toString())

        textBox.append("2",0,1)
        assertEquals("12", textBox.toString())

        textBox.append("3",0,1)
        assertEquals("123", textBox.toString())
    }

    @Ignore
    @Test
    fun onTextChanged_addingDashIsFalseForSecondNumber() {
        // Arrange
        textBox.append("12")

        // Act
        watcher.onTextChanged(textBox, 1, 0, 1)

        // Assert
        assertEquals(false, watcher.reentryGuard.appIsAddingAMask())
    }
    @Ignore
    @Test
    fun onTextChanged_addingDashIsTrueForThirdNumber() {
        // Arrange
        val actionState007 = object: SSNTextWatcher.TextWatcherActionState(){
            var addingDashRecorder  = ""
            override fun setAppIsAddingAMask(state: Boolean) {
                addingDashRecorder += state.toString()
                super.setAppIsAddingAMask(state)
            }
        }
        watcher = SSNTextWatcher( SSNFieldMock(textBox), actionState007)
        assertEquals("",  actionState007.addingDashRecorder )

        textBox.append("123")
        assertEquals("",  actionState007.addingDashRecorder )

        // Act
        watcher.onTextChanged(textBox, 2, 0, 1)

        // Assert
        assertEquals("truefalse",  actionState007.addingDashRecorder )
    }
    @Ignore
    @Test
    fun onTextChanged_whenAddingDashDoesntRecurse() {
        // Arrange
        val actionState007 = object: SSNTextWatcher.TextWatcherActionState(){
            override fun appIsAddingAMask(): Boolean {
                return true
            }
        }

        val watcher = SSNTextWatcher( SSNFieldMock(textBox), actionState007)
        textBox.append("123-")
        watcher.afterTextChanged(textBox)

        // Act
        watcher.onTextChanged(textBox, 2, 0, 1)

        // Assert
        assertEquals("123-",  textBox.toString())
    }


    @Ignore
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
    @Ignore
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

    @Ignore
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
    @Ignore
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
    @Ignore
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
    @Ignore
    @Test
    fun onTextChanged_deletingSecondDashDeletesNumberToo(){
        //Arrange
        val booleanSpy007 = object: SSNTextWatcher.TextWatcherActionState(){
            var dashRecorder  = ""
            override fun setAppIsAddingAMask(state: Boolean) {
                dashRecorder += state.toString()
                super.setAppIsAddingAMask(state)
            }
        }

        watcher = SSNTextWatcher( SSNFieldMock(textBox), booleanSpy007)

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

    @Test
    fun learn_reflectionWayToMutateProperties(){
        // arrange
        val mutablePropertyHere = WithMutableProperty()
        val mask = "xxx-xx-xxx"
        val myProperty = mutablePropertyHere::myProperty
        assertEquals("", myProperty.get())

        // act
        createMaskReflection(mask, block = myProperty)

        // assert
       assertEquals( mask, mutablePropertyHere.myProperty )
    }


    // You can @AgileThoughts1 <- tweet me here
    @Test
    fun learn_functionalWayToMutateProperties() {
        // arrange
        val mutablePropertyHere = WithMutableProperty()
        val mask = "xxx-xx-xxx"

        // act
        createMaskFunctional(mask, {mutablePropertyHere::myProperty })

        assertEquals(mask, mutablePropertyHere.myProperty)
    }

    fun createMaskReflection(mask : String, block: KMutableProperty0<String>) {
        block.set(mask)
    }
    fun createMaskFunctional(mask : String, block: () -> KMutableProperty0<String>) {
       block().set(mask)
    }

    class WithMutableProperty {
        var myProperty : String = ""
    }
}