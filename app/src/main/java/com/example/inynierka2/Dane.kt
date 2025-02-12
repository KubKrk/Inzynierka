package com.example.inynierka2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.inputmethod.InputMethodManager
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

        // Ustawienie InputFilter dla pola imienia i nazwiska – dozwolone są litery (w tym polskie znaki) oraz spacja
        val letterFilter = InputFilter { source, start, end, dest, dstart, dend ->
            // Wyrażenie regularne pozwala na litery A-Z, a-z, polskie znaki oraz spacje
            val regex = Regex("^[A-Za-ząćęłńóśżźĄĆĘŁŃÓŚŻŹ ]+\$")
            for (i in start until end) {
                if (!source[i].toString().matches(regex)) {
                    return@InputFilter ""
                }
            }
            null
        }
        editTextClientName.filters = arrayOf(letterFilter)
        editTextClientSurname.filters = arrayOf(letterFilter)

        // Ustawienie InputFilter dla pola PESEL – tylko cyfry i maksymalnie 11 znaków
        val digitFilter = InputFilter { source, start, end, dest, dstart, dend ->
            val regex = Regex("\\d")
            for (i in start until end) {
                if (!source[i].toString().matches(regex)) {
                    return@InputFilter ""
                }
            }
            null
        }
        val lengthFilterPesel = InputFilter.LengthFilter(11)
        editTextExtraInfo.filters = arrayOf(digitFilter, lengthFilterPesel)

        // Ustawienie InputFilter dla pola numeru telefonu – tylko cyfry i maksymalnie 9 znaków
        val lengthFilterPhone = InputFilter.LengthFilter(9)
        editTextClientPhone.filters = arrayOf(digitFilter, lengthFilterPhone)

        // Jeśli posiadasz już dane w DataHolder, ustaw je w odpowiednich polach
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

            // Sprawdź, czy wszystkie pola są uzupełnione
            if (imie.isEmpty() || nazwisko.isEmpty() || pesel.isEmpty() || telefon.isEmpty()) {
                Toast.makeText(this, "Uzupełnij imię, nazwisko, PESEL i telefon!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Dodatkowa walidacja – na wypadek, gdy użytkownik wkleił tekst lub coś ominęło InputFilter
            if (!imie.matches(Regex("^[A-Za-ząćęłńóśżźĄĆĘŁŃÓŚŻŹ ]+\$"))) {
                editTextClientName.error = "Imię może zawierać tylko litery"
                return@setOnClickListener
            }
            if (!pesel.matches(Regex("^\\d{11}\$"))) {
                editTextExtraInfo.error = "PESEL musi składać się z 11 cyfr"
                return@setOnClickListener
            }
            if (!telefon.matches(Regex("^\\d{9}\$"))) {
                editTextClientPhone.error = "Numer telefonu musi składać się z 9 cyfr"
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
