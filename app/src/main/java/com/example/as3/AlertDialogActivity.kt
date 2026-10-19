package com.example.as3

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AlertDialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alertdialog)

        findViewById<Button>(R.id.btn_show_dialog).setOnClickListener {
            showCustomDialog()
        }
    }

    private fun showCustomDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_login, null)
        val etUsername = view.findViewById<EditText>(R.id.et_username)
        val etPassword = view.findViewById<EditText>(R.id.et_password)

        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .create()
        dialog.show()

        view.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dialog.dismiss()
        }
        view.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            val name = etUsername.text.toString()
            val pwd = etPassword.text.toString()
            Toast.makeText(this, "用户名：$name，密码：$pwd", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }
}
