package alex.wallapopapp.Vistas

import alex.wallapopapp.R
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.TextInputEditText
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registro.*
import java.io.ByteArrayOutputStream
import java.lang.ref.PhantomReference

class Registro : AppCompatActivity() {

    private lateinit var email:TextInputEditText
    private lateinit var passw:TextInputEditText
    private lateinit var nom:TextInputEditText
    private lateinit var appell:TextInputEditText
    private lateinit var progress:ProgressBar
    private lateinit var btnPic:ImageButton
    private var dbReference: DatabaseReference?=null //Variable para conseguir la referencia a la BD
    private var database:FirebaseDatabase?=null
    private var auth:FirebaseAuth?=null
    var uri : Uri?=null
    var bitmap: Bitmap?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        email= this.findViewById(R.id.txtEmail)
        passw=this.findViewById(R.id.txtPass)
        nom=this.findViewById(R.id.txtNombre)
        appell=this.findViewById(R.id.txtApell)
        progress= this.findViewById(R.id.proCargar)
        btnPic=this.findViewById(R.id.imgBtnReg)
        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()
        dbReference= database!!.reference.child("User")

        btnPic.setOnClickListener({
            cargarFoto()
        })

    }

    fun register(v: View){
        createNewAccount()
    }

    private fun createNewAccount(){
        val userL= email.text.toString()
        val pass=passw.text.toString()
        val name=nom.text.toString()
        val surname=appell.text.toString()
        val foto = convertirImgString(bitmap)

        if(!TextUtils.isEmpty(userL) && !TextUtils.isEmpty(pass)){
            progress.visibility=View.VISIBLE

            auth?.createUserWithEmailAndPassword(userL,pass)?.addOnCompleteListener(this){

                if(it.isSuccessful){
                    val user: FirebaseUser? = auth?.currentUser!!
                    verifyEmail(user)

                    val userBD = dbReference!!.child(user?.uid!!)
                    userBD.child("Nombre").setValue(name)
                    userBD.child("Apellido").setValue(surname)
                    userBD.child("Foto").setValue(foto)
                    action()
                }else
                    progress.visibility=View.GONE
            }
        }
    }

    private fun action(){
        startActivity(Intent(this,Login::class.java))
    }

    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()?.addOnCompleteListener(this){
            task ->

            if(task.isComplete){
                Toast.makeText(this,"Email enviado",Toast.LENGTH_LONG).show()
            }else
                Toast.makeText(this,"Error al enviar el email",Toast.LENGTH_LONG).show()
        }
    }

    private fun convertirImgString(bitmap: Bitmap?): String {
        var array = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG,100,array)
        var imagenByte=array.toByteArray()
        var imagenString= Base64.encodeToString(imagenByte, Base64.DEFAULT)
        return imagenString
    }

    fun cargarFoto(){
        var i= Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        i.setType("image/")
        startActivityForResult(i,10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            10 -> {uri = data?.getData()!!
                imgReg.setImageURI(uri)
                bitmap= MediaStore.Images.Media.getBitmap(applicationContext?.contentResolver,uri)
                imgReg.setImageBitmap(bitmap)
            }
        }

    }


}
