package com.art.book.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.art.book.R
import com.art.book.model.Art
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
    private val requestManager: RequestManager
): RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private var onItemClickListener: ((String)-> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var artImages: List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_search_row, parent, false)
        return ImageRecyclerAdapter.ImageViewHolder(view)
    }

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageSearchView = holder.itemView.findViewById<ImageView>(R.id.imageSearchView)
        val url = artImages[position]

        holder.itemView.apply {
            requestManager.load(url).into(imageSearchView)
            setOnClickListener {
                onItemClickListener?.let {
                    it(url)
                }
            }
        }
       /* url.apply {
            requestManager.load(url).into(imageSearchView)
            setOnClickListener {
                onItemClickListener?.let {
                    it(url)
                }
            }
        }*/
    }

    override fun getItemCount(): Int {
        return artImages.size
    }
}