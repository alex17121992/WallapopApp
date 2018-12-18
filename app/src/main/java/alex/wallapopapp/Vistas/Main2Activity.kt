package alex.wallapopapp.Vistas

import alex.wallapopapp.Controladores.MyPagerAdapter
import android.support.design.widget.TabLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import alex.wallapopapp.R
import android.net.Uri
import kotlinx.android.synthetic.main.activity_main2.*


class Main2Activity : AppCompatActivity() ,ListaProductos.OnFragmentInteractionListener,AnadirProducto.OnFragmentInteractionListener,Cuenta.OnFragmentInteractionListener{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        setSupportActionBar(toolbar)
        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        container.adapter = fragmentAdapter

        tabs.setupWithViewPager(container)
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))


    }
    override fun onFragmentInteraction(uri: Uri) {

    }

}


