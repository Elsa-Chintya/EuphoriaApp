package com.euphoria.selfcare.euphoria

import android.content.Intent
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class KonsultasiLainnya : Fragment() {

    // Method onCreateView dipanggil saat Fragment harus menampilkan layoutnya         // dengan membuat layout tersebut secara manual lewat objek View atau dengan      // membaca file XML
    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Layout tampilan untuk fragment ini
        val view = inflater.inflate(
            R.layout.activity_konsultasi_lainnya,
            parent, false
        )
        val btnUPP = view.findViewById<View>(R.id.btnUPP) as Button
        btnUPP.setOnClickListener {
            val intentMap = Intent(activity, ShowMapUPP::class.java)
            startActivity(intentMap)
        }
        val btnLPD = view.findViewById<View>(R.id.btnLPD) as Button
        btnLPD.setOnClickListener {
            val intentMap = Intent(activity, ShowMapLPD::class.java)
            startActivity(intentMap)
        }
        val btnPLP = view.findViewById<View>(R.id.btnPLP) as Button
        btnPLP.setOnClickListener {
            val intentMap = Intent(activity, ShowMapPLP::class.java)
            startActivity(intentMap)
        }
        val btnRSUD = view.findViewById<View>(R.id.btnRSUD) as Button
        btnRSUD.setOnClickListener {
            val intentMap = Intent(activity, ShowMapRSUD::class.java)
            startActivity(intentMap)
        }
        val btnRSPS = view.findViewById<View>(R.id.btnRSPSP) as Button
        btnRSPS.setOnClickListener {
            val intentMap = Intent(activity, ShowMapRSPS::class.java)
            startActivity(intentMap)
        }
        val btnRSD = view.findViewById<View>(R.id.btnRSD) as Button
        btnRSD.setOnClickListener {
            val intentMap = Intent(activity, ShowMapRSD::class.java)
            startActivity(intentMap)
        }


        return view
    }

    // Method ini dipanggil sesaat setelah onCreateView().
    // Semua pembacaan view dan penambahan listener dilakukan disini (atau
    // bisa juga didalam onCreateView)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }
}