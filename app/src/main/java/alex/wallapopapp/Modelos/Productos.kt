package alex.wallapopapp.Modelos

import android.graphics.Bitmap


data class Productos (
        var key:String,
        var Nombre:String,
        var Categorias:String,
        var Descripcion:String,
        var Precio:String,
        var Foto: String
) {
        constructor() : this(key="",Nombre = "", Categorias = "", Descripcion = "", Precio ="0.0", Foto ="") // this constructor is an explicit
        // "empty" constructor, as seen by Java.
}
