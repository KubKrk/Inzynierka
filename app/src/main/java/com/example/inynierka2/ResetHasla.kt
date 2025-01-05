package com.example.inynierka2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ResetHasla : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailField: EditText
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_hasla)

        val butcofka = findViewById<Button>(R.id.powrot)
        butcofka.setOnClickListener{
            val cofka = Intent(this, Logowanie::class.java)
            startActivity(cofka)
        }

        // Inicjalizacja Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Powiązanie elementów z layoutem
        emailField = findViewById(R.id.email_input)
        resetButton = findViewById(R.id.reset_button)

        // Obsługa przycisku resetowania hasła
        resetButton.setOnClickListener {
            val email = emailField.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Podaj adres email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            resetPassword(email)
        }
    }


    // Funkcja do resetowania hasła
    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email do resetowania hasła wysłany", Toast.LENGTH_SHORT).show()
                    finish() // Zamyka ekran resetowania i wraca do logowania
                } else {
                    Toast.makeText(this, "Błąd: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
