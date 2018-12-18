package alex.wallapopapp.Vistas

import alex.wallapopapp.R
import android.content.Intent
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private var email: TextInputEditText?=null
    private var passw: TextInputEditText?=null
    private var progress:ProgressBar?=null
    private var auth:FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email=findViewById(R.id.txtEmailLog)
        passw=findViewById(R.id.txtPassLog)
        progress=findViewById(R.id.progressBar2)
        auth=FirebaseAuth.getInstance()
    }

    override fun onDestroy() {
        super.onDestroy()
        auth?.signOut()
    }

    fun forgorPassword(v: View){

    }

    fun register(v:View){
        startActivity(Intent(this,Registro::class.java))
    }

    fun login(v:View){
        val user=email?.text.toString()
        val pass=passw?.text.toString()
        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)){
            progress?.visibility=View.VISIBLE

            auth?.signInWithEmailAndPassword(user,pass)
                ?.addOnCompleteListener(this){

                    if(it.isSuccessful){
                        progress?.visibility=View.GONE
                        action()
                    }else
                        Toast.makeText(applicationContext,"Error al iniciar sesion",Toast.LENGTH_LONG)
                        progress?.visibility=View.GONE
                }
        }
    }

    private fun action(){
        startActivity(Intent(this,Main2Activity::class.java))
    }
}
