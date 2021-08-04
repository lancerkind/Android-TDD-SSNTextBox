package mywidgets
import android.text.Editable
import android.text.TextWatcher

class SSNTextWatcher(ssnField: TextInterface)  : TextWatcher  {

    var deleteCharacterToLeftOfDash = false

        // TextWatcher interfacing https://developer.android.com/reference/android/text/TextWatcher
        override fun beforeTextChanged(charactersInTextEdit: CharSequence?, cursorPosition: Int, numberOfCharactersToReplace: Int, countOfCharactersAdded: Int) {
            //CharSequence s, int start, int count, int after
            println("beforeTextChanged: charactersInTextEdit " + charactersInTextEdit + " cursorPosition " + cursorPosition + "numberOfCharactersToReplace " + numberOfCharactersToReplace + " countOfCharactersAdded " + countOfCharactersAdded)
            if (charactersInTextEdit?.length == 3 && numberOfCharactersToReplace == 1) {
                deleteCharacterToLeftOfDash = true
            }
        }

        // TextWatcher interfacing
        override fun afterTextChanged(characters: Editable?) {
            println("afterTextChanged: characters " + characters)
                println("OUR VALUE " + characters!!.length )
            if ( characters.length == 2 && !this.deleteCharacterToLeftOfDash ) {
                characters.append("-")
            } else if (deleteCharacterToLeftOfDash) {
                deleteCharacterToLeftOfDash = false  // this line can't be added with TDD as without it, an asynchrounus event will cause us to call this method forever
                characters.delete(1,2)
            }

        }

        // TextWatcher interfacing
        override fun onTextChanged(
                charactersInTextEdixt: CharSequence?,
                cursorPosition: Int,
                numberOfCharactersToReplace: Int,
                countOfCharactersAdded: Int
        ) {
            // public abstract void onTextChanged (CharSequence s,
            //                int start,
            //                int before,
            //                int count)
            println("onTextChanged: charactersInTextEdixt " + charactersInTextEdixt + " cursorPosition " + cursorPosition + " numberOfCharactersToReplace " + numberOfCharactersToReplace + " countOfCharactersAdded " + countOfCharactersAdded )
        }
}