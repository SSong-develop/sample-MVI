package com.ssong_develop.samplemvi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.samplemvi.R
import com.ssong_develop.samplemvi.data.entity.User
import com.ssong_develop.samplemvi.databinding.ItemViewBinding

class MainAdapter(
    private val users : ArrayList<User>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>(){

    class DataViewHolder(private val binding : ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user : User){
            binding.users = user
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size
    
    fun addData(list : List<User>){
        users.addAll(list)
    }
}