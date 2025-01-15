package com.example.inynierka2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Dane : AppCompatActivity() {

    private lateinit var buttonPowrot: Button
    private lateinit var buttonNext: Button

    private lateinit var editTextClientName: EditText
    private lateinit var editTextClientSurname: EditText
    private lateinit var editTextExtraInfo: EditText   // to planowałeś jako PESEL
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

        // EditTexty:
        editTextClientName = findViewById(R.id.editTextClientName)
        editTextClientSurname = findViewById(R.id.editTextClientSurname)
        editTextExtraInfo = findViewById(R.id.editTextExtraInfo)  // pesel
        editTextClientPhone = findViewById(R.id.editTextClientPhone)

        // Wypełnij polami, jeśli już istnieją w DataHolder
        val currentCase = DataHolder.currentCase
        if (currentCase != null) {
            editTextClientName.setText(currentCase.clientName)
            editTextClientSurname.setText(currentCase.clientSurname)
            editTextExtraInfo.setText(currentCase.clientPesel)
            editTextClientPhone.setText(currentCase.clientPhone)
        }

        buttonPowrot.setOnClickListener {
            // Wracamy do NowyCase
            val cofka = Intent(this, NowyCase::class.java)
            startActivity(cofka)
        }

        buttonNext.setOnClickListener {
            // Zapisz do DataHolder
            if (DataHolder.currentCase == null) {
                DataHolder.currentCase = CaseData()
            }
            val case = DataHolder.currentCase!!

            case.clientName = editTextClientName.text.toString().trim()
            case.clientSurname = editTextClientSurname.text.toString().trim()
            case.clientPesel = editTextExtraInfo.text.toString().trim()
            case.clientPhone = editTextClientPhone.text.toString().trim()

            // Przejście do Furka
            val next = Intent(this, Furka::class.java)
            startActivity(next)
        }
    }
}
