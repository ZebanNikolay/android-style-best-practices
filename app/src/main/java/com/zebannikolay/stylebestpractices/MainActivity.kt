package com.zebannikolay.stylebestpractices

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import android.app.Activity

class MainActivity : AppCompatActivity() {

    val THEME1 = "theme1"
    val THEME2 = "theme2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getSavedTheme())
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        custom_component.setText(R.string.custom_component_text)
        plain_text.setText(R.string.plain_text)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_red_theme -> saveTheme(THEME2);
            R.id.action_indigo_theme -> saveTheme(THEME1);
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
        val theme = getPreferences(Activity.MODE_PRIVATE).getString("theme", THEME1)
        when (theme) {
            THEME2 -> return R.style.AppTheme_Theme2
            THEME1 -> return R.style.AppTheme_Theme1
            else -> return R.style.AppTheme_Theme2
        }
    }
}
