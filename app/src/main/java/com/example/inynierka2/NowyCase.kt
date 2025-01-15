package com.example.inynierka2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase


class NowyCase : AppCompatActivity() {

    private lateinit var buttonPowrot: Button
    private lateinit var buttonPodpis: Button
    private lateinit var buttonDaneAuta: Button
    private lateinit var buttonDaneKlienta: Button
    private lateinit var buttonUbezpieczalnia: Button
    private lateinit var buttonZakoncz: Button

    // Zamiast `by lazy`, definiujemy zwykłe zmienne
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nowy_case)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicjalizacja DataHolder
        if (DataHolder.currentCase == null) {
            DataHolder.currentCase = CaseData()
        }

        buttonPowrot = findViewById(R.id.powrot)
        buttonPodpis = findViewById(R.id.podpisy)
        buttonDaneAuta = findViewById(R.id.daneAuta)
        buttonDaneKlienta = findViewById(R.id.DaneKlienta)
        buttonUbezpieczalnia = findViewById(R.id.Zdj)
        buttonZakoncz = findViewById(R.id.zakoncz)

        buttonPowrot.setOnClickListener {
            val cofka = Intent(this, StronaGlowna::class.java)
            startActivity(cofka)
        }

        buttonDaneKlienta.setOnClickListener {
            startActivity(Intent(this, Dane::class.java))
        }

        buttonDaneAuta.setOnClickListener {
            startActivity(Intent(this, Furka::class.java))
        }

        buttonUbezpieczalnia.setOnClickListener {
            startActivity(Intent(this, Ubezpieczalnia::class.java))
        }

        buttonPodpis.setOnClickListener {
            startActivity(Intent(this, Podpis::class.java))
        }

        buttonZakoncz.setOnClickListener {
            wyslijDoBazy()
        }
    }

    private fun wyslijDoBazy() {
        val case = DataHolder.currentCase
        if (case == null) {
            Toast.makeText(this, "Brak danych w DataHolder!", Toast.LENGTH_SHORT).show()
            return
        }

        // Sprawdzamy, czy najważniejsze pola nie są puste (przykład)
        if (case.clientName.isEmpty() || case.clientSurname.isEmpty()) {
            Toast.makeText(this, "Uzupełnij dane klienta (imię, nazwisko)!", Toast.LENGTH_SHORT).show()
            return
        }
        if (case.carMark.isEmpty() || case.carModel.isEmpty() || case.carPlates.isEmpty()) {
            Toast.makeText(this, "Uzupełnij dane samochodu (marka, model, tablice)!", Toast.LENGTH_SHORT).show()
            return
        }
        if (case.poszkodInsurance.isEmpty() || case.sprawcaInsurance.isEmpty()) {
            Toast.makeText(this, "Uzupełnij ubezpieczalnie poszkodowanego i sprawcy!", Toast.LENGTH_SHORT).show()
            return
        }
        if (case.signatureUrl.isEmpty()) {
            Toast.makeText(this, "Brak podpisu!", Toast.LENGTH_SHORT).show()
            return
        }

        val casesRef = database.getReference("cases")
        // generujemy klucz
        val newCaseRef = casesRef.push()
        val newCaseId = newCaseRef.key
        // (Ostrzeżenie: Condition 'newCaseId == null' is always false – można pominąć)
        if (newCaseId == null) {
            Toast.makeText(this, "Błąd tworzenia klucza w bazie!", Toast.LENGTH_SHORT).show()
            return
        }

        newCaseRef.setValue(case)
            .addOnSuccessListener {
                Toast.makeText(this, "Wysłano do bazy!", Toast.LENGTH_SHORT).show()
                // Wyzeruj DataHolder
                DataHolder.currentCase = null
                // Wróć np. do StronaGlowna
                val intent = Intent(this, StronaGlowna::class.java)
                startActivity(intent)
                finish()
            }
            // Doprecyzuj typ parametru
            .addOnFailureListener { e: Exception ->
                Toast.makeText(this, "Błąd zapisu: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
