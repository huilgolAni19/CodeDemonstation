package com.example.mysocialmedia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.*
import com.example.mysocialmedia.R
import com.example.mysocialmedia.databinding.CommentsLayoutBinding
import com.example.mysocialmedia.model.Comments
import com.example.mysocialmedia.model.CommentsItem

class CommentsAdapter(private val comments: Comments): Adapter<CommentsAdapter.CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: CommentsLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.comments_layout, parent, false)
        return CommentsViewHolder(binding)
    }

    fun setComments(comments: Comments) {
        this.comments.clear()
        this.comments.addAll(comments)
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
         val commentsItem: CommentsItem = comments[position]
        holder.bindData(commentsItem)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    class CommentsViewHolder(private val binding: CommentsLayoutBinding): ViewHolder(binding.root) {
        fun bindData(commentsItem: CommentsItem) {
            binding.commentItem = commentsItem
        }
    }
}