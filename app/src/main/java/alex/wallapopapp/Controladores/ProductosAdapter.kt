package alex.wallapopapp.Controladores

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import alex.wallapopapp.Modelos.Productos
import alex.wallapopapp.R
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.R.attr.onClick




class ProductosAdapter (arrayList: ArrayList<Productos>): RecyclerView.Adapter<ProductosAdapter.ViewHolder>() {
    var lista:ArrayList<Productos>?=null
    init {
        this.lista=arrayList
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0?.context).inflate(R.layout.item_recycler,p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int){
        p0.nombre?.text= lista?.get(p1)?.Nombre.toString()
        p0.precio?.text= lista?.get(p1)?.Precio.toString()
        var decodedString = Base64.decode(lista?.get(p1)?.Foto, Base64.DEFAULT)
        var decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        p0.img?.setImageBitmap(decodedByte)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var nombre:TextView?=itemView.findViewById(R.id.txtNombre)
        var precio:TextView?=itemView.findViewById(R.id.txtPrecio)
        var img:ImageView?=itemView.findViewById(R.id.imgPro)

    }


}