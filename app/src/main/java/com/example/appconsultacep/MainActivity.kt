package com.example.appconsultacep

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.appconsultacep.api.ViaCepClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val edtCep = findViewById<EditText>(R.id.edtCep)
        val btnConsultar = findViewById<Button>(R.id.btnConsultar)
        val txtLogradouro = findViewById<EditText>(R.id.txtLogradouro)
        val txtBairro = findViewById<EditText>(R.id.txtBairro)
        val txtUf = findViewById<EditText>(R.id.txtUf)
        val txtDdd = findViewById<EditText>(R.id.txtDdd)
        val txtLocalidade = findViewById<EditText>(R.id.txtLocalidade)

        btnConsultar.setOnClickListener {
            val cep = edtCep.text.toString()
            if (cep.length != 8) {
                edtCep.error = "CEP inválido"
                return@setOnClickListener

            }
            lifecycleScope.launch {
                val endereco = ViaCepClient.instance.buscarEndereco(cep)
                txtLogradouro.setText(endereco.logradouro)
                txtBairro.setText(endereco.bairro)
                txtUf.setText(endereco.uf)
                txtDdd.setText(endereco.ddd)
                txtLocalidade.setText(endereco.localidade)
            }
        }
    }
}
