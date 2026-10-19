package com.example.as3

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_font, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val tv = findViewById<TextView>(R.id.tv_test)
        when (item.itemId) {
            R.id.font_10 -> {
                tv.textSize = 10f
                return true
            }
            R.id.font_16 -> {
                tv.textSize = 16f
                return true
            }
            R.id.font_20 -> {
                tv.textSize = 20f
                return true
            }
            R.id.menu_normal -> {
                Toast.makeText(this, "普通菜单项被点击", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.color_red -> {
                tv.setTextColor(getColor(R.color.red))
                return true
            }
            R.id.color_black -> {
                tv.setTextColor(getColor(R.color.black))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
