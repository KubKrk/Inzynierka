package com.example.inynierka2

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class Furka : AppCompatActivity() {

    private lateinit var buttonPowrot: Button
    private lateinit var buttonNext: Button

    private lateinit var editMarka: EditText
    private lateinit var editModel: EditText
    private lateinit var editRej1: EditText  // Data pierwszej rejestracji
    private lateinit var editBlachy: EditText // Tablice rejestracyjne

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

        // 1. Ustawienie InputFilter dla "marka" i "model" – dozwolone są litery (w tym polskie) oraz spacje.
        val letterFilter = InputFilter { source, start, end, dest, dstart, dend ->
            // Wyrażenie regularne: dopuszczalne są litery angielskie, polskie oraz spacje
            val regex = Regex("^[A-Za-z0-9ąćęłńóśżźĄĆĘŁŃÓŚŻŹ ]+\$")
            for (i in start until end) {
                if (!source[i].toString().matches(regex)) {
                    return@InputFilter ""
                }
            }
            null
        }
        editMarka.filters = arrayOf(letterFilter)
        editModel.filters = arrayOf(letterFilter)

        // 2. Dla "rej1" – data pierwszej rejestracji: pozwalamy tylko cyfry i kropki, ustawiamy długość na 10 znaków
        val dateFilter = InputFilter { source, start, end, dest, dstart, dend ->
            val regex = Regex("[0-9.]")
            for (i in start until end) {
                if (!source[i].toString().matches(regex)) {
                    return@InputFilter ""
                }
            }
            null
        }
        val dateLengthFilter = InputFilter.LengthFilter(10)
        editRej1.filters = arrayOf(dateFilter, dateLengthFilter)

        // 3. Dla "blachy" – tablice rejestracyjne: dozwolone są tylko wielkie litery, cyfry oraz spacje.
        val platesFilter = InputFilter { source, start, end, dest, dstart, dend ->
            // Wyrażenie pozwalające na wielkie litery, cyfry oraz spacje
            val regex = Regex("[A-Z0-9 ]")
            val filtered = StringBuilder()
            for (i in start until end) {
                // Zamieniamy każdy znak na wielką literę
                val char = source[i].toString().uppercase(Locale.getDefault())
                if (!char.matches(regex)) {
                    continue
                }
                filtered.append(char)
            }
            filtered.toString()
        }
        editBlachy.filters = arrayOf(platesFilter)

        // Wczytanie zapisanych danych (jeśli istnieją)
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

            // Sprawdzamy, czy żadne pole nie jest puste
            if (marka.isEmpty() || model.isEmpty() || dataRejestr.isEmpty() || tablice.isEmpty()) {
                Toast.makeText(this, "Uzupełnij dane samochodu (marka, model, data rejestracji, tablice)!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Dodatkowa walidacja – na wypadek, gdyby użytkownik wkleił tekst
            if (!marka.matches(Regex("^[A-Za-ząćęłńóśżźĄĆĘŁŃÓŚŻŹ ]+\$"))) {
                editMarka.error = "Marka może zawierać tylko litery"
                return@setOnClickListener
            }
            if (!model.matches(Regex("^[A-Za-z0-9ąćęłńóśżźĄĆĘŁŃÓŚŻŹ ]+\$"))) {
                editModel.error = "Model może zawierać tylko litery i liczby"
                return@setOnClickListener
            }
            // Walidacja daty w formacie dd.MM.yyyy
            if (!dataRejestr.matches(Regex("^(0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[012])[.](19|20)\\d{2}\$"))) {
                editRej1.error = "Data rejestracji musi być w formacie dd.MM.yyyy"
                return@setOnClickListener
            }
            // Walidacja tablic – tylko wielkie litery, cyfry i spacje
            if (!tablice.matches(Regex("^[A-Z0-9 ]+\$"))) {
                editBlachy.error = "Tablice mogą zawierać tylko wielkie litery, cyfry i spacje"
                return@setOnClickListener
            }

            // Zapis danych do DataHolder
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
