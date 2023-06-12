package com.example.mystoryapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.mystoryapp.data.LoginResponse
import com.example.mystoryapp.data.Outcome
import com.example.mystoryapp.databinding.ActivityLoginBinding
import com.example.mystoryapp.ui.viewmodel.LoginViewModel
import com.example.mystoryapp.utils.Preference
import com.example.mystoryapp.utils.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.isEnabled = false

        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            loginViewModel.login(email, password).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Outcome.Loading -> {
                            showLoading(true)
                        }
                        is Outcome.Success -> {
                            showLoading(false)
                            processLogin(result.data)
                        }
                        is Outcome.Error -> {
                            showLoading(false)
                            Toast.makeText(this, result.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        binding.edLoginEmail.addTextChangedListener {
            validateForm()
        }

        binding.edLoginPassword.addTextChangedListener {
            validateForm()
        }
    }

    private fun showLoading(show: Boolean) {
        binding.apply {
            edLoginEmail.isEnabled = show
            edLoginPassword.isEnabled = show
            tvRegister.isEnabled = show
            btnLogin.isEnabled = show
            progBar.isVisible = show
        }
    }

    private fun processLogin(userData: LoginResponse) {
        if (userData.error) {
            Toast.makeText(this, userData.message, Toast.LENGTH_LONG).show()
        } else {
            Preference.saveToken(userData.loginResult.token, this)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun validateForm() {
        val email = binding.edLoginEmail.text.toString()
        val password = binding.edLoginPassword.isInputValid()
        binding.btnLogin.isEnabled = email.isNotEmpty() && password
    }
}