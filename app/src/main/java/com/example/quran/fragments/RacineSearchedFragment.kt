package com.example.quran.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quran.Adapter.RacineSearchedAdapter
import com.example.quran.R
import com.example.quran.database.ServiceDB

class RacineSearchedFragment:Fragment(R.layout.recycler_searched_racine_fragment){

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RacineSearchedAdapter.ViewHolder>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(requireContext())

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_searched_racine)
        val msg = ServiceDB.database.racineSearchedDao()?.getRacineSearchedList()

        recyclerView?.layoutManager = this.layoutManager

        adapter = RacineSearchedAdapter(msg)


        recyclerView?.adapter = this.adapter

    }







}