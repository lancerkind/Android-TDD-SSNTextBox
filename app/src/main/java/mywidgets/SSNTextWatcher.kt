package mywidgets
import android.text.Editable
import android.text.TextWatcher

class SSNTextWatcher(/*val ssnField: SSNFieldInterface,*/ var textWatcherActionState: TextWatcherActionState = TextWatcherActionState())  : TextWatcher  {

    companion object {
        const val mask = "xxx-xx-xxx"
    }

    var ssnNumber: Editable? = null
    // TextWatcher interfacing https://developer.android.com/reference/android/text/TextWatcher
        override fun beforeTextChanged(charactersInTextEdit: CharSequence?, cursorPosition: Int, numberOfCharactersToReplace: Int, countOfCharactersAdded: Int) {
            //CharSequence s, int start, int count, int after
            println("beforeTextChanged: charactersInTextEdit " + charactersInTextEdit + " cursorPosition " + cursorPosition + "numberOfCharactersToReplace " + numberOfCharactersToReplace + " countOfCharactersAdded " + countOfCharactersAdded)
        }

        // TextWatcher interfacing
        override fun afterTextChanged(characters: Editable?) {
            println("afterTextChanged: characters " + characters)
            println("OUR VALUE " + characters!!.length )

            if (textWatcherActionState.appIsAddingAMask()) return

            safeAppend(characters, "xx-xx-xxx")
        }

        private fun safeAppend(characters: Editable, mask: String) {
            textWatcherActionState.setAppIsAddingAMask( true)
            characters.append(mask)
            textWatcherActionState.setAppIsAddingAMask( false)
        }

        // TextWatcher interfacing
        override fun onTextChanged(
            charactersInTextEdit: CharSequence?,
            cursorPosition: Int,
            numberOfCharactersToReplace: Int,
            countOfCharactersAdded: Int
        ) {
            println("onTextChanged: charactersInTextEdixt " + charactersInTextEdit + " cursorPosition " + cursorPosition + " numberOfCharactersToReplace " + numberOfCharactersToReplace + " countOfCharactersAdded " + countOfCharactersAdded )
        }

    /**
     * This class allows spying on boolean state changes for unit testing
      */
    open class TextWatcherActionState( private var skipBecauseAppIsAddingAMaskToTheInputText: Boolean = false) {

        open fun setAppIsAddingAMask(state: Boolean) { this.skipBecauseAppIsAddingAMaskToTheInputText = state}
        open fun appIsAddingAMask() : Boolean {return this.skipBecauseAppIsAddingAMaskToTheInputText }
    }
}