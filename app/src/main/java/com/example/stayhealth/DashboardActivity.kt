package com.example.stayhealth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stayhealth.adapter.InformasiAdapter
import com.example.stayhealth.databinding.ActivityDashboardBinding
import com.example.stayhealth.model.InformasiModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.ArrayList

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private val data = ArrayList<InformasiModel>()
    private val PREFS_FILENAME = "dataUser"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

        val email = sharedPreferences.getString("email", "")

        val db = Firebase.firestore
        val docRef = db.collection("account").document(email!!)
        docRef.get()
            .addOnSuccessListener {
                binding.textView5.text = it.getString("nama")
            }

        binding.circleImageView.setOnClickListener {
            val intents = Intent(this@DashboardActivity, ProfileActivity::class.java)
            intents.putExtra("email", email)
            startActivity(intents)
        }

        binding.kalkulasibmi.setOnClickListener {
            val kalkulasiBmi = Intent(this@DashboardActivity, KalkulasiBmiActivity::class.java)
            kalkulasiBmi.putExtra("email", email)
            startActivity(kalkulasiBmi)
        }

        binding.rvInformasi.layoutManager = GridLayoutManager(this, 2)
        setData()

        val adapter = InformasiAdapter(data)

        adapter.setOnClickListener(object : InformasiAdapter.OnClickListener {
            override fun onClick(position: Int, model: InformasiModel) {
                val moveToDetail =
                    Intent(this@DashboardActivity, InformasiDetailActivity::class.java)
                moveToDetail.putExtra("data", model)
                startActivity(moveToDetail)
            }
        })

        binding.rvInformasi.adapter = adapter

        binding.logout.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(this@DashboardActivity, Login::class.java))
        }
    }

    private fun setData() {
        data.add(
            InformasiModel(
                R.drawable.informasi,
                resources.getString(R.string.titleInfo1),
                resources.getString(R.string.desc)
            )
        )
        data.add(
            InformasiModel(
                R.drawable.informasi,
                resources.getString(R.string.titleInfo2),
                resources.getString(R.string.desc)
            )
        )
        data.add(
            InformasiModel(
                R.drawable.informasi,
                resources.getString(R.string.titleInfo3),
                resources.getString(R.string.desc)
            )
        )
        data.add(
            InformasiModel(
                R.drawable.informasi,
                resources.getString(R.string.titleInfo4),
                resources.getString(R.string.desc)
            )
        )
        data.add(
            InformasiModel(
                R.drawable.informasi,
                resources.getString(R.string.titleInfo4),
                resources.getString(R.string.desc)
            )
        )
        data.add(
            InformasiModel(
                R.drawable.informasi,
                resources.getString(R.string.titleInfo4),
                resources.getString(R.string.desc)
            )
        )
    }

}