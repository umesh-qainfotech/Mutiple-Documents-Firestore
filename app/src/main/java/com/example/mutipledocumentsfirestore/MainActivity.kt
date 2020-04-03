package com.example.mutipledocumentsfirestore

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val ref : CollectionReference = db.collection("User Data")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_button.setOnClickListener { onAdd() }
        load_button.setOnClickListener { onLoad() }
    }

    private fun onAdd()
    {
        val title : String = findViewById<TextView>(R.id.title).text.toString()
        val desc : String = findViewById<TextView>(R.id.description).text.toString()

        val data = hashMapOf(
            "Title" to title,
            "Description" to desc
        )

        ref.add(data).addOnSuccessListener {
            Toast.makeText(this,"$title : $desc added successfully",Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {e ->
                Toast.makeText(this,"$title : $desc failed in added",Toast.LENGTH_SHORT).show()
                Log.e("TAG",e.toString())
            }
    }

    private fun onLoad()
    {
        ref.get().addOnSuccessListener { result ->
            var data : String = ""
            result.forEach { queryDocumentSnapshot : QueryDocumentSnapshot ->
                val Title : String? = queryDocumentSnapshot.get("Title") as? String
                val Desc : String? = queryDocumentSnapshot.get("Description") as? String
                val Id : String = queryDocumentSnapshot.id
                data += "Id : $Id \n Title : $Title \n Description : $Desc \n\n"
            }
            load_text.text = data
        }
            .addOnFailureListener{ e ->
                Log.i("TAG",e.toString())
            }
    }
}
