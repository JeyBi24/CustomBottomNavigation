package uz.andronaut.youin.auth

import android.R.attr
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_authentication.*

import uz.andronaut.gtg.BaseActivity
import uz.andronaut.gtg.Constants
import uz.andronaut.youin.MainActivity
import uz.andronaut.youin.R
import uz.andronaut.youin.utils.TinyDB
import java.util.*


class AuthenticationAcitivity : BaseActivity() {


    private val TG_PASSPORT_RESULT = 352
    private val RC_SIGN_IN = 351

    private val TAG = "LLSSL"



    lateinit var vibrator: Vibrator

    lateinit var mGoogleSignInClient : GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onActivityDidCreate(applicationPrefences: SharedPreferences, tinyDB: TinyDB) {

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        auth = FirebaseAuth.getInstance()


        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator







        rvInstagram.setOnClickListener {
            vibrate()
            startActivity(Intent(this,MainActivity::class.java))
        }

        rvGoogle.setOnClickListener {
            vibrate()
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }


    }

    override fun onLayoutSet(): Int {
       return R.layout.activity_authentication
    }

    fun vibrate() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(50);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_SIGN_IN) {

                val task =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e)
                    // ...
                }
            }


        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.d(TAG, "CANCELLED")
        }

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser


    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser



                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // ...


                }

                // ...
            }
    }



}
