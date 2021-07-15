package com.example.quran.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quran.Entities.Racine
import com.example.quran.R
import com.example.quran.Adapter.VersetAdapter
import com.example.quran.Entities.Verset
import com.example.quran.database.ServiceDB
import kotlinx.android.synthetic.main.recycler_verset_fragment.*

class VersetFragment(var list: List<Verset> , var racine : Racine) : Fragment(R.layout.recycler_verset_fragment){


    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<VersetAdapter.ViewHolder>? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_racine_text.text = racine.racine 

        layoutManager = LinearLayoutManager(requireContext())


        val recyclerView = view?.findViewById<RecyclerView>(R.id.verset_recycler)


        recyclerView?.layoutManager = this.layoutManager




        adapter = VersetAdapter(list)



        recyclerView?.adapter = this.adapter

    }







}