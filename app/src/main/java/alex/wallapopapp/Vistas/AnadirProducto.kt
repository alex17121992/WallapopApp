package alex.wallapopapp.Vistas

import alex.wallapopapp.Modelos.Productos
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import alex.wallapopapp.R
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.support.design.widget.TextInputEditText
import android.util.Base64
import android.util.Log
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_anadir_producto.*
import java.io.ByteArrayOutputStream
import java.lang.Double.parseDouble

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AnadirProducto.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AnadirProducto.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AnadirProducto : Fragment() {

    private lateinit var txtNombre: TextInputEditText
    private lateinit var txtDesc: TextInputEditText
    private lateinit var txtPrecio: TextInputEditText
    private lateinit var spCat: Spinner
    private lateinit var imgArt:ImageView
    private lateinit var btnImgCargar:ImageButton
    private lateinit var progress: ProgressBar
    private lateinit var btnAlta:Button
    private lateinit var productos: Productos
    private var dbReference: DatabaseReference?=null //Variable para conseguir la referencia a la BD
    private var database: FirebaseDatabase?=null
    private var auth: FirebaseAuth?=null
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var uri : Uri?=null
    var bitmap: Bitmap?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    /**
     * Inicializar los componentes pasandole la vista inflada y la bd
     */
    private fun init_components(v:View){
        txtNombre=v.findViewById<TextInputEditText>(R.id.artNom)
        txtDesc=v.findViewById(R.id.artDes)
        txtPrecio=v.findViewById(R.id.artPre)
        spCat=v.findViewById(R.id.spCategorias)
        imgArt=v.findViewById(R.id.imgArt)
        btnImgCargar=v.findViewById(R.id.btnImgCargar)
        progress=v.findViewById(R.id.proCargar2)
        btnAlta=v.findViewById(R.id.btnAlta)
        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v=inflater.inflate(R.layout.fragment_anadir_producto, container, false)
        init_components(v)
        btnImgCargar.setOnClickListener({
            cargarFoto()
        })
        btnAlta.setOnClickListener({
            insertar()
        })
        return v
    }

    fun insertar(){
        var nombre=txtNombre.text.toString()
        var desc=txtDesc.text.toString()
        var precio=txtPrecio.text.toString()
        var cat=spCat.selectedItem.toString()
        var img=convertirImgString(bitmap)
        val user: FirebaseUser? = auth?.currentUser!!
        dbReference= database!!.reference.child("User").child(user?.uid!!).child("Articulos").push()
        dbReference!!.child("Nombre").setValue(nombre)
        dbReference!!.child("Descripcion").setValue(desc)
        dbReference!!.child("Precio").setValue(precio)
        dbReference!!.child("Categorias").setValue(cat)
        dbReference!!.child("Foto").setValue(img)
        Toast.makeText(context,"Se ha añadido correctamente el articulo",Toast.LENGTH_LONG)
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
                imgArt.setImageURI(uri)
                bitmap= MediaStore.Images.Media.getBitmap(context?.contentResolver,uri)
                imgArt.setImageBitmap(bitmap)
            }
        }

    }


    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AnadirProducto.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AnadirProducto().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
