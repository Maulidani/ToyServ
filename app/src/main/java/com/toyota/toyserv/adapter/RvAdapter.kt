package com.toyota.toyserv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.belajarviewbinding.ApiClient
import com.toyota.toyserv.databinding.RvItemBinding
import com.toyota.toyserv.model.DataResult


class RvAdapter(private val dataList: ArrayList<DataResult>) :
    RecyclerView.Adapter<RvAdapter.DataViewHolder>() {

    inner class DataViewHolder(val binding: RvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val sb: StringBuilder = StringBuilder()

        fun bind(dataResult: DataResult) {
            binding.tvTitleEbook.text = dataResult.title
            binding.tvAuthorEbook.text = dataResult.author
            binding.tvCategoryEbook.text = dataResult.category
            binding.tvDateEbook.text = dataResult.created_at

            sb.append(ApiClient.URL)
            sb.append(dataResult.thumbnail)

            Glide.with(itemView)
                .load(sb.toString())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imgEbook)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}