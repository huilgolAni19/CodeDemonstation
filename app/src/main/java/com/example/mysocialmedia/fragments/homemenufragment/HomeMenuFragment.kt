package com.example.mysocialmedia.fragments.homemenufragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mysocialmedia.R
import com.example.mysocialmedia.adapters.MainMenuAdapter
import com.example.mysocialmedia.databinding.FragmentHomeMenuBinding
import com.example.mysocialmedia.di.components.DaggerSplashComponent
import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.utils.GridSpacingItemDecoration
import com.example.mysocialmedia.utils.SessionController
import com.example.mysocialmedia.utils.dpToPx
import com.example.mysocialmedia.utils.showOptionMenu
import com.example.mysocialmedia.viewmodelfactories.ViewModelFactoryWithSessionManager
import javax.inject.Inject

class HomeMenuFragment : Fragment() {

    private lateinit var viewModel: HomeMenuViewModel
    private lateinit var binding: FragmentHomeMenuBinding
    private lateinit var factory: ViewModelFactoryWithSessionManager

    @Inject
    lateinit var sessionController: SessionController
    private lateinit var adapter: MainMenuAdapter
    private var userId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_menu, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
        factory = ViewModelFactoryWithSessionManager(sessionController)
        viewModel = ViewModelProvider(this, factory) [HomeMenuViewModel::class.java]
        userId = viewModel.userDataItem.id

        adapter = MainMenuAdapter(viewModel.menuItems) { _, position ->
            val bundle = Bundle().apply {
                putInt("UserId", userId)
            }
            val navController = findNavController()
            when(position) {

                0 -> {
                    navController.navigate(R.id.action_HomeMenu_to_PostsFragment, bundle)
                }
                1 -> {
                    navController.navigate(R.id.action_HomeMenuFragment_to_albumsFragment, bundle)
                }
                2 -> {
                    navController.navigate(R.id.action_HomeMenuFragment_to_todosFragment)
                }
            }
        }
        showOptionMenu(requireContext())
        initRecycleView()
    }


    private fun initRecycleView() {
        binding.recycleViewMainMenu.layoutManager = GridLayoutManager(requireContext(), 2)
        val itemDecorator = GridSpacingItemDecoration(3, dpToPx(requireContext(), 2), true)
        binding.recycleViewMainMenu.addItemDecoration(itemDecorator)
        binding.recycleViewMainMenu.adapter = adapter
    }

    private fun inject() {
        DaggerSplashComponent
            .builder().apply {
                commonServicesProvider(CommonServicesProvider(requireContext().applicationContext))
            }.build()
            .inject(this)
    }
}