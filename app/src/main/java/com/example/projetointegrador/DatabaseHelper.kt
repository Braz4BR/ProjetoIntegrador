package com.example.projetointegrador

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class User(val id: Int, val nome: String, val email: String, val senha: String)

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "app.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NOME = "nome"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_SENHA = "senha"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT UNIQUE NOT NULL,
                $COLUMN_SENHA TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun insertUser(nome: String, email: String, senha: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, nome)
            put(COLUMN_EMAIL, email)
            put(COLUMN_SENHA, senha)
        }
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L
    }

    fun checkUserExists(email: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID),
            "$COLUMN_EMAIL = ?",
            arrayOf(email),
            null, null, null
        )
        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return exists
    }

    fun atualizarSenha(email: String, novaSenha: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SENHA, novaSenha)
        }
        val rowsAffected = db.update(
            TABLE_USERS,
            values,
            "$COLUMN_EMAIL = ?",
            arrayOf(email)
        )
        db.close()
        return rowsAffected > 0
    }


    fun autenticar(email: String, senha: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID),
            "$COLUMN_EMAIL = ? AND $COLUMN_SENHA = ?",
            arrayOf(email, senha),
            null, null, null
        )
        val authenticated = cursor.moveToFirst()
        cursor.close()
        db.close()
        return authenticated
    }


    fun buscarUsuario(email: String): User? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID, COLUMN_NOME, COLUMN_EMAIL, COLUMN_SENHA),
            "$COLUMN_EMAIL = ?",
            arrayOf(email),
            null, null, null
        )
        var user: User? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME))
            val emailResult = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val senha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SENHA))
            user = User(id, nome, emailResult, senha)
        }
        cursor.close()
        db.close()
        return user
    }
}