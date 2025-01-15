package com.example.inynierka2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Ubezpieczalnia : AppCompatActivity() {

    private lateinit var buttonPowrot: Button
    private lateinit var buttonNext: Button

    private lateinit var editPoszkodUbezp: EditText // ubezp
    private lateinit var editSprawcaUbezp: EditText // ubezp1
    private lateinit var editBlachy1: EditText      // blachy1
    private lateinit var editMarka1: EditText       // marka1

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
            if (DataHolder.currentCase == null) {
                DataHolder.currentCase = CaseData()
            }
            val case = DataHolder.currentCase!!

            case.poszkodInsurance = editPoszkodUbezp.text.toString().trim()
            case.sprawcaInsurance = editSprawcaUbezp.text.toString().trim()
            case.sprawcaCarPlates = editBlachy1.text.toString().trim()
            case.sprawcaCarMark = editMarka1.text.toString().trim()

            // Dalej do Podpis
            val next = Intent(this, Podpis::class.java)
            startActivity(next)
        }
    }
}
