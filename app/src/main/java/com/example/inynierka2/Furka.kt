package com.example.inynierka2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Furka : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_furka)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val butcofka = findViewById<Button>(R.id.powrot)
        butcofka.setOnClickListener {
            val cofka = Intent(this, Dane::class.java)
            startActivity(cofka)
        }
        val dane2 = findViewById<Button>(R.id.buttonCreateCase)
        dane2.setOnClickListener{
            val next = Intent(this, Ubezpieczalnia::class.java)
            startActivity(next)
        }
    }
}