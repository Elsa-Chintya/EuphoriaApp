package com.euphoria.selfcare.euphoria

import android.os.Bundle
import android.os.Handler
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

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextUserName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonRegister: Button

    private var isPasswordVisible = false

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        initTextViewLogin()
        initViews()

        buttonRegister.setOnClickListener {
            if (validate()) {
                val userName = editTextUserName.text.toString().trim()
                val email = editTextEmail.text.toString().trim()
                val password = editTextPassword.text.toString().trim()

                registerUser(userName, email, password)
            }
        }

        // 👁 Toggle Show/Hide Password
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
    }

    // TEXTVIEW LOGIN
    private fun initTextViewLogin() {
        val textViewLogin = findViewById<TextView>(R.id.textViewLogin)
        textViewLogin.text = LoginActivity.fromHtml(
            "<font color='#868686'>Saya sudah memiliki akun. </font>" +
                    "<font color='#0c0099'>Masuk akun</font>"
        )
        textViewLogin.setOnClickListener { finish() }
    }

    // INISIALISASI VIEW
    private fun initViews() {
        editTextUserName = findViewById(R.id.nama)
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonRegister = findViewById(R.id.registBtn)
    }

    // VALIDASI
    private fun validate(): Boolean {
        var valid = true

        val userName = editTextUserName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (userName.isEmpty()) {
            editTextUserName.error = "Masukkan nama pengguna!"
            valid = false
        }

        if (email.isEmpty() || !email.contains("@")) {
            editTextEmail.error = "Masukkan email yang valid!"
            valid = false
        }

        if (password.length < 6) {
            editTextPassword.error = "Password minimal 6 karakter!"
            valid = false
        }

        return valid
    }

    // REGISTRASI USER KE FIREBASE
    private fun registerUser(userName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser!!.uid
                    val userMap = mapOf(
                        "name" to userName,
                        "email" to email
                    )

                    FirebaseDatabase.getInstance().getReference("users")
                        .child(uid)
                        .setValue(userMap)
                        .addOnSuccessListener {
                            Snackbar.make(
                                buttonRegister,
                                "Akun berhasil dibuat! Silakan login.",
                                Snackbar.LENGTH_LONG
                            ).show()

                            Handler().postDelayed({ finish() }, 1500)
                        }
                        .addOnFailureListener { e ->
                            Snackbar.make(
                                buttonRegister,
                                "Gagal menyimpan data: ${e.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                } else {
                    Snackbar.make(
                        buttonRegister,
                        "Registrasi gagal: ${task.exception?.message}",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
    }
}
