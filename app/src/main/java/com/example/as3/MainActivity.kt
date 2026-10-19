package com.example.as3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_listview).setOnClickListener {
            startActivity(Intent(this, ListViewActivity::class.java))
        }
        findViewById<Button>(R.id.btn_alertdialog).setOnClickListener {
            startActivity(Intent(this, AlertDialogActivity::class.java))
        }
        findViewById<Button>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
        findViewById<Button>(R.id.btn_actionmode).setOnClickListener {
            startActivity(Intent(this, ActionModeActivity::class.java))
        }
        findViewById<Button>(R.id.btn_recyclerview).setOnClickListener {
            startActivity(Intent(this, RecyclerViewActivity::class.java))
        }
    }
}
