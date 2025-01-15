package com.example.inynierka2

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inynierka2.DataHolder
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class Podpis : AppCompatActivity() {

    private lateinit var signaturePad: SignaturePad
    private lateinit var buttonClearSignature: Button
    private lateinit var buttonSaveSignature: Button

    // Definiujemy zwykłą zmienną zamiast by lazy:
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podpis)

        signaturePad = findViewById(R.id.signaturePad)
        buttonClearSignature = findViewById(R.id.buttonClearSignature)
        buttonSaveSignature = findViewById(R.id.buttonSaveSignature)

        buttonClearSignature.setOnClickListener {
            signaturePad.clear()
        }

        buttonSaveSignature.setOnClickListener {
            if (signaturePad.isEmpty) {
                Toast.makeText(this, "Brak podpisu!", Toast.LENGTH_SHORT).show()
            } else {
                // Konwersja podpisu do bajtów
                val signatureBitmap: Bitmap = signaturePad.signatureBitmap
                val baos = ByteArrayOutputStream()
                signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                val data = baos.toByteArray()

                // Upload do Storage
                val storageRef: StorageReference =
                    storage.reference.child("signatures/${System.currentTimeMillis()}.png")

                storageRef.putBytes(data)
                    .addOnSuccessListener {
                        // Po sukcesie pobieramy downloadUrl i zapisujemy do DataHolder
                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            DataHolder.currentCase?.signatureUrl = uri.toString()
                            Toast.makeText(this, "Podpis zapisany!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                    // Jawnie określamy typ parametru w razie problemu z inferowaniem
                    .addOnFailureListener { e: Exception ->
                        Toast.makeText(this, "Błąd uploadu: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
