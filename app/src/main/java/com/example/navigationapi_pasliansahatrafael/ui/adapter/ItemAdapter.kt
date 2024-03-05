package com.example.navigationapi_pasliansahatrafael.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navigationapi_pasliansahatrafael.data.remote.response.ItemsItem
import com.example.navigationapi_pasliansahatrafael.databinding.ListItemBinding
import com.example.navigationapi_pasliansahatrafael.ui.DetailActivity


class ItemAdapter (private val context: Context) : ListAdapter<ItemsItem, ItemAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("USERNAME", item.login)
            context.startActivity(intent)
        }
    }
    class MyViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsItem){
            binding.tvItemName.text = "${item.login}"
            Glide.with(binding.root)
                .load(item.avatarUrl)
                .into(binding.imgItemPhoto)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}