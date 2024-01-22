package com.adilashraf.sqllitedatabase.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adilashraf.sqllitedatabase.AddContact
import com.adilashraf.sqllitedatabase.R
import com.adilashraf.sqllitedatabase.model.ContactModel


class MyContactAdapter(private val context: Context,private val contactList: List<ContactModel> )
    : RecyclerView.Adapter<MyContactAdapter.ContactViewHolder>() {


    inner class ContactViewHolder( view: View): RecyclerView.ViewHolder(view) {
        val txtName: TextView
        val txtDob: TextView
        val txtNumber: TextView
        val btnEdit: Button

        init {
             txtName = view.findViewById(R.id.txtName)
            txtDob = view.findViewById(R.id.txtDob)
            txtNumber = view.findViewById(R.id.txtNumber)
            btnEdit = view.findViewById(R.id.btnEdit)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.contact_layout,parent, false )
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contactPos = contactList[position]
        holder.txtName.text = contactPos.name
        holder.txtNumber.text = contactPos.number.toString()
        holder.txtDob.text = contactPos.dob

        holder.btnEdit.setOnClickListener {
            val i = Intent(context, AddContact::class.java)
            i.putExtra("Mode", "E")
            i.putExtra("number", contactPos.number)
            context.startActivities(arrayOf(i))

        }
    }
}
