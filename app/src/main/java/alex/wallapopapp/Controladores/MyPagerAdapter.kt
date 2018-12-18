package alex.wallapopapp.Controladores

import alex.wallapopapp.Vistas.AnadirProducto
import alex.wallapopapp.Vistas.Cuenta
import alex.wallapopapp.Vistas.ListaProductos
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ListaProductos()
            }
            1 ->AnadirProducto()
            else -> {
                return Cuenta()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Articulos"
            1 -> "Dar Alta Articulo"
            else -> {
                return "Tu cuenta"
            }
        }
    }
}