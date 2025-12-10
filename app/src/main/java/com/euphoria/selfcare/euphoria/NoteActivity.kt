package com.euphoria.selfcare.euphoria

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.TextView

class NotesActivity : Activity() {

    private var dbManager: DBManager? = null
    private var listView: ListView? = null
    private var adapter: SimpleCursorAdapter? = null

    val from = arrayOf(
        DatabaseHelper._ID,
        DatabaseHelper.SUBJECT,
        DatabaseHelper.DESC
    )

    val to = intArrayOf(
        R.id.id,
        R.id.title,
        R.id.desc
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_notes)

        dbManager = DBManager(this)
        dbManager!!.open()

        val cursor = dbManager!!.fetch()!!

        listView = findViewById(R.id.list_view)
        listView!!.emptyView = findViewById(R.id.empty)

        adapter = SimpleCursorAdapter(
            this,
            R.layout.fragment_notes_view,
            cursor,
            from,
            to,
            0
        )

        listView!!.adapter = adapter

        // Klik item: buka ModifyRecord
        listView!!.setOnItemClickListener { parent, view, position, id ->
            val idTv = view.findViewById<TextView>(R.id.id)
            val titleTv = view.findViewById<TextView>(R.id.title)
            val descTv = view.findViewById<TextView>(R.id.desc)

            val intent = Intent(this, ModifyRecord::class.java)
            intent.putExtra("id", idTv.text.toString())
            intent.putExtra("title", titleTv.text.toString())
            intent.putExtra("desc", descTv.text.toString())
            startActivity(intent)
        }

        // Tombol tambah catatan
        val btnTambah = findViewById<Button>(R.id.tambahCatatan)
        btnTambah.setOnClickListener {
            startActivity(Intent(this, AddRecord::class.java))
        }
    }
}
