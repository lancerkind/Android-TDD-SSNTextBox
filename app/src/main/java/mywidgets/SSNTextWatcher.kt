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
            println("onTextChanged: charactersInTextEdixt " + charactersInTextEdixt + " cursorPosition " + cursorPosition + " numberOfCharactersToReplace " + numberOfCharactersToReplace + " countOfCharactersAdded " + countOfCharactersAdded )
            if (cursorPosition  == 2 && textWatcherActionState.getIsAddingDash() ==  false  && textWatcherActionState.getIsDeletingDash() == false )  {
                textWatcherActionState.setIsAddingDash( true)
                ssnNumber?.append('-')
                textWatcherActionState.setIsAddingDash(false)
            }

            // Couldn't TDD the below. How to test isDeletingDash is working?
            if (cursorPosition == 3 && numberOfCharactersToReplace == 1) {
                textWatcherActionState.setIsDeletingDash( true)
                ssnNumber?.delete(2,3)
                textWatcherActionState.setIsDeletingDash(false)
            }
        }

    class David{
         constructor(){}

    }

    public open class TextWatcherActionState(private var isDeletingDash: Boolean = false, private var isAddingDash: Boolean = false) {

        public open fun setIsDeletingDash(state: Boolean) { this.isDeletingDash = state      }
        public open fun getIsDeletingDash() : Boolean { return this.isDeletingDash        }

        public open fun setIsAddingDash(state: Boolean) { this.isAddingDash = state}
        public open fun getIsAddingDash() : Boolean {return this.isAddingDash        }
    }
}