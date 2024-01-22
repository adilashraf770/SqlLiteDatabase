package com.adilashraf.sqllitedatabase.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
 import com.adilashraf.sqllitedatabase.model.ContactModel

class ContactDBHelper(private val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val  DATABASE_NAME = "UserDatabase"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "users"
        private const val COL_NUMBER = "number"
        private const val COL_DOB = "dob"
        private const val COL_NAME = "name"
        private const val TAG = "ContactDBHelper"
    }


    override fun onCreate(db: SQLiteDatabase?) {
         val query = "CREATE TABLE $TABLE_NAME ($COL_NUMBER INTEGER PRIMARY KEY, $COL_NAME TEXT, $COL_DOB TEXT )"
        db?.execSQL(query)
        Log.d(TAG, "onCreate: Table is created")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
         val query = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(query)
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getAllContacts(): List<ContactModel>{
        val contactList = ArrayList<ContactModel>()
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
         val cursor = db.rawQuery(query, null)
        if(cursor!= null && cursor.moveToFirst()){
            do {
                val contacts = ContactModel()
                contacts.number = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_NUMBER)))
                contacts.name =  cursor.getString(cursor.getColumnIndex(COL_NAME))
                contacts.dob =  cursor.getString(cursor.getColumnIndex(COL_DOB))
                contactList.add(contacts)
            }while (cursor.moveToNext())
        }
        cursor.close()

        return contactList
    }


    // Insert Data
    fun insertData(contact: ContactModel): Boolean{
       val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NUMBER, contact.number)
        values.put(COL_NAME, contact.name)
        values.put(COL_DOB, contact.dob)

        val insert = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$insert") != -1)
    }



    @SuppressLint("Range")
    fun getSingleContact(id: Int): ContactModel {
        val contact =  ContactModel()
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME where $COL_NUMBER = $id"
        val cursor = db.rawQuery(query, null)
        cursor?.moveToNext()
        contact.number = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_NUMBER)))
        contact.name =  cursor.getString(cursor.getColumnIndex(COL_NAME))
        contact.dob =  cursor.getString(cursor.getColumnIndex(COL_DOB))
        cursor.close()
        return contact
    }


    fun deleteContact(number: Int): Boolean{
        val db = this.writableDatabase
        val delete = db.delete(TABLE_NAME, "$COL_NUMBER  ?=", arrayOf(number.toString()))
        db.close()
        return (Integer.parseInt("$delete") != -1)
    }

   fun updateContact(contact: ContactModel): Boolean{
        val db = this.writableDatabase
       val values = ContentValues()
        values.put("name", contact.name)
        values.put("dob", contact.dob)

        val update = db.update(TABLE_NAME, values, "$COL_NUMBER  ?=", arrayOf(contact.number.toString()))
        db.close()
        return (Integer.parseInt("$update") != -1)
    }
}