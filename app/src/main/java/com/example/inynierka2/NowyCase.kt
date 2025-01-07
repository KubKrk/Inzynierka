package com.example.inynierka2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NowyCase : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nowy_case)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val butcofka = findViewById<Button>(R.id.powrot)
        butcofka.setOnClickListener{
            val cofka = Intent(this, StronaGlowna::class.java)
            startActivity(cofka)
        }
        val new = findViewById<Button>(R.id.DaneKlienta)
        new.setOnClickListener{
            val newC = Intent(this, Dane::class.java)
            startActivity(newC)
        }
        val dS = findViewById<Button>(R.id.daneAuta)
        dS.setOnClickListener{
            val newD = Intent(this, Furka::class.java)
            startActivity(newD)
        }
        val dU = findViewById<Button>(R.id.Zdj)
        dU.setOnClickListener{
            val newD = Intent(this, Ubezpieczalnia::class.java)
            startActivity(newD)
        }
        val dP = findViewById<Button>(R.id.podpisy)
        dP.setOnClickListener{
            val newD = Intent(this, Podpis::class.java)
            startActivity(newD)
        }
    }
}