package com.test.app.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.app.R
import com.test.app.databinding.ListItemAlbumBinding
import com.test.app.model.Album
import com.squareup.picasso.Picasso

class AlbumListAdapter :
    RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Album)
    }

    var albumsList: List<Album> = ArrayList()
    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumViewHolder {
        val listItemAlbumBinding: ListItemAlbumBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.getContext()),
            R.layout.list_item_album, viewGroup, false
        )
        return AlbumViewHolder(listItemAlbumBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album: Album = albumsList[position]
        holder.listItemAlbumBinding.album = album
        holder.listItemAlbumBinding.onClickListener = listener
    }

    fun setItemsAndListener(
        albumList: List<Album>,
        listener: OnItemClickListener
    ) {
        this.albumsList = albumList
        this.listener = listener
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return albumsList.size
    }

    inner class AlbumViewHolder(val listItemAlbumBinding: ListItemAlbumBinding) :
        RecyclerView.ViewHolder(listItemAlbumBinding.getRoot())

    object ImageBindingAdapter {
        @BindingAdapter("bind:imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String) {
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(view)
        }

        @JvmStatic
        @BindingAdapter("albumItems")
        fun RecyclerView.bindItems(items: List<Album>) {
            val adapter = adapter as AlbumListAdapter
            adapter.setItemsAndListener(items, object : OnItemClickListener {
                override fun onItemClick(album: Album) {
                    adapter.listener?.onItemClick(album)
                }

            })
        }
    }


}
