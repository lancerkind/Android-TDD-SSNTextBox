package mywidgets

import android.text.*

/**
 * Day job: be able to mask with "xxx-xx-xxx" and also handle not only numbers
 * but SSN's with letters to.
 */

class SSNTextWatcher(
    private var ssnFieldAccess: SSNFieldAccess,
    var textWatcherActionState: TextWatcherActionState = TextWatcherActionState()
) : TextWatcher {

    private val whatUserEntered: StringBuilder = StringBuilder()
    private var mask : StringBuilder = StringBuilder()
    private var userCursor : Int = 0

    companion object {
        const val mask = "xxx-xx-xxx"
    }

    fun setSelection(position: Int) {
        ssnFieldAccess.setSelectionOfTextEdit(position)
    }

    fun getSelectionEnd(): Int {
        return ssnFieldAccess.getSelectionEndOfTextEdit()
    }

    var ssnNumber: Editable? = null

    // TextWatcher interfacing https://developer.android.com/reference/android/text/TextWatcher
    override fun beforeTextChanged(
        charactersInTextEdit: CharSequence?,
        cursorPosition: Int,
        numberOfCharactersToReplace: Int,
        countOfCharactersAdded: Int
    ) {
        if (textWatcherActionState.appIsAddingAMask()) return

        if(countOfCharactersAdded > 0) this.userCursor += countOfCharactersAdded
        //CharSequence s, int start, int count, int after
        println("beforeTextChanged: charactersInTextEdit " + charactersInTextEdit + " cursorPosition " + cursorPosition + "numberOfCharactersToReplace " + numberOfCharactersToReplace + " countOfCharactersAdded " + countOfCharactersAdded)
    }

    // The below is an interface from TextWatcher
    override fun afterTextChanged(characters: Editable?) {
        println("afterTextChanged: characters " + characters)
        println("OUR VALUE " + characters!!.length)

        if (textWatcherActionState.appIsAddingAMask()) return

        whatUserEntered.append(characters.toString()) // will not work as mask is in here too.
        computeMask(characters)
        safelyAppendMaskOntoEnd(characters)
        ssnFieldAccess.setSelectionOfTextEdit(userCursor)
        /*         ssnFieldAccess.setSelectionOfTextEdit(1)
                 if(ssnFieldAccess.getSelectionEndOfTextEdit() == 1) println("selection location is 1")
                 else println("selection location is NOT 1")

         */
    }

    private fun computeMask(characters: Editable) {

        if (characters.length > 1) {
            mask = StringBuilder("x-xx-xxx")
            println("current color: ${ssnFieldAccess.getCurrentColorOfTextEdit()}")
        } else {
            mask = StringBuilder("xx-xx-xxx")
        }

    }

    private fun safelyAppendMaskOntoEnd(characters: Editable) {
        textWatcherActionState.setAppIsAddingAMask(true)
        characters.append(mask)
        textWatcherActionState.setAppIsAddingAMask(false)
    }

    // TextWatcher interfacing
    override fun onTextChanged(
        charactersInTextEdit: CharSequence?,
        cursorPosition: Int,
        numberOfCharactersToReplace: Int,
        countOfCharactersAdded: Int
    ) {
        println("onTextChanged: charactersInTextEdixt " + charactersInTextEdit + " cursorPosition " + cursorPosition + " numberOfCharactersToReplace " + numberOfCharactersToReplace + " countOfCharactersAdded " + countOfCharactersAdded)
    }

    // Test drive the interface calls below.
    // Todo: need to put cursor back where it belongs.
    fun getColor(i: Int): Int {
/*        ssnFieldAccess.setSelection2(3)
        return ssnFieldAccess.getColorAtPosition(3)

*/
        return -1
    }

    /**
     * The below class allows spying on boolean state changes for unit testing
     */
    open class TextWatcherActionState(private var skipBecauseAppIsAddingAMaskToTheInputText: Boolean = false) {

        open fun setAppIsAddingAMask(state: Boolean) {
            this.skipBecauseAppIsAddingAMaskToTheInputText = state
        }

        open fun appIsAddingAMask(): Boolean {
            return this.skipBecauseAppIsAddingAMaskToTheInputText
        }
    }
}