package com.example.inynierka2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CaseDetailsActivity : AppCompatActivity() {

    companion object {
        // Klucze do Intent extras
        const val EXTRA_CLIENT_NAME = "clientName"
        const val EXTRA_CLIENT_SURNAME = "clientSurname"
        const val EXTRA_CAR_MARK = "carMark"
        const val EXTRA_CAR_PLATES = "carPlates"
        const val EXTRA_POSZKOD_INS = "poszkodInsurance"
        // ... i co jeszcze potrzebujesz
    }

    private lateinit var buttonBack: Button
    private lateinit var textClientInfo: TextView
    private lateinit var textCarInfo: TextView
    private lateinit var textInsuranceInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_case_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_case_details)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonBack = findViewById(R.id.buttonBack)
        textClientInfo = findViewById(R.id.textClientInfo)
        textCarInfo = findViewById(R.id.textCarInfo)
        textInsuranceInfo = findViewById(R.id.textInsuranceInfo)

        buttonBack.setOnClickListener {
            // wracaj do CaseList (lub co chcesz)
            val intent = Intent(this, CaseList::class.java)
            startActivity(intent)
            finish()
        }

        // Pobieramy dane z Intent
        val clientName = intent.getStringExtra(EXTRA_CLIENT_NAME) ?: ""
        val clientSurname = intent.getStringExtra(EXTRA_CLIENT_SURNAME) ?: ""
        val carMark = intent.getStringExtra(EXTRA_CAR_MARK) ?: ""
        val carPlates = intent.getStringExtra(EXTRA_CAR_PLATES) ?: ""
        val poszkod = intent.getStringExtra(EXTRA_POSZKOD_INS) ?: ""

        // Wyświetlamy w textView
        textClientInfo.text = "Klient: $clientName $clientSurname"
        textCarInfo.text = "Samochód: $carMark, $carPlates"
        textInsuranceInfo.text = "Ubezp. poszkodowanego: $poszkod"
    }
}
