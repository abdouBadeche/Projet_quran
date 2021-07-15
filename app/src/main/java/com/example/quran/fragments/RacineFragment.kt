package com.example.quran.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quran.Entities.Racine
import com.example.quran.R
import com.example.quran.Adapter.RacineAdapter
import kotlinx.android.synthetic.main.recycler_racine_fragment.*

class RacineFragment(var list: List<Racine>) : Fragment(R.layout.recycler_racine_fragment){


    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RacineAdapter.ViewHolder>? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        layoutManager = LinearLayoutManager(requireContext())


        val recyclerView = view?.findViewById<RecyclerView>(R.id.racine_recycler)


        recyclerView?.layoutManager = this.layoutManager




        adapter = RacineAdapter(list)



        recyclerView?.adapter = this.adapter

        search_input.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (adapter as RacineAdapter).filter.filter(newText)
                return false
            }

        })

    }







}