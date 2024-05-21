package com.example.stayhealth

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.stayhealth.databinding.ActivityKategoriBbBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Suppress("DEPRECATION")
class KategoriBbActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKategoriBbBinding
    private val PREFS_FILENAME = "dataUser"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKategoriBbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

        val email = sharedPreferences.getString("email", "")
        val db = Firebase.firestore
        val docRef = db.collection("kalkulasiBmi").document(email!!)
        docRef.get()
            .addOnSuccessListener {
                binding.tvBerat.text = it.data!!.get("berat").toString()
                binding.tvUsia.text = it.data!!.get("umur").toString()
                binding.tvTinggi.text = it.data!!.get("tinggi").toString()
                binding.status.text = it.data!!.get("status").toString()

                displayStatus(it.data!!.get("status").toString())
            }
            .addOnFailureListener {
                Toast.makeText(this@KategoriBbActivity, "gagal mengambil data", Toast.LENGTH_SHORT)
                    .show()
            }

        binding.imageView4.setOnClickListener {
            onBackPressed()
        }
    }

    private fun displayStatus(status: String) {
        if (status.equals("Berat tidak mencukupi")) {
            binding.kategoriSatu.setBackgroundResource(R.color.bg)
        } else if (status.equals("Normal")) {
            binding.kategoriDua.setBackgroundResource(R.color.bg)
        } else if (status.equals("Kegemukan")) {
            binding.kategoriTiga.setBackgroundResource(R.color.bg)
        } else if (status.equals("Kegemukan level I")) {
            binding.kategoriEmpat.setBackgroundResource(R.color.bg)
        } else if (status.equals("Kegemukan level II")) {
            binding.kategoriLima.setBackgroundResource(R.color.bg)
        } else {
            binding.kategoriEnam.setBackgroundResource(R.color.bg)
        }
    }
}