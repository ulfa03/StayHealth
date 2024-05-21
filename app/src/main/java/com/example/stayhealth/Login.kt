package com.example.stayhealth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.stayhealth.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val PREFS_FILENAME = "dataUser"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val stats = sharedPreferences.getBoolean("stats", false)

        if (stats == true) {
            startActivity(Intent(this@Login, DashboardActivity::class.java))
        }

        binding.login.setOnClickListener {
            val db = Firebase.firestore
            val docRef = db.collection("account").document(binding.edEmail.text.toString())
            docRef.get()
                .addOnSuccessListener {
                    if (binding.edEmail.text.toString() == it.getString("email") && binding.edPassword.text.toString() == it.getString(
                            "password"
                        )
                    ) {
                        val editor = sharedPreferences.edit()
                        editor.putString("email", it.getString("email"))
                        editor.putBoolean("stats", true)
                        editor.putString("nama",it.getString("nama"))
                        editor.apply()
                        startActivity(Intent(this@Login, DashboardActivity::class.java))
                    } else {
                        Toast.makeText(this@Login, "Akun Tidak Tersedia", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this@Login,"Gagal Login",Toast.LENGTH_SHORT).show()
                }
        }
        binding.signup.setOnClickListener {
            startActivity(Intent(this@Login, SignUpActivity::class.java))
        }
    }
}