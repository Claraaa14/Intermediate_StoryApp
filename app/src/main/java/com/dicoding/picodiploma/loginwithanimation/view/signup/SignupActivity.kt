package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding
import com.dicoding.picodiploma.loginwithanimation.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.retrofit.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingConfigure()
        setupView()
        playAnimation()
    }

    private fun bindingConfigure() {
        binding.signupButton.setOnClickListener {
            val nameAcc = binding.nameEditText.text.toString()
            val emailAcc = binding.emailEditText.text.toString()
            val passwordAcc = binding.passwordEditText.text.toString()

            when {
                nameAcc.isEmpty() -> {
                    binding.nameEditText.error = getString(R.string.name_error_register)
                }
                emailAcc.isEmpty() -> {
                    binding.emailEditText.error = getString(R.string.email_error_register)
                }
                passwordAcc.isEmpty() -> {
                    binding.passwordEditText.error = getString(R.string.password_error_register)
                }
                else -> {
                    setupAction()
                }
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        val emailAcc = binding.emailEditText.text.toString().trim()
        val nameAcc = binding.nameEditText.text.toString().trim()
        val passwordAcc = binding.passwordEditText.text.toString().trim() // trim menghapus spasi

        binding.progressBar.visibility = View.VISIBLE

        ApiConfig.getApiService().registerStoryApp(UserRegister(nameAcc, emailAcc, passwordAcc))
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.success),
                            Toast.LENGTH_SHORT
                        ).show()

                        val registIntentAccount = Intent(this@SignupActivity, LoginActivity::class.java)
                        startActivity(registIntentAccount)
                        finish()

                        AlertDialog.Builder(this@SignupActivity).also {
                            it.setTitle("Yeah!")
                            it.setMessage("Akun dengan $emailAcc sudah jadi nih. Yuk, login dan masuk ke StoryApp Dicoding.")
                            it.setPositiveButton("Lanjut") { _, _ ->
                                finish()
                            }
                            it.show()
                        }
                    } else {
                        binding.progressBar.visibility = View.INVISIBLE

                        Log.d("failure: ", "Status Code: ${response.code()}")
                        Log.d("failure: ", "Error Body: ${response.errorBody()?.string()}")

                        Toast.makeText(
                            applicationContext,
                            getString(R.string.not_success),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.INVISIBLE
                    Log.d("failure: ", t.message.toString())
                }
            }
            )
    }

        private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 100
        }.start()
    }
}
