package com.euphoria.selfcare.euphoria

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment


class InformationFragment : Fragment() {
    var btnArtikel: Button? = null
    var judulArtikel: String? = null
    var jdl: String? = null

    // Method onCreateView dipanggil saat Fragment harus menampilkan layoutnya         // dengan membuat layout tersebut secara manual lewat objek View atau dengan      // membaca file XML
    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_information,
            parent, false
        )
        val btnArtikel = view.findViewById<View>(R.id.bt_tkm) as Button
        btnArtikel.setOnClickListener {
            val nextFrag = showInfo1()
            //gagal kirim judul
            //                String jdl = ((Button) v).getText().toString();
            //                Bundle bundle = new Bundle();
            //                bundle.putString("message", jdl);
            //                nextFrag.setArguments(bundle);
            //                getFragmentManager()
            //                        .beginTransaction()
            //                        .replace(R.id.content, nextFrag)
            //                        .commit();
            requireActivity().supportFragmentManager.beginTransaction()
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }
        val btnArtikellcd = view.findViewById<View>(R.id.bt_lcd) as Button
        btnArtikellcd.setOnClickListener {
            val nextFrag = showInfo2()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }
        val btnArtikelccs = view.findViewById<View>(R.id.bt_ccs) as Button
        btnArtikelccs.setOnClickListener {
            val nextFrag = showInfo3()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }
        val btnArtikelcms = view.findViewById<View>(R.id.bt_cms) as Button
        btnArtikelcms.setOnClickListener {
            val nextFrag = showInfo4()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }
        val btnArtikelmcip = view.findViewById<View>(R.id.bt_mcip) as Button
        btnArtikelmcip.setOnClickListener {
            val nextFrag = showInfo5()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }
        val btnArtikelmgk = view.findViewById<View>(R.id.bt_mgk) as Button
        btnArtikelmgk.setOnClickListener {
            val nextFrag = showInfo6()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }
        val btnArtikelkdsd = view.findViewById<View>(R.id.bt_kdsd) as Button
        btnArtikelkdsd.setOnClickListener {
            val nextFrag = showInfo7()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
        }
        val btnArtikelbdpb = view.findViewById<View>(R.id.bt_bdpb) as Button
        btnArtikelbdpb.setOnClickListener {
            val nextFrag = showInfo8()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace((requireView().parent as ViewGroup).id, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
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