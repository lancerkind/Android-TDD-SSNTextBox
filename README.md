# Android-TDD-SSNTextBox
A social security number widget built with a testable design.  Please learn how to apply these patterns to ALL your android view objects.  
Test Driven Development is possible on Android!

Are you interested in the craft of writing code that is *durable*?  Code that test itself so you *know* it's ready to be released?  
Do you yern to have automatic checks (automated tests) that can tell you at the click of a button (or within a minute) that you've made a regression? 

If so, then WELCOME! This repository contains code that was developed on TDD.Academy's livestream.  
You can find the videos at these locations:
* https://www.twitch.tv/tddacademy
* https://m.youtube.com/channel/UCU1U6oPscxzKd_IWAIOm60w

Note: if you are a regular view of the show, all the code has been merged to Master branch. The other braches will soon be removed.


# Organization
Typically, each checkin represents an episode.

# Software Refence Architecture
The below is the recommendad design to use when developing with Android views:
View implements Interface 
Watcher depends on Interface

So in this situation, you'll create three things: a unit test for the watcher, a watcher, and an interface that decouples the watcher from the Android view object.
For example, for this repository we wanted to TDD a text box that accepts an SSN.  
So we built the following: SSNTextBoxWatcherTest, SSNTextBoxWatcher, TextBox interface with one method: setMethod (we'll only add what is necessary for our code).  

# How to use this pattern in other situations
To develop your logic using TDD, you'll need to:
- Learn how your Watcher's interface is used in the Android environment.  I recommend a simple prototype that outputs the parameters so you can understand how to test drive the watcher.
- Write a unit test that can instantiate your Watcher class
- Keep adding a unit test and then some functionality to make the test pass, following the three laws of TDD.

This pattern will work not only with creating watchers, but with other View work.

# How to use this to level up your team's capabilities?
The good news is that the code isn't completley finished. :-)  Here is what I suggest you do:
- demo to the team the working software (including what's not finished: dimming the mask and fix the problems of when you type in 9-10 characters, you can't backspace)
- show them the textedit code, the text watcher code (and supporting classes), and the test code.
- discuss the patterns and design principles: 
-- little as code possible in the view layer as that would be untestable for the unit test
-- keep the text watcher small (in fact it can be made smaller) by delegating to other classes so that functionality can be added without dealing with mock classes

After the team understannds the design then do some mobbing to resolve the aformentioned bugs by test driving the code.  If you want to see video of the code being written in this manner go to YouTube and look at the videos on TDD.Academy. There you'll see videos about this Android SSN Text Editer.

# Other Resources
Learn about the Software Testing Pyramid on the Agile Thoughts podcast: https://agilenoir.biz/en/agilethoughts/test-automation-pyramid-series/

An IT radio drama about TDD (I bet you never knew that software development could be so dangerous): https://agilenoir.biz/en/agilethoughts/why-developers-dont-tdd-a-radio-drama/

And learn more about TDD on Agile Thoughts: https://agilenoir.biz/en/agilethoughts/test-driven-development-series/

Cheers,
Coach Lancer
