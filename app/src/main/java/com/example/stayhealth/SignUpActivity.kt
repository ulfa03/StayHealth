package com.example.stayhealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.stayhealth.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class SignUpActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.daftar.setOnClickListener {
            val user = hashMapOf(
                "nama" to binding.edNama.text.toString(),
                "email" to binding.edEmail.text.toString(),
                "password" to binding.edPassword.text.toString(),
            )

            db.collection("account").document(binding.edEmail.text.toString())
                .set(user)
                .addOnSuccessListener {
                    Toast.makeText(this@SignUpActivity, "Berhasil Daftar", Toast.LENGTH_SHORT)
                        .show()
                    val movetoDashboard = Intent(this@SignUpActivity, Login::class.java)
                    startActivity(movetoDashboard)
                }

                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Gagal Membuat Akun \n ${it.message.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}