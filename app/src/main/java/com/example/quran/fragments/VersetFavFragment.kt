package com.example.quran.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quran.Adapter.RacineSearchedAdapter
import com.example.quran.Adapter.VersetFavAdapter
import com.example.quran.R
import com.example.quran.database.ServiceDB

class VersetFavFragment:Fragment(R.layout.recycler_verset_fav_fragment){

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<VersetFavAdapter.ViewHolder>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(requireContext())

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_verset_fav)
        val msg = ServiceDB.database.versetFavDeo()?.getVersetFavList()

        recyclerView?.layoutManager = this.layoutManager

        adapter = VersetFavAdapter(msg)


        recyclerView?.adapter = this.adapter

    }







}