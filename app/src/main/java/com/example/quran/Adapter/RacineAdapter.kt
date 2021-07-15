package com.example.quran.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.quran.Entities.RacinSearched
import com.example.quran.Entities.Racine
import com.example.quran.R
import com.example.quran.database.ServiceDB
import com.example.quran.fragments.VersetFragment
import kotlin.collections.ArrayList

class RacineAdapter(var msg: List<Racine>):RecyclerView.Adapter<RacineAdapter.ViewHolder>() , Filterable {


    var racinesFilterList = ArrayList<Racine>()
    var user_search = false ;

    private  var dataSet= arrayListOf<Racine>()

    init {
        dataSet.addAll(msg)
        racinesFilterList = dataSet
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.findViewById(R.id.idRacine)
        var title: TextView = itemView.findViewById(R.id.racineTv)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewObj = LayoutInflater.from(parent.context).inflate(R.layout.racine_item, parent, false)
        return ViewHolder(viewObj)
    }


    override fun getItemCount(): Int {
        return racinesFilterList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id.text = racinesFilterList[position].id.toString()
        holder.title.text = racinesFilterList[position].racine

        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity
                if( getItemCount() != dataSet.size ){
                    ServiceDB.database.racineSearchedDao().insertRacineSearched(RacinSearched(racinesFilterList[position].id , racinesFilterList[position].id))
                }



                val versetFragment = VersetFragment(ServiceDB.database.versetDao().getVersetsByRacine(racinesFilterList[position].id) , racinesFilterList[position] ) ;

                activity.supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment,  versetFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }
        )

    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    racinesFilterList = dataSet
                } else {
                    val resultList = ArrayList<Racine>()
                    for (row in dataSet) {
                            if (row.racine.contains(charSearch)) {
                            resultList.add(row)
                        }
                    }
                    racinesFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = racinesFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                racinesFilterList = results?.values as ArrayList<Racine>
                notifyDataSetChanged()
            }

        }
    }



}