package alex.wallapopapp.Vistas

import alex.wallapopapp.Modelos.Productos
import alex.wallapopapp.Modelos.Usuario
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import alex.wallapopapp.R
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_cuenta.*
import kotlinx.android.synthetic.main.fragment_cuenta.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Cuenta : Fragment() {
    private var auth:FirebaseAuth?=null
    private var dbReference: DatabaseReference?=null //Variable para conseguir la referencia a la BD
    private var database: FirebaseDatabase?=null
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v=inflater.inflate(R.layout.fragment_cuenta, container, false)
        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        val userBD=auth?.currentUser!!
        dbReference=database?.reference?.child("User")?.child(userBD?.uid!!)
        var userListener= dbReference
        var nomb=v.findViewById<TextView>(R.id.txtNombreC)
        var apell=v.findViewById<TextView>(R.id.txtApellC)
        var img=v.findViewById<ImageView>(R.id.imgFotoC)
        userListener?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var user=p0.getValue(Usuario::class.java)
                nomb.text = user!!.Nombre
                apell.text = user!!.Apellido
                var decodedString = Base64.decode(user.Foto, Base64.DEFAULT)
                var decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                img.setImageBitmap(decodedByte)
            }
        })
        return v
    }

    // TODO: Rename method, update argument and hook method into UI event
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
         * @return A new instance of fragment Cuenta.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Cuenta().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
