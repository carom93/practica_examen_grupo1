package com.example.inicio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.inicio.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.security.Principal

class Inicio : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        binding.btLogin.setOnClickListener {
            haceLogin();
        }

        binding.btRegister.setOnClickListener {
            haceRegister();
        }
    }

    private fun haceLogin() {
        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()

        auth.signInWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    Log.d("Autenticando","Autenticado")
                    val user = auth.currentUser
                    actualiza(user)
                } else {
                    Log.d("Autneticando","Fallo")
                    Toast.makeText(baseContext,"Fallo",Toast.LENGTH_LONG).show()
                    actualiza(null)
                }
            }
    }

    private fun actualiza(user: FirebaseUser?) {
        if (user != null) {
            val intent =  Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        val usuario = auth.currentUser
        actualiza(usuario)
    }

    private fun haceRegister() {
        val email = binding.etEmail.text.toString()
        val clave = binding.etClave.text.toString()

        auth.createUserWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    Log.d("Creando usuario","Registrado")
                    val user = auth.currentUser
                    actualiza(user)
                } else {
                    Log.d("Creando usuario","Fallo")
                    Toast.makeText(baseContext,"Fallo",Toast.LENGTH_LONG).show()
                    actualiza(null)
                }
            }
    }
}