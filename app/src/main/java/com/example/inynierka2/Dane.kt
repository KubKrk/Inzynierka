package com.example.inynierka2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Dane : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dane)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val butcofka = findViewById<Button>(R.id.powrot)
        butcofka.setOnClickListener {
            val cofka = Intent(this, NowyCase::class.java)
            startActivity(cofka)
        }
        val dane1 = findViewById<Button>(R.id.buttonCreateCase)
        dane1.setOnClickListener{
            val next = Intent(this, Furka::class.java)
            startActivity(next)
        }

    }
}