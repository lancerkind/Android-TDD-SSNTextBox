package com.example.texteditbasicactivity

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.core.text.getSpans
import com.example.texteditbasicactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    /*
    function not unit testable due to this.findViewById, SSNField
    SSNField is a view object which requires a context to instantiate.

    Although we could use "extension" to override findViewById, we still have the problem with
    SSNField.
     */

    // MainActivity.OnStart-> ssnField.setHint()->MaskSetter
    override fun onStart() {
        super.onStart()
        // @+id/editTextText
        val ssnField : mywidgets.SSNField = findViewById (R.id.editTextText)
        ssnField.setMask()
        // learnToSetColors()
       // learnToMoveCursor()
    }

    fun learnToMoveCursor() {
        val textView: mywidgets.SSNField = findViewById(R.id.editTextText)
        textView.setSelection(5)
    }

    fun learnToSetColors(){
        // The below code isn't unit testable
        val textView: mywidgets.SSNField = findViewById(R.id.editTextText)
        val wordToSpan: Spannable = SpannableString("partial colored text")
        wordToSpan.setSpan(
            ForegroundColorSpan(Color.LTGRAY),
            2,
            4,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        // The getSpans takes a kotlin class, but the ForegroundColorSpan is a java class, so "::class.java"
        // converts it into a kotlin.
        textView.setText(wordToSpan)

        // The below code IS unit testable
        val array : Array<ForegroundColorSpan> = wordToSpan.getSpans(2, 4, ForegroundColorSpan::class.java)

        //val result  = if (textView.currentTextColor == Color.LTGRAY) "Y" else "N"
        val result = if(array[0].foregroundColor == Color.LTGRAY) "Y" else "N"

        println("Current color is LTGRAY $result")
    }
}