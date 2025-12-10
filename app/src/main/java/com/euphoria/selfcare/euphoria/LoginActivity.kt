package com.euphoria.selfcare.euphoria

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var loginAkun: Button

    private var isPasswordVisible = false

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        initCreateAccountTextView()
        initViews()

        // 👁 Toggle Show / Hide Password
        editTextPassword.setOnTouchListener { _, event ->
            val RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= editTextPassword.right -
                    editTextPassword.compoundDrawables[RIGHT].bounds.width()
                ) {

                    val selection = editTextPassword.selectionEnd

                    if (isPasswordVisible) {
                        editTextPassword.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_baseline_visibility_off_24, 0
                        )
                        editTextPassword.transformationMethod =
                            PasswordTransformationMethod.getInstance()
                        isPasswordVisible = false
                    } else {
                        editTextPassword.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_baseline_visibility_24, 0
                        )
                        editTextPassword.transformationMethod =
                            HideReturnsTransformationMethod.getInstance()
                        isPasswordVisible = true
                    }

                    editTextPassword.setSelection(selection)
                    return@setOnTouchListener true
                }
            }
            false
        }

        // 💛 LOGIN BUTTON
        loginAkun.setOnClickListener {
            if (validate()) {

                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString().trim()

                // 🔥 LOGIN Firebase Auth
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {

                        // 🔥 Ambil data nama dari Realtime Database
                        val uid = auth.currentUser!!.uid
                        val dbRef = FirebaseDatabase.getInstance()
                            .getReference("users")
                            .child(uid)

                        dbRef.get().addOnSuccessListener { snapshot ->
                            val name = snapshot.child("name").getValue(String::class.java) ?: "User"

                            // 🔥 Simpan ke SharedPreferences (untuk HomeFragment)
                            val pref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
                            pref.edit()
                                .putString("name", name)
                                .apply()

                            Snackbar.make(loginAkun, "Login Berhasil!", Snackbar.LENGTH_LONG).show()

                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }

                    }
                    .addOnFailureListener {
                        Snackbar.make(
                            loginAkun,
                            "Login gagal: ${it.message}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
            }
        }
    }

    // TEKS "BELUM PUNYA AKUN"
    private fun initCreateAccountTextView() {
        val textViewCreateAccount = findViewById<TextView>(R.id.textViewCreateAccount)
        textViewCreateAccount.text = fromHtml(
            "<font color='#868686'>Saya belum memiliki akun. </font>" +
                    "<font color='#0c0099'>Buat akun</font>"
        )
        textViewCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // INISIALISASI VIEW
    private fun initViews() {
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        loginAkun = findViewById(R.id.loginBtn)
    }

    // VALIDASI
    private fun validate(): Boolean {
        var valid = true

        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (email.isEmpty()) {
            editTextEmail.error = "Email tidak boleh kosong!"
            valid = false
        } else if (!email.contains("@")) {
            editTextEmail.error = "Masukkan email yang valid!"
            valid = false
        }

        if (password.isEmpty()) {
            editTextPassword.error = "Masukkan password!"
            valid = false
        } else if (password.length < 6) {
            editTextPassword.error = "Password minimal 6 karakter!"
            valid = false
        }

        return valid
    }

    companion object {

        // handle html for clickable text
        fun fromHtml(html: String?): Spanned {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(html)
            }
        }
    }
}
