package com.example.projetointegrador

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import android.os.Handler
import android.os.Looper

class Tela4ok : AppCompatActivity() {

    private lateinit var emailInput: TextInputEditText
    private lateinit var btnEntrar: Button
    private lateinit var voltarLogin: TextView
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela4ok)


        emailInput = findViewById(R.id.emailInput)
        btnEntrar = findViewById(R.id.button5)
        voltarLogin = findViewById(R.id.textView16)

        // aqui ta o banco doido
        databaseHelper = DatabaseHelper(this)


        btnEntrar.setOnClickListener {
            val email = emailInput.text.toString().trim()

            if (email.isNotEmpty()) {
                if (databaseHelper.checkUserExists(email)) {
                    sendRecoveryEmail(email)
                    Toast.makeText(this, "E-mail de recuperação enviado!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "E-mail não encontrado no sistema.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, insira um e-mail.", Toast.LENGTH_SHORT).show()
            }
        }


        voltarLogin.setOnClickListener {
            finish() // ou: startActivity(Intent(this, TelaLogin::class.java))
        }
    }

    //  Método para enviar e-mail de recuperação que eu me matei pra fazer (XP)
    private fun sendRecoveryEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            putExtra(Intent.EXTRA_SUBJECT, "Recuperação de Senha")
            putExtra(Intent.EXTRA_TEXT, "Olá!\n\nClique no link abaixo para redefinir sua senha:\n\nhttps://seusite.com/redefinir\n\nSe não solicitou, ignore este e-mail.")
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Nenhum aplicativo de e-mail encontrado.", Toast.LENGTH_SHORT).show()
        }
        val email = emailInput.text.toString()

        if (databaseHelper.checkUserExists(email)) {
            Toast.makeText(this, "Um e-mail foi enviado para $email com instruções de recuperação.", Toast.LENGTH_LONG).show()

            // Simula um "atraso" como se estivesse enviando
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, Tela5::class.java)
                intent.putExtra("email", email)  // Opcional, se quiser usar na Tela5
                startActivity(intent)
            }, 2000)  // 2 segundos de "simulação"

        } else {
            Toast.makeText(this, "E-mail não encontrado.", Toast.LENGTH_SHORT).show()
        }
    }
}