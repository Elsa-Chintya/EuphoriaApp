package com.euphoria.selfcare.euphoria

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object EditJournalDialog {

    fun show(context: Context, journal: Journal) {
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        val etAffirmation = EditText(context)
        etAffirmation.hint = "Affirmation"
        etAffirmation.setText(journal.affirmation)
        layout.addView(etAffirmation)

        val etReflection = EditText(context)
        etReflection.hint = "Reflection"
        etReflection.setText(journal.reflection)
        layout.addView(etReflection)

        AlertDialog.Builder(context)
            .setTitle("Edit Jurnal")
            .setView(layout)
            .setPositiveButton("Simpan") { _, _ ->
                val newAff = etAffirmation.text.toString().trim()
                val newRef = etReflection.text.toString().trim()

                if (newAff.isEmpty() || newRef.isEmpty()) {
                    Toast.makeText(context, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val user = FirebaseAuth.getInstance().currentUser

                val updates = mapOf(
                    "affirmation" to newAff,
                    "reflection" to newRef
                )

                FirebaseDatabase.getInstance()
                    .getReference("journals")
                    .child(user?.uid ?: "guest")
                    .child(journal.id ?: "")
                    .updateChildren(updates)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Jurnal diperbarui!", Toast.LENGTH_SHORT).show()
                    }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
