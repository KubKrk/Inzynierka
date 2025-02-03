package com.example.inynierka2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Logowanie : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var inputLogin: EditText
    private lateinit var inputPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var forgotPasswordButton: Button
    private lateinit var rememberMeCheckBox: CheckBox
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicjalizacja Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Powiąż pola tekstowe, przyciski i CheckBox z XML
        inputLogin = findViewById(R.id.nazwa)
        inputPassword = findViewById(R.id.haslo)
        loginButton = findViewById(R.id.zaloguj12)
        registerButton = findViewById(R.id.zarej)
        forgotPasswordButton = findViewById(R.id.zapomniane)
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox)

        // Inicjalizacja SharedPreferences
        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

        // Sprawdź, czy użytkownik zaznaczył "Zapamiętaj mnie"
        checkRememberMe()

        // Obsługa przycisku logowania
        loginButton.setOnClickListener {
            val login = inputLogin.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Podaj nazwę użytkownika i hasło", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Wywołanie metody logowania
            loginUser(login, password)
        }

        // Obsługa przycisku rejestracji
        registerButton.setOnClickListener {
            val registerIntent = Intent(this, Rejestracja::class.java)
            startActivity(registerIntent)
        }

        // Obsługa przycisku zapomnianego hasła
        forgotPasswordButton.setOnClickListener {
            val forgot = Intent(this, ResetHasla::class.java)
            startActivity(forgot)
        }
    }

    // Metoda logowania użytkownika
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Logowanie pomyślne
                    Toast.makeText(this, "Logowanie udane", Toast.LENGTH_SHORT).show()

                    // Zapamiętaj użytkownika, jeśli zaznaczył CheckBox
                    if (rememberMeCheckBox.isChecked) {
                        val editor = sharedPreferences.edit()
                        editor.putString("LOGIN", email)
                        editor.putBoolean("REMEMBER_ME", true)
                        editor.apply()
                    }

                    val intent = Intent(this, StronaGlowna::class.java) // Zmień na odpowiednią aktywność
                    startActivity(intent)
                    finish() // Zakończ bieżącą aktywność
                } else {
                    // Logowanie nieudane
                    Toast.makeText(this, "Logowanie nieudane: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Metoda sprawdzająca, czy użytkownik wcześniej zaznaczył opcję "Zapamiętaj mnie"
    private fun checkRememberMe() {
        val rememberMe = sharedPreferences.getBoolean("REMEMBER_ME", false)
        if (rememberMe) {
            val savedLogin = sharedPreferences.getString("LOGIN", "")
            inputLogin.setText(savedLogin)
        }
    }
}
