package com.example.inynierka2

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class Podpis : AppCompatActivity() {

    private lateinit var signaturePad: SignaturePad
    private lateinit var buttonPowrot: Button
    private lateinit var buttonClearSignature: Button
    private lateinit var buttonSaveSignature: Button

    // Nie setujemy nic w RTDB – tylko upload do Storage + zapis w DataHolder
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podpis)

        // Inicjalizacja widoków
        buttonPowrot = findViewById(R.id.powrot)
        signaturePad = findViewById(R.id.signaturePad)
        buttonClearSignature = findViewById(R.id.buttonClearSignature)
        buttonSaveSignature = findViewById(R.id.buttonSaveSignature)

        // Przycisk powrot -> wraca do NowyCase
        buttonPowrot.setOnClickListener {
            val cofka = Intent(this, NowyCase::class.java)
            startActivity(cofka)
            finish()
        }

        // Wyczyść
        buttonClearSignature.setOnClickListener {
            signaturePad.clear()
        }

        // Zapisz podpis
        buttonSaveSignature.setOnClickListener {
            if (signaturePad.isEmpty) {
                Toast.makeText(this, "Brak podpisu!", Toast.LENGTH_SHORT).show()
            } else {
                // Konwersja bitmapy do PNG
                val signatureBitmap: Bitmap = signaturePad.signatureBitmap
                val baos = ByteArrayOutputStream()
                signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                val data = baos.toByteArray()

                // Upload do Storage
                val storageRef: StorageReference =
                    storage.reference.child("signatures/${System.currentTimeMillis()}.png")

                storageRef.putBytes(data)
                    .addOnSuccessListener {
                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            // Zapisz URL w DataHolder (ale jeszcze nie w RTDB)
                            DataHolder.currentCase?.signatureUrl = uri.toString()
                            Toast.makeText(this, "Podpis zapisany!", Toast.LENGTH_SHORT).show()

                            // Wróć do NowyCase, user może dalej edytować albo zakończyć
                            val intent = Intent(this, NowyCase::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    .addOnFailureListener { e: Exception ->
                        Toast.makeText(this, "Błąd uploadu: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
