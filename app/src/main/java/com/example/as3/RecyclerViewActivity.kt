package com.example.as3

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Animal(val name: String, val imageRes: Int)

class RecyclerViewActivity : AppCompatActivity() {

    private val animals = arrayListOf(
        Animal("Lion", R.drawable.lion),
        Animal("Tiger", R.drawable.tiger),
        Animal("Monkey", R.drawable.monkey),
        Animal("Dog", R.drawable.dog),
        Animal("Cat", R.drawable.cat),
        Animal("Elephant", R.drawable.elephant)
    )

    private lateinit var adapter: AnimalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_animals)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AnimalAdapter(animals) { animal ->
            Toast.makeText(this, animal.name, Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.btn_add).setOnClickListener {
            animals.add(Animal("New Animal ${animals.size + 1}", R.drawable.cat))
            adapter.notifyItemInserted(animals.size - 1)
        }
    }
}
