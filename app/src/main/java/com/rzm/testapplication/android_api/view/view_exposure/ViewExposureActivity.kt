package com.rzm.testapplication.android_api.view.view_exposure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rzm.testapplication.R

class ViewExposureActivity : AppCompatActivity() {

    var list = ArrayList<String>()

    init {
        var i = 100
        do {
            list.add("我是$i")
            i--
        } while (i >= 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_exposure)
        var recyclerView = findViewById<RecyclerView>(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter(list)
    }

    inner class RecyclerAdapter(private val textList: ArrayList<String>) :
        RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MyViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int = textList.size ?: 0

        override fun onBindViewHolder(holder: RecyclerAdapter.MyViewHolder, position: Int) {
            val textpos = textList[position]
            holder.title.text = textpos

            if (position == 20) {
                holder.container.visibility = View.VISIBLE
                holder.title.visibility = View.GONE
                (holder.container as ExposureViewGroup).setData(textpos)
            } else {
                holder.container.visibility = View.GONE
                holder.title.visibility = View.VISIBLE
            }

            holder.itemView.setOnClickListener {
                Toast.makeText(holder.itemView.context, "${holder.title.text}", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.mine_title)
            val container: ViewGroup = itemView.findViewById(R.id.container)
        }
    }
}