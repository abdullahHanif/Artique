package com.artique.folders.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artique.R
import com.artique.databinding.LiFoldersBinding
import com.artique.models.AlbumContent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


class FoldersAdapter(
    var list: List<AlbumContent> = listOf(),
    var onClick: (AlbumContent) -> Unit = {},
) :
    RecyclerView.Adapter<FoldersAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: LiFoldersBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LiFoldersBinding.inflate(
            LayoutInflater.from(parent.context)
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.binding.folderDisplayImageView).load(
            list[position].displayPicture
        ).placeholder(R.drawable.ic_gallery)
            .transform(MultiTransformation(CenterCrop(), RoundedCorners(12)))
            .sizeMultiplier(0.5f).into(holder.binding.folderDisplayImageView)

        holder.binding.folderNameTextView.text = list[position].displayName
        holder.binding.folderCountTextView.text = "${list[position].count} items"
        holder.binding.root.setOnClickListener { onClick(list[position]) }
    }

    override fun getItemCount() = list.size
}