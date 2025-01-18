package com.example.inynierka2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PoprzedniCase : AppCompatActivity() {

    private lateinit var buttonPowrot: Button
    private lateinit var textOstatniCase: TextView

    private val dbRef = FirebaseDatabase.getInstance().getReference("cases")
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_poprzedni_case)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonPowrot = findViewById(R.id.powrot)
        textOstatniCase = findViewById(R.id.textOstatniCase)

        buttonPowrot.setOnClickListener {
            val cofka = Intent(this, StronaGlowna::class.java)
            startActivity(cofka)
        }

        loadLastCaseForUser()
    }

    @SuppressLint("SetTextI18n")
    private fun loadLastCaseForUser() {
        val user = auth.currentUser
        if (user == null) {
            textOstatniCase.text = "Brak zalogowanego użytkownika!"
            return
        }

        // Najnowszy (ostatni) = limitToLast(1)
        dbRef.orderByChild("ownerId").equalTo(user.uid).limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // w pętli for, bo .limitToLast(1) też zwraca 1..n elementów
                        for (child in snapshot.children) {
                            val caseData = child.getValue(CaseData::class.java)
                            if (caseData != null) {
                                textOstatniCase.text = buildString {
                                    append("Ostatni case:\n")
                                    append("Imię: ${caseData.clientName}\n")
                                    append("Nazwisko: ${caseData.clientSurname}\n")
                                    append("Samochód: ${caseData.carMark}, ${caseData.carPlates}\n")
                                    append("Ubezp.poszkod: ${caseData.poszkodInsurance}\n")
                                    append("Ubezp.sprawcy: ${caseData.sprawcaInsurance}\n")
                                    // i tak dalej
                                }
                            }
                        }
                    } else {
                        textOstatniCase.text = "Nie znaleziono żadnych case'ów."
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("PoprzedniCase", "Błąd pobierania: ${error.message}")
                }
            })
    }
}
