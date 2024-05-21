package com.example.stayhealth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.stayhealth.databinding.ActivityProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    private val PREFS_FILENAME = "dataUser"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email","")!!
        val nama = sharedPreferences.getString("nama","")!!
        val db = Firebase.firestore

        binding.circleImageView2.setOnClickListener {
            val intents = Intent(Intent.ACTION_PICK)
            intents.setType("images/*")
            startActivityForResult(Intent.createChooser(intent,"Take Image"),20)
        }

        val docRef = db.collection("kalkulasiBmi").document(email)
        docRef.get()
            .addOnSuccessListener {
                binding.nama.text = nama
                binding.tvUmur.text = "${it.data!!.get("umur").toString()} Tahun"
                binding.tvBerat.text = "${it.data!!.get("berat").toString()} kg"
                binding.status.text = "${it.data!!.get("status")}"
            }

        binding.imageView4.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 20 && resultCode == RESULT_OK && data != null){
            val path = data.data
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val inputStream = contentResolver.openInputStream(path!!)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    withContext(Dispatchers.Main) {
                        binding.circleImageView2.setImageBitmap(bitmap)
                    }
                } catch (e: Exception) {
                    Log.e("TAG", "Error loading image: ${e.message}", e)
                    // Handle the error appropriately, e.g., display a user-friendly message
                }
            }
        }
    }
}