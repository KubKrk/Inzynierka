package com.example.inynierka2

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.gcacace.signaturepad.views.SignaturePad

class Podpis : AppCompatActivity() {

    private lateinit var signaturePad: SignaturePad
    private lateinit var buttonClearSignature: Button
    private lateinit var buttonSaveSignature: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podpis)

        // Inicjalizacja widoków:
        signaturePad = findViewById(R.id.signaturePad)
        buttonClearSignature = findViewById(R.id.buttonClearSignature)
        buttonSaveSignature = findViewById(R.id.buttonSaveSignature)

        // Obsługa przycisku "Wyczyść podpis"
        buttonClearSignature.setOnClickListener {
            signaturePad.clear()
        }

        // Obsługa przycisku "Zapisz podpis"
        buttonSaveSignature.setOnClickListener {
            if (signaturePad.isEmpty) {
                Toast.makeText(this, "Brak podpisu!", Toast.LENGTH_SHORT).show()
            } else {
                // Przykład: pobieramy bitmapę z podpisu
                val signatureBitmap: Bitmap = signaturePad.signatureBitmap

                // Zrób z bitmapą, co potrzebujesz (zapis do pliku, upload do Firebase Storage itd.)
                Toast.makeText(this, "Podpis pobrany jako bitmapa!", Toast.LENGTH_SHORT).show()

                // Po zapisaniu (np. w bazie) możesz wrócić do poprzedniego ekranu:
                // finish()
            }
        }
    }
}
