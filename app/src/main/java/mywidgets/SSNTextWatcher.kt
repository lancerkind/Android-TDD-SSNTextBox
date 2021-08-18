package mywidgets
import android.text.Editable
import android.text.TextWatcher

class SSNTextWatcher(ssnField: TextInterface)  : TextWatcher  {

    var isAddingDash: Boolean = false
    private var ssnField : TextInterface = ssnField
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
            if (cursorPosition  == 2 && isAddingDash ==  false )  {
                isAddingDash = true
                ssnNumber?.append('-')
                isAddingDash = false
            }
            if (cursorPosition == 3 && numberOfCharactersToReplace == 1) {
                ssnNumber?.delete(2,3)
            }
        }

}