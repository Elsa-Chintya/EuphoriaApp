package com.euphoria.selfcare.euphoria

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // ⭐ WAJIB UNTUK ANDROID 13+ AGAR NOTIFIKASI MUNCUL
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }

        // 🔹 Tes koneksi Firebase
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("test")
        myRef.setValue("Firebase Connected!")

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        // 🔹 Tampilkan HomeFragment saat pertama dibuka
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    // 🔹 Fungsi untuk ganti fragment
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // 🔹 Listener Bottom Navigation
    // 🔹 Listener Bottom Navigation
    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {

            R.id.nav_home -> {
                loadFragment(HomeFragment())
            }

            // 🔥 UBAH DI SINI — buka halaman intro chatbot chatawal.xml
            R.id.nav_therapy -> {
                loadFragment(ChatAwalFragment())
            }


            R.id.nav_notification -> {
                loadFragment(NotificationFragment())
            }

            R.id.nav_selfcare -> {
                loadFragment(SelfCareFragment())
            }

            R.id.nav_journal -> {
                loadFragment(JournalFragment())
            }
        }

        true
    }

}
