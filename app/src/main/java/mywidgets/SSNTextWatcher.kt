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
    private var userCursor: Int = 0  // It's possible this is extra state.
    private val masker = Masker()

    enum class UserInputCase {
        DELETE, NEW_CHARACTER
    }

    private var userInputCase = UserInputCase.NEW_CHARACTER

    companion object {
        const val initialMask = "xxx-xx-xxx"
        const val maxCharAllowed = initialMask.length
    }

    fun setSelection(position: Int) {
        ssnFieldAccess.setSelectionOfTextEdit(position)
    }

    fun getSelectionEnd(): Int {
        return ssnFieldAccess.getSelectionEndOfTextEdit()
    }

    // TextWatcher interfacing https://developer.android.com/reference/android/text/TextWatcher
    override fun beforeTextChanged(
        charactersInTextEdit: CharSequence?,
        cursorPosition: Int,
        numberOfCharactersToReplace: Int,
        countOfCharactersAdded: Int
    ) {
        if (reentryGuard.appIsAddingAMask() || hittingTextBoxInputLimit()) return

        if (countOfCharactersAdded > 0)  {
            userInputCase = UserInputCase.NEW_CHARACTER
            userCursor += countOfCharactersAdded
        } else {
            userInputCase = UserInputCase.DELETE
            if (insertionPointAbutsBackOfDash(charactersInTextEdit!!)) {  // why double exclamation point?
                userCursor -=2
            }else {
                userCursor--
            }
        }
        println("beforeTextChanged: charactersInTextEdit " + charactersInTextEdit + " cursorPosition " + cursorPosition + "numberOfCharactersToReplace " + numberOfCharactersToReplace + " countOfCharactersAdded " + countOfCharactersAdded)
    }

    // The below is an interface from TextWatcher
    /*
    * The below overwrites the mask but skips dashes.
    // Skip dash functionality is handled in a different event depending of adding
    // a new character or deleting a new character.
    // when adding: 123-  <- after text changed
    // when deleting: 123-xx-xxx  <- before text changed
    */

    override fun afterTextChanged(charactersInGUI: Editable?) {
        println("afterTextChanged: characters " + charactersInGUI)
        if (reentryGuard.appIsAddingAMask()) return

        if( userInputCase == UserInputCase.NEW_CHARACTER) userAddsACharacter(charactersInGUI)
        else userDeletesACharacter(charactersInGUI)
    }

    private fun userDeletesACharacter(charactersInGUI: Editable?) {
        mask = StringBuilder(masker.computeMaskForDeleteCase(mask.toString()))
        println("afterTextChanged, new mask is computed to be: ${mask.toString()}")
        safelyAppendMaskOnToEnd(charactersInGUI!!)
// warning: only passes first simple test. grow code by adding more tests.
        ssnFieldAccess.setSelectionOfTextEdit(userCursor)
    }

    private fun userAddsACharacter(charactersInGUI: Editable?) {
        mask = StringBuilder(masker.computeMaskForInputCase(mask.toString()))
        println("afterTextChanged, new mask is computed to be: ${mask.toString()}")
        safelyAppendMaskOnToEnd(charactersInGUI!!)

        // And notice in beforeTextChanged, I'm not using the cursor parameter.  Shouldn't I?
        if (insertionPointAbutsFrontOfDash(charactersInGUI)) {
            userCursor++
            ssnFieldAccess.setSelectionOfTextEdit(userCursor)
        } else {
            ssnFieldAccess.setSelectionOfTextEdit(userCursor)
        }
    }

    private fun insertionPointAbutsBackOfDash(charactersInTextEdit : CharSequence): Boolean {
        if((charactersInTextEdit[userCursor-1]=='-')) return true
        return false
    }


    private fun insertionPointAbutsFrontOfDash(charactersInGUI: Editable): Boolean {
        if(charactersInGUI.length == 1 || hittingTextBoxInputLimit()) return false  // Case happens during initial GUI render
        if(charactersInGUI.get(userCursor).equals('-')) return true // 123-xx-xxx
        return false
    }

    private fun hittingTextBoxInputLimit() = userCursor == maxCharAllowed

    private fun safelyAppendMaskOnToEnd(characters: Editable) {
        reentryGuard.setAppIsAddingAMask(true)
        val userTextEndPosition = maxCharAllowed - mask.length
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