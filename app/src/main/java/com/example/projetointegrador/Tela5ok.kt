package com.example.projetointegrador

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class Tela5 : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var novaSenhaInput: TextInputEditText
    private lateinit var confirmarSenhaInput: TextInputEditText
    private lateinit var buttonConfirmar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela5ok)

        // o email volta aqui
        val email = intent.getStringExtra("email")


        db = DatabaseHelper(this)


        novaSenhaInput = findViewById(R.id.novaSenhaInput)
        confirmarSenhaInput = findViewById(R.id.confirmarSenhaInput)
        buttonConfirmar = findViewById(R.id.button4)


        buttonConfirmar.setOnClickListener {
            val novaSenha = novaSenhaInput.text.toString()
            val confirmarSenha = confirmarSenhaInput.text.toString()

            if (novaSenha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else if (novaSenha != confirmarSenha) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
            } else {
                if (email != null) {
                    val sucesso = db.atualizarSenha(email, novaSenha)

                    if (sucesso) {
                        Toast.makeText(this, "Senha atualizada com sucesso!", Toast.LENGTH_LONG).show()
                        // aqui faz volta pra tela normal
                        val intent = Intent(this, Tela2ok::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Erro ao atualizar a senha.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Erro: e-mail não encontrado.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}