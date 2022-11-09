package com.example.mysocialmedia.fragments.userprofile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.mysocialmedia.R
import com.example.mysocialmedia.databinding.FragmentUserProfileBinding
import com.example.mysocialmedia.di.components.DaggerUserProfileComponent
import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.utils.SessionController
import com.example.mysocialmedia.utils.hideOptionMenu
import com.example.mysocialmedia.viewmodelfactories.ViewModelFactoryWithSessionManager
import javax.inject.Inject

class UserProfileFragment : Fragment() {

    private lateinit var viewModel: UserProfileViewModel

    private lateinit var factory: ViewModelFactoryWithSessionManager
    private lateinit var binding: FragmentUserProfileBinding


    @Inject
    lateinit var sessionController: SessionController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
        factory = ViewModelFactoryWithSessionManager(sessionController)
        viewModel = ViewModelProvider(this, factory)[UserProfileViewModel::class.java]
        binding.userProfileViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        hideOptionMenu(requireContext())
    }

    private fun inject() {
        DaggerUserProfileComponent.builder().apply {
            commonServicesProvider(CommonServicesProvider(requireContext().applicationContext))
        }
            .build()
            .inject(this)
    }
}