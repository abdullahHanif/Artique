package com.artique.folderdetail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.artique.R
import com.artique.databinding.LiFolderDetailsBinding
import com.artique.models.AlbumContent
import com.artique.models.MimeType.Movies
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


class FolderDetailsAdapter(
    var list: List<AlbumContent> = listOf(),
) :
    RecyclerView.Adapter<FolderDetailsAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: LiFolderDetailsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LiFolderDetailsBinding.inflate(
            LayoutInflater.from(parent.context)
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]
        Glide.with(holder.binding.root).load(
            item.displayPicture
        ).placeholder(R.drawable.ic_gallery)
            .transform(MultiTransformation(CenterCrop(), RoundedCorners(12)))
            .sizeMultiplier(0.5f).into(holder.binding.displayPictureImageView)

        if (item.mimeType == Movies) {
            holder.binding.playButtonImageView.isVisible = true
            holder.binding.displayPictureImageView.alpha = 0.5f

            Glide.with(holder.binding.root).load(
                R.drawable.ic_play
            ).placeholder(R.drawable.ic_gallery)
                .transform(MultiTransformation(CenterCrop(), RoundedCorners(12)))
                .sizeMultiplier(0.5f).into(holder.binding.playButtonImageView)

        } else {
            holder.binding.playButtonImageView.isVisible = false
            holder.binding.displayPictureImageView.alpha = 1.0f
        }


    }

    override fun getItemCount() = list.size
}