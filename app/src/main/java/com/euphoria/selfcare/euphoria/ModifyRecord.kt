package com.euphoria.selfcare.euphoria

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.euphoria.selfcare.euphoria.NotesActivity



class ModifyRecord : Activity(), View.OnClickListener {
    private var titleText: EditText? = null
    private var updateBtn: Button? = null
    private var deleteBtn: Button? = null
    private var descText: EditText? = null
    private var _id: Long = 0
    private var dbManager: DBManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Modify Record"
        setContentView(R.layout.fragment_notes_modify)
        dbManager = DBManager(this)
        dbManager!!.open()
        titleText = findViewById<View>(R.id.subject_edittext) as EditText
        descText = findViewById<View>(R.id.description_edittext) as EditText
        updateBtn = findViewById<View>(R.id.btn_update) as Button
        deleteBtn = findViewById<View>(R.id.btn_delete) as Button
        val intent = intent
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("title")
        val desc = intent.getStringExtra("desc")
        _id = id!!.toLong()
        titleText!!.setText(name)
        descText!!.setText(desc)
        updateBtn!!.setOnClickListener(this)
        deleteBtn!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_update -> {
                val title = titleText!!.text.toString()
                val desc = descText!!.text.toString()
                dbManager!!.update(_id, title, desc)
                returnHome()
            }
            R.id.btn_delete -> {
                dbManager!!.delete(_id)
                returnHome()
            }
        }
    }

    fun returnHome() {
        val home_intent = Intent(applicationContext, NotesActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(home_intent)
    }
}