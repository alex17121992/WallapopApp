package alex.wallapopapp.Vistas

import alex.wallapopapp.Controladores.ProductosAdapter
import alex.wallapopapp.Modelos.Productos
import alex.wallapopapp.R
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ListaProductos : android.support.v4.app.Fragment(){

    private var producto:Productos?=null
    private var dbReference: DatabaseReference?=null //Variable para conseguir la referencia a la BD
    private var database: FirebaseDatabase?=null
    private var rv:RecyclerView?=null
    private var keys:ArrayList<String>?=null
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var lista:ArrayList<Productos>?=null
    private var auth:FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        val userBD=auth?.currentUser!!
        dbReference=database?.reference?.child("User")?.child(userBD?.uid!!)?.child("Articulos")
        lista=ArrayList<Productos>()
        keys=ArrayList<String>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.fragment_lista_productos, container, false)
        rv=v?.findViewById(R.id.rv) as RecyclerView
        var productoListener= dbReference

        productoListener?.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                lista?.clear()
                for (snapshot: DataSnapshot in p0.children) {
                    var productos = snapshot.getValue(Productos::class.java)
                    productos?.key=snapshot.key.toString()
                    lista?.add(
                        Productos(
                            productos?.key!!,
                            productos?.Nombre!!,
                            productos?.Categorias!!,
                            productos?.Descripcion!!,
                            productos?.Precio!!,
                            productos?.Foto!!
                        )
                    )

                }
            }
        })
        val adaptador = ProductosAdapter(lista!!)
        rv?.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val layoutManager= GridLayoutManager(context,2)
        rv?.layoutManager=layoutManager
        rv?.setHasFixedSize(true)
        return v
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
         * @return A new instance of fragment ListaProductos.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListaProductos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
