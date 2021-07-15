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
import com.example.quran.R
import com.example.quran.database.ServiceDB
import com.example.quran.fragments.RacineSearchedFragment
import com.example.quran.fragments.VersetFragment


class RacineSearchedAdapter(var msg: List<Racine>):RecyclerView.Adapter<RacineSearchedAdapter.ViewHolder>()  {


    var racinesFilterList = ArrayList<Racine>()


    private  var dataSet= arrayListOf<Racine>()



    init {
        dataSet.addAll(msg)
        racinesFilterList = dataSet
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.findViewById(R.id.idRacine)
        var title: TextView = itemView.findViewById(R.id.racineTv)
        var deleteItem: ImageView = itemView.findViewById(R.id.delete_item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewObj = LayoutInflater.from(parent.context).inflate(R.layout.racine_searched_item, parent, false)
        return ViewHolder(viewObj)

    }


    override fun getItemCount(): Int {
        return racinesFilterList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id.text = racinesFilterList[position].id.toString()
        holder.title.text = racinesFilterList[position].racine
        holder.deleteItem.setOnClickListener {
            ServiceDB.database.racineSearchedDao().deleteRacineSearchedByIdRacin(racinesFilterList[position].id)
            val histRacineFragment =RacineSearchedFragment()

            val activity = it!!.context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,  histRacineFragment)

                commit()
            }
        }

        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {


                val activity = v!!.context as AppCompatActivity


                val ayaFragment = VersetFragment(ServiceDB.database.versetDao().getVersetsByRacine(racinesFilterList[position].id) , racinesFilterList[position] )

               activity.supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment,  ayaFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }
        )
    }




}