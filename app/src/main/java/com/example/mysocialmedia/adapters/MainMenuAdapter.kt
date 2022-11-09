package com.example.mysocialmedia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mysocialmedia.R
import com.example.mysocialmedia.databinding.MenuItemBinding
import com.example.mysocialmedia.model.MenuItem

class MainMenuAdapter(private val menuItems: ArrayList<MenuItem>,
                      private val onItemClickListener: (menuItem: MenuItem, position: Int) -> Unit)
    : RecyclerView.Adapter<MainMenuAdapter.MenuItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: MenuItemBinding = DataBindingUtil.inflate(inflater, R.layout.menu_item, parent, false)
        return MenuItemViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val menuItem: MenuItem = menuItems[position]
        holder.bind(menuItem, position)
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    inner class MenuItemViewHolder(private val binding: MenuItemBinding,
                                   private val onItemClickListener: (menuItem: MenuItem, position: Int) -> Unit): RecyclerView.ViewHolder(binding.root) {
        fun bind(menuItem: MenuItem, position: Int) {
            binding.menuItem = menuItem
            binding.parentLayout.setOnClickListener {
                onItemClickListener.invoke(menuItem, position)
            }
            binding.itemIcon.setOnClickListener {
                onItemClickListener.invoke(menuItem, position)
            }
        }
    }
}