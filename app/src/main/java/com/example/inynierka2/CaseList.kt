package com.example.inynierka2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CaseList : AppCompatActivity() {

    private lateinit var buttonPowrot: Button
    private lateinit var recyclerViewCases: RecyclerView
    private val casesList = ArrayList<CaseData>()
    private lateinit var adapter: CaseAdapter

    private val dbRef = FirebaseDatabase.getInstance().getReference("cases")
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_case_list)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonPowrot = findViewById(R.id.powrot)
        recyclerViewCases = findViewById(R.id.recyclerViewCases)

        recyclerViewCases.layoutManager = LinearLayoutManager(this)
        adapter = CaseAdapter(casesList)
        recyclerViewCases.adapter = adapter

        buttonPowrot.setOnClickListener {
            startActivity(Intent(this, StronaGlowna::class.java))
        }

        loadAllCasesForUser()
    }

    private fun loadAllCasesForUser() {
        val user = auth.currentUser
        if (user == null) {
            // brak usera
            return
        }
        // Pokaż wszystkie case z ownerId = user.uid
        dbRef.orderByChild("ownerId").equalTo(user.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    casesList.clear()
                    for (child in snapshot.children) {
                        val case = child.getValue(CaseData::class.java)
                        if (case != null) {
                            casesList.add(case)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("CaseList", "Błąd pobierania: ${error.message}")
                }
            })
    }
}
