package alex.wallapopapp.Modelos

data class Usuario(var Nombre:String,var Apellido:String,var Foto:String){
    constructor() : this(Nombre = "",Apellido = "",Foto="") // this constructor is an explicit
    // "empty" constructor, as seen by Java.
}