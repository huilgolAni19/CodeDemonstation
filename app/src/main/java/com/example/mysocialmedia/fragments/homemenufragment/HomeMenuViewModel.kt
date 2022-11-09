package com.example.mysocialmedia.fragments.homemenufragment

import androidx.lifecycle.ViewModel
import com.example.mysocialmedia.R
import com.example.mysocialmedia.model.MenuItem
import com.example.mysocialmedia.model.UserDataItem
import com.example.mysocialmedia.utils.SessionController

class HomeMenuViewModel(sessionController: SessionController): ViewModel() {

    val userDataItem: UserDataItem = sessionController.userDetails!!
    val menuItems: ArrayList<MenuItem> = ArrayList()

    init {

        val menuItem1 = MenuItem(R.drawable.ic_posts, "Post")
        val menuItem2 = MenuItem(R.drawable.ic_albums, "Albums")
        val menuItem3 = MenuItem(R.drawable.ic_todo, "Todos")

        menuItems.add(menuItem1)
        menuItems.add(menuItem2)
        menuItems.add(menuItem3)
    }


}