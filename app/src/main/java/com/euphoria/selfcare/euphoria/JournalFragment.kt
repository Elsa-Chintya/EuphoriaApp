package com.euphoria.selfcare.euphoria

import com.euphoria.selfcare.euphoria.databinding.FragmentJournalBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class JournalFragment : Fragment() {

    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Auto date
        val currentDate = SimpleDateFormat("dd MMMM yyyy", Locale("id")).format(Date())
        binding.etDate.setText(currentDate)
        binding.etDate.isEnabled = false

        val user = FirebaseAuth.getInstance().currentUser
        val database = FirebaseDatabase.getInstance().getReference("journals")

        // ============================
        // 🔹 TOMBOL SIMPAN JURNAL
        // ============================
        binding.btnSaveJournal.setOnClickListener {
            val affirmation = binding.etAffirmation.text.toString().trim()
            val reflection = binding.etReflection.text.toString().trim()
            val date = binding.etDate.text.toString()

            if (affirmation.isEmpty() || reflection.isEmpty()) {
                Toast.makeText(requireContext(), "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val journalId = database.push().key ?: ""

            val data = mapOf(
                "id" to journalId,
                "uid" to (user?.uid ?: "guest"),
                "date" to date,
                "affirmation" to affirmation,
                "reflection" to reflection,
                "timestamp" to System.currentTimeMillis()
            )

            database.child(user?.uid ?: "guest").child(journalId).setValue(data)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "✨ Jurnal berhasil disimpan!", Toast.LENGTH_SHORT).show()

                    binding.etAffirmation.text?.clear()
                    binding.etReflection.text?.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Gagal menyimpan jurnal!", Toast.LENGTH_SHORT).show()
                }
        }

        // ======================================
        // 🔹 TOMBOL "LIHAT RIWAYAT JURNAL"
        // ======================================
        binding.btnViewHistory.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListJournalFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
