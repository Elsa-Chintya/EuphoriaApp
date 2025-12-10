package com.euphoria.selfcare.euphoria

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.euphoria.selfcare.euphoria.NotesActivity

class AddRecord : Activity(), View.OnClickListener {

    private var addTodoBtn: Button? = null
    private var subjectEditText: EditText? = null
    private var descEditText: EditText? = null
    private var dbManager: DBManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Add Record"
        setContentView(R.layout.fragment_notes_add)

        subjectEditText = findViewById(R.id.subject_edittext)
        descEditText = findViewById(R.id.description_edittext)
        addTodoBtn = findViewById(R.id.add_record)

        dbManager = DBManager(this)
        dbManager?.open()

        addTodoBtn?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.add_record) {

            val name = subjectEditText?.text.toString()
            val desc = descEditText?.text.toString()

            dbManager?.insert(name, desc)

            // 🔹 Kembali ke NoteFragment (Activity) — FIX
            val main = Intent(this@AddRecord, NotesActivity::class.java)
            startActivity(main)


            startActivity(main)
        }
    }
}
