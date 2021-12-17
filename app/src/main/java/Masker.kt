package mywidgets

/**
 * What it does is:
 * 1) xxx-xx-xxx, when user enters a digit, it computes the mask that
 * is appended to user input
 *     vvvvvvvvv <- masker computes
 * 2) 1xx-xx-xxx
  *     vvvvvvvv <- masker computes
 * 3) 12x-xx-xxx
 */
class Masker {

    fun computeMaskForInputCase(maskBeforeInput: String) : String {
        var newMask =""

        if (maskBeforeInput.equals("xxx-xx-xxx")) { // user enters first digit, go from xxx-xx-xxx to xx-xx-xxx
            newMask = "xx-xx-xxx"
        } else if (maskBeforeInput.equals("xx-xx-xxx")) { // user enters second digit, go from xx-xx-xxx to x-xx-xxx
            newMask = "x-xx-xxx"
        } else if (maskBeforeInput.equals("x-xx-xxx")) {
            newMask = "-xx-xxx"
        } else if (maskBeforeInput.equals("-xx-xxx")){
            newMask = "x-xxx"
        } else if(maskBeforeInput.equals("x-xxx")) {
            newMask = "-xxx"
        } else if(maskBeforeInput.equals("xxx")) {
            newMask = "xx"
        }
        return newMask
    }

    fun computeMaskForDeleteCase(maskBeforeDeletion: String): String {
        if (maskBeforeDeletion == "x-xx-xxx") return "xx-xx-xxx"
        else if (maskBeforeDeletion == "-xx-xxx") return "x-xx-xxx"
        else if (maskBeforeDeletion == "x-xxx") return "-xx-xxx"
        else if (maskBeforeDeletion == "-xxx") return "x-xxx"
        else if (maskBeforeDeletion == "xx") return "xxx"
        else if (maskBeforeDeletion == "x") return "xx"
        else if (maskBeforeDeletion == "") return "x"

        return "xxx-xx-xxx"
    }
}