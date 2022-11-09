package com.example.mysocialmedia.fragments.photosfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mysocialmedia.R
import com.example.mysocialmedia.adapters.PhotosAdapter
import com.example.mysocialmedia.data.datasource.AlbumAndPhotosDataSource
import com.example.mysocialmedia.databinding.FragmentPhotosBinding
import com.example.mysocialmedia.di.components.DaggerAlbumAndPhotosComponent
import com.example.mysocialmedia.di.modules.AlbumAndPhotosModule
import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.model.Photos
import com.example.mysocialmedia.utils.*
import com.example.mysocialmedia.viewmodelfactories.AlbumAndPhotosViewModelFactory
import com.google.gson.Gson
import io.github.rupinderjeet.kprogresshud.KProgressHUD
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotosFragment : Fragment() {

    private lateinit var binding: FragmentPhotosBinding
    @Inject
    lateinit var dataSource: AlbumAndPhotosDataSource
    private lateinit var factory: AlbumAndPhotosViewModelFactory
    private lateinit var viewModel: PhotosViewModel
    private lateinit var adapter: PhotosAdapter
    private lateinit var photos: Photos
    private lateinit var hud: KProgressHUD

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photos, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()

        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setDetailsLabel("Fetching Photos...")
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)

        factory = AlbumAndPhotosViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, factory)[PhotosViewModel::class.java]
        val id = requireArguments().getInt("AlbumId")
        viewModel.id = id
        photos = Photos()
        PhotosAdapter(photos) {
            //log("Clicked: ${it.id}")
            val item = Gson().toJson(it)
            val b = bundleOf("PhotoItem" to item)
            findNavController().navigate(R.id.action_photosFragment_to_fullPicFragment, b)
        }.also { adapter ->
            this.adapter = adapter
        }

        initRecycleView()

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.setPhotos(it)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.status.collectLatest { value ->
                when(value) {
                    NetworkStatus.REQUEST_SENT -> {
                        withContext(Main) {
                            hud.show()
                        }
                    } else -> {
                        withContext(Main) {
                            hud.dismiss()
                        }
                    }
                }
            }
        }


    }

    private fun initRecycleView() {

        val itemDecoration = GridSpacingItemDecoration(3, dpToPx(requireContext(), 2), true)
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        
        binding.recycleViewPhotosItems.apply {
            layoutManager = gridLayoutManager
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(itemDecoration)
        }.also {
            it.adapter = adapter
        }
    }

    private fun inject() {
        DaggerAlbumAndPhotosComponent.builder()
            .apply {
                commonServicesProvider(CommonServicesProvider(requireContext().applicationContext))
                albumAndPhotosModule(AlbumAndPhotosModule())
            }
            .build()
            .inject(this)
    }

}