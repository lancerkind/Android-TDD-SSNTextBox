package mywidgets
import android.text.Editable
import android.text.TextWatcher

public class SSNTextWatcher(var textWatcherActionState: TextWatcherActionState = TextWatcherActionState())  : TextWatcher  {

   var ssnNumber: Editable? = null

    // TextWatcher interfacing https://developer.android.com/reference/android/text/TextWatcher
        override fun beforeTextChanged(charactersInTextEdit: CharSequence?, cursorPosition: Int, numberOfCharactersToReplace: Int, countOfCharactersAdded: Int) {
            //CharSequence s, int start, int count, int after
            println("beforeTextChanged: charactersInTextEdit " + charactersInTextEdit + " cursorPosition " + cursorPosition + "numberOfCharactersToReplace " + numberOfCharactersToReplace + " countOfCharactersAdded " + countOfCharactersAdded)
        }

        // TextWatcher interfacing
        override fun afterTextChanged(characters: Editable?) {
            if (ssnNumber == null) ssnNumber = characters
            println("afterTextChanged: characters " + characters)
                println("OUR VALUE " + characters!!.length )
        }

        // TextWatcher interfacing
        override fun onTextChanged(
                charactersInTextEdixt: CharSequence?,
                cursorPosition: Int,
                numberOfCharactersToReplace: Int,
                countOfCharactersAdded: Int
        ) {
            if (textWatcherActionState.getSkipOnTextChanged() == true) return;

            println("onTextChanged: charactersInTextEdixt " + charactersInTextEdixt + " cursorPosition " + cursorPosition + " numberOfCharactersToReplace " + numberOfCharactersToReplace + " countOfCharactersAdded " + countOfCharactersAdded )
            if (cursorPosition  == 2 || cursorPosition == 5 )  {
                textWatcherActionState.setSkipOnTextChanged( true)
                ssnNumber?.append('-')
                textWatcherActionState.setSkipOnTextChanged( false)
            }

            if (cursorPosition == 3 && numberOfCharactersToReplace == 1 ) {
                textWatcherActionState.setSkipOnTextChanged( true)
                ssnNumber?.delete(2,3)
                textWatcherActionState.setSkipOnTextChanged( false)
            }

            if(cursorPosition == 6 && numberOfCharactersToReplace == 1) {
                textWatcherActionState.setSkipOnTextChanged(true)
                ssnNumber?.delete(5,6)
                textWatcherActionState.setSkipOnTextChanged(false)
            }
        }

    /**
     * This class allows spying on boolean state changes for unit testing
      */
    public open class TextWatcherActionState( private var isSkipOnTextChanged: Boolean = false) {

        public open fun setSkipOnTextChanged(state: Boolean) { this.isSkipOnTextChanged = state}
        public open fun getSkipOnTextChanged() : Boolean {return this.isSkipOnTextChanged }
    }
}