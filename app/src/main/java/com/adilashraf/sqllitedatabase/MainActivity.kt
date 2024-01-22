package com.adilashraf.sqllitedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adilashraf.sqllitedatabase.adapter.MyContactAdapter
import com.adilashraf.sqllitedatabase.database.ContactDBHelper
import com.adilashraf.sqllitedatabase.model.ContactModel

class MainActivity : AppCompatActivity() {

    private lateinit var btnAdd: Button
    private lateinit var recyclerView: RecyclerView
    private var dbHelper: ContactDBHelper? = null
    private var contactList: List<ContactModel>  = ArrayList()
    private var adapter: MyContactAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAdd = findViewById(R.id.addContact)
        recyclerView = findViewById(R.id.recyclerView)

        dbHelper = ContactDBHelper(applicationContext)
        getAllContactsFromDatabase()

        btnAdd.setOnClickListener{
            val i = Intent(this@MainActivity, AddContact::class.java )
            startActivity(i)

        }
    }

    private fun getAllContactsFromDatabase(){
        contactList = dbHelper!!.getAllContacts()
        adapter = MyContactAdapter(applicationContext, contactList)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

    }
}