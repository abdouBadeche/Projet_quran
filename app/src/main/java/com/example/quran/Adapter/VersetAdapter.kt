package com.example.quran.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.quran.Entities.Verset
import com.example.quran.R
import com.example.quran.database.ServiceDB
import com.example.quran.fragments.VersetPageFragment
import kotlin.collections.ArrayList

class VersetAdapter(var msg: List<Verset>):RecyclerView.Adapter<VersetAdapter.ViewHolder>(){


    var versetsFilterList = ArrayList<Verset>()


    private  var dataSet= arrayListOf<Verset>()

    init {
        dataSet.addAll(msg)
        versetsFilterList = dataSet
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var idVerset: TextView = itemView.findViewById(R.id.tvIdVerset)
        var idSourat: TextView = itemView.findViewById(R.id.tvIdSourat)
        var TextAr: TextView = itemView.findViewById(R.id.tvTextAr)
        var TextAn: TextView = itemView.findViewById(R.id.tvTextAn)
        var SouratName: TextView = itemView.findViewById(R.id.tvNameSourat)
        var location: TextView = itemView.findViewById(R.id.tvTextAn2)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewObj = LayoutInflater.from(parent.context).inflate(R.layout.verset_item, parent, false)
        return ViewHolder(viewObj)
    }


    override fun getItemCount(): Int {
        return versetsFilterList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idVerset.text = versetsFilterList[position].verset_sourat_id.toString()
        holder.idSourat.text = versetsFilterList[position].sourat_id.toString()
        holder.TextAr.text = versetsFilterList[position].text_ar
        holder.TextAn.text = versetsFilterList[position].text_an
        val sourat_this_verset = ServiceDB.database.souratDao().getSouratById(versetsFilterList[position].sourat_id)


        holder.SouratName.text = sourat_this_verset[0]?.nom
        holder.location.text = sourat_this_verset[0]?.location



        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity

                val versetPageFragment = VersetPageFragment(versetsFilterList[position])

                activity.supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment,  versetPageFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }
        )
    }




}