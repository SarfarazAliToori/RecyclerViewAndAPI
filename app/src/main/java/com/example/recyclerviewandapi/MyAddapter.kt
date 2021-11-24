package com.example.recyclerviewandapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAddapter( private val listener: MyOnClickItemViewListener) : RecyclerView.Adapter<MyAddapter.MyViewHolder>() {

    private val arrayList: ArrayList<MyDataClass> =ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_view, parent, false)
        val myViewHolder = MyViewHolder(view)
        view.setOnClickListener {
           listener.onClickedListener(arrayList[myViewHolder.adapterPosition])
        }
        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hh = arrayList[position]
        holder.uniTitle.text = hh.uniTitle
        holder.contry.text = hh.country
        holder.province.text = hh.province
        holder.alphaCode.text = hh.alphaCode
    }

    fun updateData(updatedItem: ArrayList<MyDataClass>) {
        arrayList.clear()
        arrayList.addAll(updatedItem)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class MyViewHolder(items : View) : RecyclerView.ViewHolder(items) {
        val uniTitle = items.findViewById<TextView>(R.id.tv_uni_name)
        val province = items.findViewById<TextView>(R.id.tv_province)
        val contry = items.findViewById<TextView>(R.id.tv_country)
        val alphaCode = items.findViewById<TextView>(R.id.tv_alpha_code)
    }
}

interface MyOnClickItemViewListener {
    fun onClickedListener(items: MyDataClass)
}