package mywidgets

import android.text.*

/**
 * Day job: be able to mask with "xxx-xx-xxx" and also handle not only numbers
 * but SSN's with letters to.
 */

class SSNTextWatcher(
    private var ssnFieldAccess: SSNFieldAccess,
    var reentryGuard: TextWatcherActionState = TextWatcherActionState()
) : TextWatcher {

    private var mask: StringBuilder = StringBuilder(initialMask)
    private var userCursor: Int = 0
    private val masker = Masker()

    companion object {
        const val initialMask = "xxx-xx-xxx"
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
        if (reentryGuard.appIsAddingAMask()) return

        if (countOfCharactersAdded > 0) this.userCursor += countOfCharactersAdded
        //CharSequence s, int start, int count, int after
        println("beforeTextChanged: charactersInTextEdit " + charactersInTextEdit + " cursorPosition " + cursorPosition + "numberOfCharactersToReplace " + numberOfCharactersToReplace + " countOfCharactersAdded " + countOfCharactersAdded)
    }

    // The below is an interface from TextWatcher
    override fun afterTextChanged(charactersInGUI: Editable?) {
        println("afterTextChanged: characters " + charactersInGUI)
        if (reentryGuard.appIsAddingAMask()) return

        mask = StringBuilder(masker.computeMask(mask.toString()))
        println("afterTextChanged, new mask is computed to be: ${mask.toString()}")
        safelyAppendMaskOnToEnd(charactersInGUI!!)
        ssnFieldAccess.setSelectionOfTextEdit(userCursor)
    }

    private fun safelyAppendMaskOnToEnd(characters: Editable) {
        reentryGuard.setAppIsAddingAMask(true)
        val ssnFieldLength = 10
        val userTextEndPosition = ssnFieldLength - mask.length
        characters.delete(userTextEndPosition,characters.length)
        characters.insert(userTextEndPosition, mask)
        reentryGuard.setAppIsAddingAMask(false)
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
    fun getColor(i: Int): Int {
        return ssnFieldAccess.getCurrentColorOfTextEdit(3)
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