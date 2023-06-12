package com.example.mystoryapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.mystoryapp.data.Outcome
import com.example.mystoryapp.data.RegisterResponse
import com.example.mystoryapp.databinding.ActivityRegisterBinding
import com.example.mystoryapp.ui.viewmodel.RegisterViewModel
import com.example.mystoryapp.utils.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLogin.setOnClickListener {
            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.isEnabled = false

        binding.btnRegister.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            registerViewModel.register(name, email, password).observe(this) {
                if (it != null) {
                    when (it) {
                        is Outcome.Loading -> {
                            showLoading(true)
                        }
                        is Outcome.Success -> {
                            showLoading(false)
                            processRegister(it.data)
                        }
                        is Outcome.Error -> {
                            showLoading(false)
                            Toast.makeText(this, it.error, Toast.LENGTH_LONG).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }

        binding.edRegisterName.addTextChangedListener {
            validateForm()
        }

        binding.edRegisterEmail.addTextChangedListener {
            validateForm()
        }

        binding.edRegisterPassword.addTextChangedListener {
            validateForm()
        }
    }

    private fun showLoading(show: Boolean) {
        binding.edRegisterName.isVisible = show
        binding.edRegisterEmail.isVisible = show
        binding.edRegisterPassword.isVisible = show
        binding.tvLogin.isVisible = show
        binding.btnRegister.isVisible = show
        binding.progBar.isVisible = show
    }

    private fun processRegister(userData: RegisterResponse) {
        if (userData.error) {
            Toast.makeText(this, "Failed to register", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Register successful, please login!", Toast.LENGTH_LONG).show()
        }
    }

    private fun validateForm() {
        val name = binding.edRegisterName.text.toString()
        val email = binding.edRegisterName.text.toString()
        val password = binding.edRegisterPassword.isInputValid()
        binding.btnRegister.isEnabled = name.isNotEmpty() && email.isNotEmpty() && password
    }
}