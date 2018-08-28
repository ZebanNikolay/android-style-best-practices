package com.zebannikolay.stylebestpractices

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import android.app.Activity
import kotlinx.android.synthetic.main.content_main.*
import android.content.SharedPreferences

class MainActivity : AppCompatActivity() {

    val INDIGO = "indigo"
    val RED = "red"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getSavedTheme())
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_red_theme -> saveTheme(RED);
            R.id.action_indigo_theme -> saveTheme(INDIGO);
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun saveTheme(value: String) {
        val editor = getPreferences(Activity.MODE_PRIVATE).edit()
        editor.putString("theme", value)
        editor.commit()
        recreate()
    }

    private fun getSavedTheme(): Int {
        val theme = getPreferences(Activity.MODE_PRIVATE).getString("theme", INDIGO)
        when (theme) {
            RED -> return R.style.AppTheme_Red
            INDIGO -> return R.style.AppTheme_Indigo
            else -> return R.style.AppTheme_Red
        }
    }
}
