package com.example.inynierka2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StronaGlowna : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rejestracja)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Przycisk "wyloguj"
        val butlogout = findViewById<Button>(R.id.wyloguj)
        butlogout.setOnClickListener {
            // ewentualnie FirebaseAuth.getInstance().signOut()?
            val logout = Intent(this, Logowanie::class.java)
            startActivity(logout)
        }

        // Przycisk "Nowy case"
        val nowycas = findViewById<Button>(R.id.nowy_case)
        nowycas.setOnClickListener {
            val nowy = Intent(this, NowyCase::class.java)
            startActivity(nowy)
        }

        // Przycisk "Przesłane case’y" -> ekranu CaseList
        val starycas = findViewById<Button>(R.id.stary_case)
        starycas.setOnClickListener {
            val przeslany = Intent(this, CaseList::class.java)
            startActivity(przeslany)
        }

        // NOWY przycisk "Ostatni case" -> ekranu PoprzedniCase
        val ostatniCaseBtn = findViewById<Button>(R.id.ostatni_case)
        ostatniCaseBtn.setOnClickListener {
            val intent = Intent(this, PoprzedniCase::class.java)
            startActivity(intent)
        }
    }
}
