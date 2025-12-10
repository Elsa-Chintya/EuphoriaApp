package com.euphoria.selfcare.euphoria

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null

    // 🎵 PLAYLIST LAGU
    private var currentSong = 0
    private val playlist = arrayOf(
        R.raw.relaxation,   // lagu 1
        R.raw.music2,       // lagu 2
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, parent, false)

        val namaPasienTextView = view.findViewById<TextView>(R.id.Nama)

        // 🔹 Ambil nama user dari Firebase
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

        if (uid != null) {
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(uid)
            userRef.get().addOnSuccessListener { snapshot ->
                val nama = snapshot.child("name").value.toString()
                namaPasienTextView.text = "$nama's Space"
            }.addOnFailureListener {
                namaPasienTextView.text = "Gagal memuat nama"
            }
        } else {
            namaPasienTextView.text = "Belum login"
        }

        // 🔹 Tombol fitur
        val btnInfo = view.findViewById<Button>(R.id.info)
        val btnCall = view.findViewById<Button>(R.id.Call)
        val btnSesi1 = view.findViewById<Button>(R.id.btnSesi1)
        val btnSesi2 = view.findViewById<Button>(R.id.btnSesi2)
        val btnNotes = view.findViewById<Button>(R.id.notes)
        val btnMapLain = view.findViewById<Button>(R.id.btnMapLain)
        val btnNotif = view.findViewById<ImageView>(R.id.btnNotif)

        // 🔹 Mood Icons
        val happyIcon = view.findViewById<ImageView>(R.id.happyIcon)
        val angryIcon = view.findViewById<ImageView>(R.id.angryIcon)
        val sadIcon = view.findViewById<ImageView>(R.id.sadIcon)

        happyIcon.setOnClickListener {
            Toast.makeText(requireContext(), "Kamu sedang bahagia 😊", Toast.LENGTH_SHORT).show()
            saveMoodToFirebase("Bahagia")
        }

        angryIcon.setOnClickListener {
            Toast.makeText(requireContext(), "Kamu sedang marah 😠", Toast.LENGTH_SHORT).show()
            saveMoodToFirebase("Marah")
        }

        sadIcon.setOnClickListener {
            Toast.makeText(requireContext(), "Kamu sedang sedih 😢", Toast.LENGTH_SHORT).show()
            saveMoodToFirebase("Sedih")
        }

        // 🎵 TOMBOL AUDIO RELAKSASI (PLAY – NEXT – STOP)
        val btnPlay = view.findViewById<Button>(R.id.btnPlay)
        val btnNext = view.findViewById<Button>(R.id.btnNext)
        val btnStop = view.findViewById<Button>(R.id.btnStop)

        // ▶ PLAY
        btnPlay.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(requireContext(), playlist[currentSong])
            }
            mediaPlayer?.start()
            Toast.makeText(requireContext(), "🎵 Memutar lagu ${currentSong + 1}", Toast.LENGTH_SHORT).show()
        }

        // ⏭ NEXT SONG
        btnNext.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null

            currentSong = (currentSong + 1) % playlist.size

            mediaPlayer = MediaPlayer.create(requireContext(), playlist[currentSong])
            mediaPlayer?.start()

            Toast.makeText(requireContext(), "⏭ Lagu berikutnya: ${currentSong + 1}", Toast.LENGTH_SHORT).show()
        }

        // ⏹ STOP
        btnStop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            Toast.makeText(requireContext(), "⏹ Musik dihentikan", Toast.LENGTH_SHORT).show()
        }

        // 🔹 Navigasi Fragment
        btnNotif.setOnClickListener {
            navigateToFragment(NotificationFragment())
        }

        btnInfo.setOnClickListener {
            navigateToFragment(InformationFragment())
        }

        btnCall.setOnClickListener {
            navigateToFragment(CallFragment())
        }

        btnMapLain.setOnClickListener {
            navigateToFragment(KonsultasiLainnya())
        }

        // 🔥 CATATAN (NotesActivity)
        btnNotes.setOnClickListener {
            startActivity(Intent(requireContext(), NotesActivity::class.java))
        }

        btnSesi1.setOnClickListener {
            startActivity(Intent(activity, ShowMap1::class.java))
        }

        btnSesi2.setOnClickListener {
            startActivity(Intent(activity, ShowMap2::class.java))
        }

        return view
    }

    // Navigasi antar Fragment
    private fun navigateToFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun saveMoodToFirebase(mood: String) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("moodTracker")
        val time = System.currentTimeMillis()
        ref.child(time.toString()).setValue(mood)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
