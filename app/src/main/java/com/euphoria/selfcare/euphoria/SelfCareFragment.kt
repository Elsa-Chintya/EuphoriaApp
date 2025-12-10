package com.euphoria.selfcare.euphoria


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment


class SelfCareFragment : Fragment() {
    var btnInfo: Button? = null
    var btnCall: Button? = null

    // Method onCreateView dipanggil saat Fragment harus menampilkan layoutnya         // dengan membuat layout tersebut secara manual lewat objek View atau dengan      // membaca file XML
    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Layout tampilan untuk fragment ini
        val view = inflater.inflate(
            R.layout.fragment_selfcare,
            parent, false
        )
        val btnInfo = view.findViewById<View>(R.id.info) as Button
        val btnCall = view.findViewById<View>(R.id.Call) as Button
        val btnNotes = view.findViewById<View>(R.id.notes) as Button
        val btnLogout = view.findViewById<View>(R.id.btnLogout) as Button
        btnNotes.setOnClickListener {
            val intentNotes = Intent(activity, NotesActivity::class.java)
            startActivity(intentNotes)
        }
        btnCall.setOnClickListener {
            val nextFrag = CallFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }
        btnInfo.setOnClickListener {
            val nextFrag = InformationFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }
        btnLogout.setOnClickListener {

        }
        return view
    }

    // Method ini dipanggil sesaat setelah onCreateView().
    // Semua pembacaan view dan penambahan listener dilakukan disini (atau             // bisa juga didalam onCreateView)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }
}