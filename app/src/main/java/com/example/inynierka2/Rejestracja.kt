package com.example.inynierka2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Rejestracja : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rejestracja)

        val butcofka = findViewById<Button>(R.id.powrot)
        butcofka.setOnClickListener{
            val cofka = Intent(this, Logowanie::class.java)
            startActivity(cofka)
        }

        // Inicjalizacja Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Powiąż pola tekstowe i przycisk z XML
        emailField = findViewById(R.id.nazwa1)
        passwordField = findViewById(R.id.haslo2)
        registerButton = findViewById(R.id.Zarejestruj)

        // Obsługa przycisku rejestracji
        registerButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Podaj email i hasło", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Wywołanie metody rejestracji
            createAccount(email, password)
        }
    }

    // Metoda do tworzenia nowego użytkownika
    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Rejestracja pomyślna
                    Log.d("Rejestracja", "createUserWithEmail:success")
                    Toast.makeText(this, "Rejestracja udana", Toast.LENGTH_SHORT).show()

                    // Cofnięcie do strony logowania
                    val pomyslnie = Intent(this, Logowanie::class.java)
                    startActivity(pomyslnie)
                    finish() // Zakończ bieżącą aktywność
                } else {
                    // Rejestracja nieudana
                    Log.w("Rejestracja", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Rejestracja nieudana: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }

            }
    }
}
