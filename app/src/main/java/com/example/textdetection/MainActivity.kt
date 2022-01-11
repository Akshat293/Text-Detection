package com.example.textdetection

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import java.security.KeyStore
import com.example.textdetection.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
      binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val But:ImageView=findViewById(R.id.entry)
        But.setOnClickListener(){
            val intent = Intent(this,ScannerActivity::class.java)
            startActivity(intent)
        }
    }
}

