package com.example.stayhealth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.stayhealth.databinding.ActivityKalkulasiBmiBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import org.json.JSONException
import org.json.JSONObject

@Suppress("DEPRECATION")
class KalkulasiBmiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKalkulasiBmiBinding
    private val db = Firebase.firestore
    private lateinit var sharedPreferences: SharedPreferences
    private val PREFS_FILENAME = "dataUser"

    private val url = "https://cauww.pythonanywhere.com/predict"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKalkulasiBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

        val email = sharedPreferences.getString("email", "")

        var GENDER = "none"

        binding.btnGenderLk.setOnClickListener {
            GENDER = "L"
            if (binding.btnGenderLk.isClickable) {
                binding.btnGenderLk.setBackgroundResource(R.drawable.rectangle)
                binding.btnGenderPr.setBackgroundResource(R.drawable.gender)
            }
        }

        binding.btnGenderPr.setOnClickListener {
            GENDER = "P"
            if (binding.btnGenderPr.isClickable) {
                binding.btnGenderPr.setBackgroundResource(R.drawable.rectangle)
                binding.btnGenderLk.setBackgroundResource(R.drawable.gender)
            }
        }

        binding.imageView4.setOnClickListener {
            onBackPressed()
        }

        binding.hitungBmi.setOnClickListener {
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val class_obecity = jsonObject.getString("class_obecity")
                        binding.result.text = "Hasil Prediksi: $class_obecity"

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error: VolleyError ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["Age"] = binding.edUmur.text.toString()
                    params["Height"] = binding.edTinggi.text.toString()
                    params["Weight"] = binding.edBerat.text.toString()
                 return params
                }
            }
            val queue: RequestQueue = Volley.newRequestQueue(this)
            queue.add(stringRequest)

        }

    }

    private fun hitungBmi(berat: Float, tinggi: Float): Float {
        val tinggi_badan = tinggi / 100
        val bmi = berat / (tinggi_badan * tinggi_badan) * 10000
        return bmi
    }

    private fun kategoriBmi(bmi: Float): String {
        if (bmi < 18.5) {
            return "Insufficient_Weight"
        } else if (bmi < 24.9) {
            return "Normal_Weight"
        } else if (bmi < 29.9) {
            return "Overweight_Level_I"
        } else if (bmi < 34.9) {
            return "Overweight_Level_II"
        } else if (bmi < 39.9) {
            return "Obesity_Type_I"
        } else if (bmi < 44.9) {
            return "Obesity_Type_II"
        }else {
            return "Obesitas_Type_III"
        }
    }
}