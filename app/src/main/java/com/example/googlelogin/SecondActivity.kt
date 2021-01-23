package com.example.googlelogin

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.TaskExecutors
import kotlin.math.sign


class SecondActivity : AppCompatActivity() {

    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var name : TextView
    lateinit var email : TextView
    lateinit var signout : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);


        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        signout = findViewById(R.id.signout)
        signout.setOnClickListener {
            signOut()
        }

        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName: String? = acct.getDisplayName()
            val Email = acct.getEmail()
            name.setText(personName)
            email.setText(Email)
        }
    }

    private fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {
               if (it.isSuccessful)
               {
                   Toast.makeText(this,"Successfully Signout",Toast.LENGTH_LONG).show()
                   finish()
               }
                else
               {
                   Toast.makeText(this,"Signout Failed",Toast.LENGTH_LONG).show()
               }
            })
    }
}