package com.example.inynierka2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Furka : AppCompatActivity() {

    private lateinit var buttonPowrot: Button
    private lateinit var buttonNext: Button

    private lateinit var editMarka: EditText
    private lateinit var editModel: EditText
    private lateinit var editRej1: EditText  // data pierwszej rejestracji
    private lateinit var editBlachy: EditText // tablice rejestracyjne

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_furka)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonPowrot = findViewById(R.id.powrot)
        buttonNext = findViewById(R.id.buttonCreateCase)

        editMarka = findViewById(R.id.marka)
        editModel = findViewById(R.id.model)
        editRej1 = findViewById(R.id.rej1)
        editBlachy = findViewById(R.id.blachy)

        val currentCase = DataHolder.currentCase
        if (currentCase != null) {
            editMarka.setText(currentCase.carMark)
            editModel.setText(currentCase.carModel)
            editRej1.setText(currentCase.carFirstRegDate)
            editBlachy.setText(currentCase.carPlates)
        }

        buttonPowrot.setOnClickListener {
            val cofka = Intent(this, Dane::class.java)
            startActivity(cofka)
        }

        buttonNext.setOnClickListener {
            val marka = editMarka.text.toString().trim()
            val model = editModel.text.toString().trim()
            val dataRejestr = editRej1.text.toString().trim()
            val tablice = editBlachy.text.toString().trim()

            // sprawdzamy puste
            if (marka.isEmpty() || model.isEmpty() || dataRejestr.isEmpty() || tablice.isEmpty()) {
                Toast.makeText(this, "Uzupełnij dane samochodu (marka, model, data rej, tablice)!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (DataHolder.currentCase == null) {
                DataHolder.currentCase = CaseData()
            }
            val case = DataHolder.currentCase!!
            case.carMark = marka
            case.carModel = model
            case.carFirstRegDate = dataRejestr
            case.carPlates = tablice

            val next = Intent(this, Ubezpieczalnia::class.java)
            startActivity(next)
        }
    }
}
