package mywidgets

/**
 * What it does is: 1) xxx-xx-xxx, when user enters a digit, it computes the mask that
 * is appended to user input
 *     vvvvvvvvv <- masker computes
 * 2) 1xx-xx-xxx
  *     vvvvvvvv <- masker computes
 * 3) 12x-xx-xxx
 */
class Masker {

    // Next show: do I need userInputMergedWithMask as a parameter?
    // Do I need to use a StringBuilder when I'm not building strings?
    fun computeMask(mask: String) : String {
        var newMask =""

        if (mask.equals("xxx-xx-xxx")) { // user enters first digit, go from xxx-xx-xxx to xx-xx-xxx
            newMask = "xx-xx-xxx"
        } else if (mask.equals("xx-xx-xxx")) { // user enters second digit, go from xx-xx-xxx to x-xx-xxx
            newMask = "x-xx-xxx"
        } else if (mask.equals("x-xx-xxx")) {
            newMask = "-xx-xxx"
        } else if (mask.equals("-xx-xxx")){
            newMask = "x-xxx"
        }
        return newMask
    }
}