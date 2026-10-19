package com.example.as3

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActionModeActivity : AppCompatActivity() {

    private val items = arrayListOf("One", "Two", "Three", "Four", "Five")
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actionmode)

        val listView = findViewById<ListView>(R.id.lv_actionmode)
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)

        listView.setOnItemLongClickListener { _, _, position, _ ->
            if (actionMode == null) {
                actionMode = startActionMode(ActionModeCallback(position))
            }
            true
        }
    }

    private inner class ActionModeCallback(private val position: Int) : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.menu_actionmode, menu)
            mode.title = items[position]
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean = false

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.action_edit -> {
                    Toast.makeText(
                        this@ActionModeActivity,
                        "编辑 ${items[position]}",
                        Toast.LENGTH_SHORT
                    ).show()
                    mode.finish()
                    return true
                }
                R.id.action_delete -> {
                    val removed = items.removeAt(position)
                    val adapter =
                        findViewById<ListView>(R.id.lv_actionmode).adapter as ArrayAdapter<*>
                    adapter.notifyDataSetChanged()
                    Toast.makeText(
                        this@ActionModeActivity,
                        "已删除 $removed",
                        Toast.LENGTH_SHORT
                    ).show()
                    mode.finish()
                    return true
                }
            }
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
        }
    }
}
