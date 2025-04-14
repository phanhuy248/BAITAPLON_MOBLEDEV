package com.neatroots.instagramclone

import Models.User
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.neatroots.instagramclone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val email = binding.email.editText?.text.toString().trim()
            val password = binding.pass.editText?.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(email, password)

                FirebaseAuth.getInstance().signInWithEmailAndPassword(user.email!!, user.password!!)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        tvForgotPassword.setOnClickListener {
            val edtEmail = findViewById<TextInputEditText>(R.id.edtEmail)
            val email = edtEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Đã gửi email khôi phục!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Lỗi: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show()
            }
        }


        binding.CreateNewAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
