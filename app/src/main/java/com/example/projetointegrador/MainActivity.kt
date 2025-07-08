package com.example.projetointegrador

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEntrar = findViewById<Button>(R.id.button)
        val txtCadastrar = findViewById<TextView>(R.id.textView3)

        btnEntrar.setOnClickListener {
           startActivity(Intent(this, Tela3ok::class.java))
        }

        txtCadastrar.setOnClickListener {
            val intent = Intent(this, Tela2ok::class.java)
            startActivity(intent)
        }
    }
}

