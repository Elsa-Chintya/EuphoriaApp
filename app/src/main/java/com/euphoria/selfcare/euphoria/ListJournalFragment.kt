package com.euphoria.selfcare.euphoria

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.euphoria.selfcare.euphoria.databinding.FragmentListJournalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ListJournalFragment : Fragment() {

    private var _binding: FragmentListJournalBinding? = null
    private val binding get() = _binding!!

    private lateinit var ref: DatabaseReference
    private lateinit var adapter: JournalAdapter
    private val journalList = ArrayList<Journal>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListJournalBinding.inflate(inflater, container, false)

        binding.rvJournal.layoutManager = LinearLayoutManager(requireContext())

        adapter = JournalAdapter(journalList) { journal ->
            showActionDialog(journal)
        }
        binding.rvJournal.adapter = adapter

        loadJournals()

        return binding.root
    }

    private fun loadJournals() {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        ref = FirebaseDatabase.getInstance().getReference("journals").child(uid)

        // Tidak perlu equalTo, karena sudah di node uid
        ref.orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                journalList.clear()

                for (child in snapshot.children) {
                    val journal = child.getValue(Journal::class.java)
                    if (journal != null) journalList.add(journal)
                }

                journalList.reverse() // terbaru muncul di atas
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun showActionDialog(journal: Journal) {
        val options = arrayOf("Edit Jurnal", "Hapus Jurnal")

        AlertDialog.Builder(requireContext())
            .setTitle("Pilih Aksi")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openEditDialog(journal)
                    1 -> deleteJournal(journal)
                }
            }
            .show()
    }

    private fun openEditDialog(journal: Journal) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_journal, null)

        val etDate = dialogView.findViewById<android.widget.EditText>(R.id.etDate)
        val etAffirmation = dialogView.findViewById<android.widget.EditText>(R.id.etAffirmation)
        val etReflection = dialogView.findViewById<android.widget.EditText>(R.id.etReflection)

        etDate.setText(journal.date)
        etAffirmation.setText(journal.affirmation)
        etReflection.setText(journal.reflection)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Jurnal")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val uid = FirebaseAuth.getInstance().currentUser!!.uid

                val updatedJournal = Journal(
                    journal.id,
                    uid,
                    etDate.text.toString(),
                    etAffirmation.text.toString(),
                    etReflection.text.toString(),
                    System.currentTimeMillis()
                )

                FirebaseDatabase.getInstance().getReference("journals")
                    .child(uid).child(journal.id!!)
                    .setValue(updatedJournal)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun deleteJournal(journal: Journal) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        FirebaseDatabase.getInstance().getReference("journals")
            .child(uid)
            .child(journal.id!!)
            .removeValue()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
