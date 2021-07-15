package com.example.quran.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.quran.Entities.Racine
import com.example.quran.Entities.Verset
import com.example.quran.Entities.VersetFire
import com.example.quran.R
import com.example.quran.database.ServiceDB
import com.example.quran.fragments.RacineSearchedFragment
import com.example.quran.fragments.VersetFragment
import com.example.quran.fragments.VersetPageFragment


class VersetStoredAdapter(var msg: List<VersetFire>):RecyclerView.Adapter<VersetStoredAdapter.ViewHolder>()  {


    var versetsFilterList = ArrayList<VersetFire>()


    private  var dataSet= arrayListOf<VersetFire>()



    init {
        dataSet.addAll(msg)
        versetsFilterList = dataSet
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var TextAr: TextView = itemView.findViewById(R.id.tvTextAr)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewObj = LayoutInflater.from(parent.context).inflate(R.layout.verset_stored_item, parent, false)
        return ViewHolder(viewObj)

    }


    override fun getItemCount(): Int {
        return versetsFilterList.size
    }


    override fun onBindViewHolder(holder: VersetStoredAdapter.ViewHolder, position: Int) {
        holder.TextAr.text = versetsFilterList[position].text_ar



        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity
                val verset :List<Verset> = ServiceDB.database.versetDao().getVersetsById( versetsFilterList[position]?.id.toInt() )
                val versetPageFragment = VersetPageFragment(verset[0])

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