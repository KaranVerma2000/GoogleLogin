package com.example.googlelogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Property
import android.util.Size
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var sigin: SignInButton
    var RC_SIGN_IN = 123
    private lateinit var progress : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso: GoogleSignInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        progress = findViewById(R.id.progressBar)
        sigin = findViewById(R.id.sign_in_button)
        sigin.setOnClickListener {
            progress.visibility = View.VISIBLE
            sigingoogle()
        }
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
    }

    private fun sigingoogle() {
        val signInIntent: Intent = googleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data);
            Toast.makeText(this, "auth success", Toast.LENGTH_LONG).show()
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            progress.visibility = View.GONE
            Log.d("displayname","handleSignInResult: ${account!!.displayName}")
            Toast.makeText(this, "sigin successfully", Toast.LENGTH_LONG).show()
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(intent)
            // Signed in successfully, show authenticated UI.
        }
        catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            progress.visibility = View.GONE
            Log.d("errors", "signInResult:failed code =" + e.statusCode)
//            Toast.makeText(this, e.statusCode,Toast.LENGTH_LONG).show()
        }
    }
}

