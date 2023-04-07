package com.example.msgkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.msgkotlin.databinding.ActivityAuthentcationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AuthentcationActivity : AppCompatActivity() {

    var binding: ActivityAuthentcationBinding? = null
     var dbrefernce: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthentcationBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        var email = binding!!.email.getText().toString()
        var password = binding!!.password.getText().toString()
        var name = binding!!.name.getText().toString()


        dbrefernce = FirebaseDatabase.getInstance().getReference("Arslan").child("users")

        binding!!.login.setOnClickListener(View.OnClickListener {

            login()
        })

        binding!!.signup.setOnClickListener(View.OnClickListener {
            email = binding!!.email.getText().toString()
            password = binding!!.password.getText().toString()
            name = binding!!.name.getText().toString()
            signup()
        })

    }

    private fun login() {

        var email = binding!!.email.getText().toString()
        var password = binding!!.password.getText().toString()
        var name = binding!!.name.getText().toString()

        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email.trim { it <= ' ' }, password.trim { it <= ' ' })
            .addOnSuccessListener {
                startActivity(Intent(this@AuthentcationActivity, MainActivity::class.java))
                finish()
            }
    }

    private fun signup() {
        var email = binding!!.email.getText().toString()
        var password = binding!!.password.getText().toString()
        var name = binding!!.name.getText().toString()
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email.trim { it <= ' ' }, password.trim { it <= ' ' })
            .addOnSuccessListener {
                val userProfileChangeRequest =
                    UserProfileChangeRequest.Builder().setDisplayName(name).build()
                val firebaseUser = FirebaseAuth.getInstance().currentUser
                firebaseUser!!.updateProfile(userProfileChangeRequest)
                val userModel = UserModel(FirebaseAuth.getInstance().uid!!, name, email, password)

                //user model is pass though here
                FirebaseAuth.getInstance().uid?.let { it1 -> dbrefernce?.child(it1)?.setValue(userModel)
                      }
                startActivity(Intent(this@AuthentcationActivity, MainActivity::class.java))
                finish()
            }
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this@AuthentcationActivity, MainActivity::class.java))
            finish()
        }
    }
}