package com.example.mysocialmedia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mysocialmedia.R
import com.example.mysocialmedia.databinding.PostsLayoutBinding
import com.example.mysocialmedia.model.Posts
import com.example.mysocialmedia.model.PostsItem

class PostsAdapter (
    private val posts: Posts,
    private val onPostClicked: (postItem: PostsItem) -> Unit
): RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: PostsLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.posts_layout, parent, false)
        return PostViewHolder(binding, onPostClicked)
    }

    fun setPosts(posts: Posts) {
        this.posts.clear()
        this.posts.addAll(posts)
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postItem: PostsItem = posts[position]
        holder.binding(postItem)
    }

    override fun getItemCount(): Int {
        return posts.size
    }


    inner class PostViewHolder(
        private var binding: PostsLayoutBinding,
        private var onPostClicked: (postItem: PostsItem) -> Unit
    ): ViewHolder(binding.root) {

        fun binding(postsItem: PostsItem) {
            binding.postItem = postsItem
            binding.postParentLayout.setOnClickListener { _ ->
                onPostClicked.invoke(postsItem)
            }
        }
    }
}