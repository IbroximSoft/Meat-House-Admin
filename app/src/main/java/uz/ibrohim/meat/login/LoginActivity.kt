package uz.ibrohim.meat.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import uz.ibrohim.meat.HomeActivity
import uz.ibrohim.meat.databinding.ActivityLoginBinding
import uz.ibrohim.meat.login.request_response.LoginRequest
import uz.ibrohim.meat.login.request_response.LoginResponse
import uz.ibrohim.meat.login.services.LoginViewModel
import uz.ibrohim.meat.retrofit.ApiClient
import uz.ibrohim.meat.retrofit.NetworkHelper
import uz.ibrohim.meat.retrofit.Resource
import uz.ibrohim.meat.utils.Preferences
import uz.ibrohim.meat.utils.errorToast
import uz.ibrohim.meat.utils.progressInterface
import uz.ibrohim.meat.utils.successToast
import uz.ibrohim.meat.utils.warningToast

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            Preferences.init(this@LoginActivity)
            val token: String = Preferences.token
            progressInterface(circularProgress)

            if (token.isNotEmpty()) {
                intents()
            }

            loginBtn.setOnClickListener {
                checkData()
            }
        }
    }

    private fun checkData() {
        binding.apply {
            val phone: String = loginNumber.text.toString()
            val password: String = loginPassword.text.toString()
            if (phone.isEmpty()) {
                warningToast(this@LoginActivity, "Login kiriting")
            } else if (password.isEmpty()) {
                warningToast(this@LoginActivity, "Paro'l kiriting")
            } else {
                val networkHelper = NetworkHelper(this@LoginActivity)
                val viewModel = LoginViewModel(ApiClient.apiServices, networkHelper)
                val model = LoginRequest(phone = phone, password = password)
                viewModel.getLogin(body = model)

                lifecycleScope.launchWhenCreated {
                    viewModel.flow.collect {
                        when (it) {
                            is Resource.Error -> {
                                Log.d("test777", it.message)
                                binding.apply {
                                    loginProgress.isVisible = false
                                    loginBtn.isVisible = true
                                }
                                errorToast(this@LoginActivity, "Login yoki paro'l hato!")
                            }

                            Resource.Loading -> {
                                binding.apply {
                                    loginBtn.isVisible = false
                                    loginProgress.isVisible = true
                                }
                            }

                            is Resource.Success -> {
                                savePreferencesNetworkData(it.data as LoginResponse, phone)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun savePreferencesNetworkData(requestLogin: LoginResponse, phone: String) {
        val limit = requestLogin.access
        Preferences.token = limit
        Preferences.phone = phone

        successToast(this@LoginActivity, "Muvoffaqiyatli!")
        intents()
    }

    private fun intents(){
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}