package com.example.inynierka2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Dane : AppCompatActivity() {

    private lateinit var buttonPowrot: Button
    private lateinit var buttonNext: Button

    private lateinit var editTextClientName: EditText
    private lateinit var editTextClientSurname: EditText
    private lateinit var editTextExtraInfo: EditText   // PESEL
    private lateinit var editTextClientPhone: EditText

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

        buttonPowrot = findViewById(R.id.powrot)
        buttonNext = findViewById(R.id.buttonCreateCase)

        editTextClientName = findViewById(R.id.editTextClientName)
        editTextClientSurname = findViewById(R.id.editTextClientSurname)
        editTextExtraInfo = findViewById(R.id.editTextExtraInfo)
        editTextClientPhone = findViewById(R.id.editTextClientPhone)

        val currentCase = DataHolder.currentCase
        if (currentCase != null) {
            editTextClientName.setText(currentCase.clientName)
            editTextClientSurname.setText(currentCase.clientSurname)
            editTextExtraInfo.setText(currentCase.clientPesel)
            editTextClientPhone.setText(currentCase.clientPhone)
        }

        buttonPowrot.setOnClickListener {
            val cofka = Intent(this, NowyCase::class.java)
            startActivity(cofka)
        }

        buttonNext.setOnClickListener {
            val imie = editTextClientName.text.toString().trim()
            val nazwisko = editTextClientSurname.text.toString().trim()
            val pesel = editTextExtraInfo.text.toString().trim()
            val telefon = editTextClientPhone.text.toString().trim()

            // Sprawdź puste pola
            if (imie.isEmpty() || nazwisko.isEmpty() || pesel.isEmpty() || telefon.isEmpty()) {
                Toast.makeText(this, "Uzupełnij imię, nazwisko, PESEL i telefon!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (DataHolder.currentCase == null) {
                DataHolder.currentCase = CaseData()
            }
            val case = DataHolder.currentCase!!
            case.clientName = imie
            case.clientSurname = nazwisko
            case.clientPesel = pesel
            case.clientPhone = telefon

            val next = Intent(this, Furka::class.java)
            startActivity(next)
        }
    }
}
