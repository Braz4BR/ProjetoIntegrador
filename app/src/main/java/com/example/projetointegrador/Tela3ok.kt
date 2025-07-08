package com.example.projetointegrador

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Tela3ok : AppCompatActivity() {

    private lateinit var btnEntrar: Button
    private lateinit var lembrarSenhaCheck: CheckBox
    private lateinit var esqueceuSenhaText: TextView
    private lateinit var cadastroText: TextView
    private lateinit var emailInput: EditText
    private lateinit var senhaInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_tela3ok)


        btnEntrar = findViewById(R.id.button3)
        lembrarSenhaCheck = findViewById(R.id.checkBox2)
        esqueceuSenhaText = findViewById(R.id.textView10)
        cadastroText = findViewById(R.id.textView7)
        emailInput = findViewById(R.id.inputEmail)
        senhaInput = findViewById(R.id.inputSenha)


        btnEntrar.setOnClickListener {
            val email = emailInput.text.toString()
            val senha = senhaInput.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {

                val intent = Intent(this, TelaConsulta_Exame::class.java)
                startActivity(intent)
            }
        }

             esqueceuSenhaText.setOnClickListener {
            val intent = Intent(this, Tela4ok::class.java)
            startActivity(intent)
        }


        cadastroText.setOnClickListener {
            val intent = Intent(this, Tela2ok::class.java)
            startActivity(intent)
        }


        lembrarSenhaCheck.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(
                this,
                if (isChecked) "Senha será lembrada" else "Senha não será lembrada",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}