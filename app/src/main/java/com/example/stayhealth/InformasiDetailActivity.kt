package com.example.stayhealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stayhealth.databinding.ActivityInformasiDetailBinding
import com.example.stayhealth.model.InformasiModel

@Suppress("DEPRECATION")
class InformasiDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityInformasiDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformasiDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<InformasiModel>("data")

        binding.titleDetail.text = data?.title
        binding.ivDetail.setImageResource(data!!.image)
        binding.desc.text = data?.desc

        binding.imageView4.setOnClickListener {
            onBackPressed()
        }
    }
}