package com.adilashraf.sqllitedatabase


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.adilashraf.sqllitedatabase.database.ContactDBHelper
import com.adilashraf.sqllitedatabase.model.ContactModel

class AddContact : AppCompatActivity() {
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button
    private lateinit var editName: EditText
    private lateinit var editDob: EditText
    private lateinit var editNumber: EditText
    private lateinit var dbHelper: ContactDBHelper
    private var isEditMode: Boolean = false
    private var TAG: String = "AddContact"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        btnSave = findViewById(R.id.btnSave)
        btnDelete = findViewById(R.id.btnDelete)
        editName = findViewById(R.id.editName)
        editDob = findViewById(R.id.editDob)
        editNumber = findViewById(R.id.editNumber)

        dbHelper = ContactDBHelper(applicationContext)
        if (intent != null && intent.getStringExtra("Mode") == "E"){
            // update
            isEditMode = true
            btnSave.text = "Update"
            btnDelete.visibility = View.VISIBLE

            val contact: ContactModel = dbHelper.getSingleContact(intent.getIntExtra("number", 0))
           editName.setText(contact.name)
           editNumber.setText(contact.number)
           editDob.setText(contact.dob)
        }else{
            // insert
            isEditMode = false
            btnSave.text = "Save Data"
            btnDelete.visibility = View.GONE
        }

        btnSave.setOnClickListener{
            val success: Boolean
            val contact = ContactModel()
             if (isEditMode){
                // update
                 contact.number = intent.getIntExtra("number", 0)
                 contact.name = editName.text.toString()
                 contact.dob = editDob.text.toString()
                 success = dbHelper.updateContact(contact)
                 Log.d(TAG, "Data is Updated")
            }else{
                //insert
                contact.name = editName.text.toString()
                contact.number = editNumber.text.toString().toInt()
                contact.dob = editNumber.text.toString()
                 success = dbHelper.insertData(contact)
                 Log.d(TAG, "Data is inserted")
            }

            if (success){
                Toast.makeText(applicationContext, "Data is inserted", Toast.LENGTH_SHORT).show()
                val i = Intent(this@AddContact, MainActivity::class.java)
                startActivity(i)
            }else{
                Toast.makeText(applicationContext, "Data is NOT inserted", Toast.LENGTH_SHORT).show()
            }

        }


        btnDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(applicationContext)
                .setTitle("Info")
                .setMessage("Please Enter yes if you want delete contact?")
                .setPositiveButton("Yes") { dialog, _ ->
                    val success =
                        dbHelper.deleteContact(intent.getIntExtra("number", 0))
                    if (success)
                        finish()
                    dialog.dismiss()
                }.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            dialog.show()
            Log.d(TAG, "Data is Deletd")
        }







    }
}