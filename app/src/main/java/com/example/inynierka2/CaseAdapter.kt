package com.example.inynierka2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CaseAdapter(private val casesList: List<CaseData>) :
    RecyclerView.Adapter<CaseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textDesc: TextView = itemView.findViewById(R.id.textDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_case, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = casesList[position]
        // Tytuł -> "Imię Nazwisko"
        holder.textTitle.text = "${item.clientName} ${item.clientSurname}"
        // Desc -> "Samochód: ... / Tablice: ..."
        holder.textDesc.text = "Samochód: ${item.carMark}, ${item.carPlates}"
    }

    override fun getItemCount(): Int = casesList.size
}
