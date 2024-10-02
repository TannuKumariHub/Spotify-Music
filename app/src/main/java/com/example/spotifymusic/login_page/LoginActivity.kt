package com.example.spotifymusic.login_page

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.spotifymusic.R
import com.example.spotifymusic.databinding.ActivityLoginBinding
import com.example.spotifymusic.home_page.HomeActivity
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val CLIENT_ID = "9dc6e542835c4faf8395b7e7c36e4348"
    private val REDIRECT_URI = "spotifymusic://callback"
    private lateinit var data: String
    private var iS_Login: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("SpotifyShared", MODE_PRIVATE)
//        data= sharedPreferences.getString("TOKEN_STORED","N").toString()
        iS_Login = sharedPreferences.getBoolean("IS_LOGIN", false)


        if (iS_Login) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.logIn.setOnClickListener {
            val builder = AuthorizationRequest.Builder(
                    CLIENT_ID,
                    AuthorizationResponse.Type.TOKEN,
                    REDIRECT_URI
                )

            builder.setScopes(arrayOf("streaming"))
            builder.setShowDialog(true)
            val request = builder.build()
            AuthorizationClient.openLoginInBrowser(this, request)
        }


    }

    override fun onNewIntent(intent: Intent) {

        super.onNewIntent(intent)


        val uri: Uri? = intent.data
        uri?.let {
            val response = AuthorizationResponse.fromUri(it)

            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    data = response.accessToken
                    Log.d("jhreyfg", "onNewIntent: $data")
                    val edit = sharedPreferences.edit()
                    edit.putString("TOKEN_STORED", data)
                    edit.putBoolean("IS_LOGIN", true)
                    edit.apply()


                    // Handle successful response
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                AuthorizationResponse.Type.ERROR -> {
                    // Handle error response
                }

                else -> {
                    // Handle other cases
                }
            }
        }
    }

}