package com.example.projetointegrador

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Tela2ok : AppCompatActivity() {

    private lateinit var inputNome: EditText
    private lateinit var inputEmail: EditText
    private lateinit var inputSenha: EditText
    private lateinit var inputConfirmarSenha: EditText
    private lateinit var btnCadastrar: Button
    private lateinit var termosCheckbox: CheckBox
    private lateinit var voltarLoginText: TextView
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2ok)

        databaseHelper = DatabaseHelper(this)

        inputNome = findViewById(R.id.inputNome)
        inputEmail = findViewById(R.id.inputEmail)
        inputSenha = findViewById(R.id.inputSenha)
        inputConfirmarSenha = findViewById(R.id.inputConfirmarSenha)
        btnCadastrar = findViewById(R.id.button2)
        termosCheckbox = findViewById(R.id.checkBox)
        voltarLoginText = findViewById(R.id.textView5)

        btnCadastrar.setOnClickListener {
            val nome = inputNome.text.toString().trim()
            val email = inputEmail.text.toString().trim()
            val senha = inputSenha.text.toString().trim()
            val confirmarSenha = inputConfirmarSenha.text.toString().trim()

            when {
                nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty() -> {
                    Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show()
                }
                !termosCheckbox.isChecked -> {
                    Toast.makeText(this, "Você precisa aceitar os termos", Toast.LENGTH_SHORT).show()
                }
                senha != confirmarSenha -> {
                    Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                }
                databaseHelper.checkUserExists(email) -> {
                    Toast.makeText(this, "Esse e-mail já está cadastrado", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val sucesso = databaseHelper.insertUser(nome, email, senha)
                    if (sucesso) {
                        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Tela3ok::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Erro ao cadastrar, tente novamente", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        voltarLoginText.setOnClickListener {
            val intent = Intent(this, Tela3ok::class.java)
            startActivity(intent)
            finish()
        }
    }
}