package com.example.quran

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.quran.database.ServiceDB
import com.example.quran.fragments.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getSupportActionBar()?.hide()
        setContentView(R.layout.activity_main)



        ServiceDB.database.racineDao().getRacines() ;

        val drawerLayout: DrawerLayout = drawer_layout
        val navView: NavigationView = nav_view

        if (navView != null) {
            navView.setNavigationItemSelectedListener(this);
        }

        menu_btn.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }



        val racines= ServiceDB.database.racineDao()?.getRacines()

        val racineFragment = RacineFragment(racines!!)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,  racineFragment)
            commit()
        }



    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }


    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {

            R.id.nav_search -> {

                val racines= ServiceDB.database.racineDao()?.getRacines()

                val racineFragment = RacineFragment(racines!!)
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment,  racineFragment)
                    commit()
                }
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()

            }

            R.id.nav_history -> {

                val racineFragment = RacineSearchedFragment()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment,  racineFragment)
                    commit()
                }
                Toast.makeText(this, "History", Toast.LENGTH_SHORT).show()

            }
            R.id.nav_fav -> {
                val versetFavFragment = VersetFavFragment()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment,  versetFavFragment)
                    commit()
                }
                Toast.makeText(this, "Favories", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_firebase -> {
                val versetStoredFragment = VersetStoredFragment()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment,  versetStoredFragment)
                    commit()
                }
                Toast.makeText(this, "Stord", Toast.LENGTH_SHORT).show()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }






}