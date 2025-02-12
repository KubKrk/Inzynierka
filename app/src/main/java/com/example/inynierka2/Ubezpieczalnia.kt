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

class Ubezpieczalnia : AppCompatActivity() {

    private lateinit var buttonPowrot: Button
    private lateinit var buttonNext: Button

    private lateinit var editPoszkodUbezp: EditText   // Ubezpieczenie poszkodowanego
    private lateinit var editSprawcaUbezp: EditText     // Ubezpieczenie sprawcy
    private lateinit var editBlachy1: EditText          // Tablice rejestracyjne sprawcy
    private lateinit var editMarka1: EditText           // Marka pojazdu sprawcy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ubezpieczalnia)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonPowrot = findViewById(R.id.powrot)
        buttonNext = findViewById(R.id.buttonCreateCase)

        editPoszkodUbezp = findViewById(R.id.ubezp)
        editSprawcaUbezp = findViewById(R.id.ubezp1)
        editBlachy1 = findViewById(R.id.blachy1)
        editMarka1 = findViewById(R.id.marka1)

        // Filtr dla pól poszkod, sprawca oraz marka – dozwolone są litery, cyfry oraz spacje (np. LINK4)
        val alphaNumFilter = InputFilter { source, start, end, dest, dstart, dend ->
            // Wyrażenie regularne dopuszcza litery (angielskie i polskie), cyfry i spacje
            val regex = Regex("^[A-Za-z0-9ąćęłńóśżźĄĆĘŁŃÓŚŻŹ ]+\$")
            for (i in start until end) {
                if (!source[i].toString().matches(regex)) {
                    return@InputFilter ""
                }
            }
            null
        }
        // Ustawiamy filtr dla pól ubezpieczeniowych
        editPoszkodUbezp.filters = arrayOf(alphaNumFilter)
        editSprawcaUbezp.filters = arrayOf(alphaNumFilter)
        editMarka1.filters = arrayOf(alphaNumFilter)

        // Filtr dla tablic rejestracyjnych – dozwolone są tylko wielkie litery, cyfry oraz spacje.
        // Dodatkowo każdy znak jest konwertowany na wielką literę.
        val platesFilter = InputFilter { source, start, end, dest, dstart, dend ->
            val regex = Regex("[A-Z0-9 ]")
            val filtered = StringBuilder()
            for (i in start until end) {
                val charUpper = source[i].toString().uppercase(Locale.getDefault())
                if (!charUpper.matches(regex)) {
                    continue
                }
                filtered.append(charUpper)
            }
            filtered.toString()
        }
        editBlachy1.filters = arrayOf(platesFilter)

        // Wczytanie zapisanych danych, jeśli istnieją
        val currentCase = DataHolder.currentCase
        if (currentCase != null) {
            editPoszkodUbezp.setText(currentCase.poszkodInsurance)
            editSprawcaUbezp.setText(currentCase.sprawcaInsurance)
            editBlachy1.setText(currentCase.sprawcaCarPlates)
            editMarka1.setText(currentCase.sprawcaCarMark)
        }

        buttonPowrot.setOnClickListener {
            val cofka = Intent(this, Furka::class.java)
            startActivity(cofka)
        }

        buttonNext.setOnClickListener {
            val poszkod = editPoszkodUbezp.text.toString().trim()
            val sprawca = editSprawcaUbezp.text.toString().trim()
            val tablice1 = editBlachy1.text.toString().trim()
            val marka1str = editMarka1.text.toString().trim()

            // Sprawdzenie, czy żadne pole nie jest puste
            if (poszkod.isEmpty() || sprawca.isEmpty() || tablice1.isEmpty() || marka1str.isEmpty()) {
                Toast.makeText(this, "Uzupełnij wszystkie pola ubezpieczalni!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Dodatkowa walidacja – na wypadek, gdyby użytkownik wkleił tekst
            if (!poszkod.matches(Regex("^[A-Za-z0-9ąćęłńóśżźĄĆĘŁŃÓŚŻŹ ]+\$"))) {
                editPoszkodUbezp.error = "To pole może zawierać tylko litery, cyfry i spacje"
                return@setOnClickListener
            }
            if (!sprawca.matches(Regex("^[A-Za-z0-9ąćęłńóśżźĄĆĘŁŃÓŚŻŹ ]+\$"))) {
                editSprawcaUbezp.error = "To pole może zawierać tylko litery, cyfry i spacje"
                return@setOnClickListener
            }
            if (!marka1str.matches(Regex("^[A-Za-z0-9ąćęłńóśżźĄĆĘŁŃÓŚŻŹ ]+\$"))) {
                editMarka1.error = "Marka może zawierać tylko litery, cyfry i spacje"
                return@setOnClickListener
            }
            if (!tablice1.matches(Regex("^[A-Z0-9 ]+\$"))) {
                editBlachy1.error = "Tablice mogą zawierać tylko wielkie litery, cyfry i spacje"
                return@setOnClickListener
            }

            if (DataHolder.currentCase == null) {
                DataHolder.currentCase = CaseData()
            }
            val case = DataHolder.currentCase!!
            case.poszkodInsurance = poszkod
            case.sprawcaInsurance = sprawca
            case.sprawcaCarPlates = tablice1
            case.sprawcaCarMark = marka1str

            val next = Intent(this, Podpis::class.java)
            startActivity(next)
        }
    }
}
