package com.example.mysocialmedia.fragments.albumsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysocialmedia.R
import com.example.mysocialmedia.adapters.AlbumsAdapter
import com.example.mysocialmedia.data.datasource.AlbumAndPhotosDataSource
import com.example.mysocialmedia.databinding.FragmentAlbumsBinding
import com.example.mysocialmedia.di.components.DaggerAlbumAndPhotosComponent
import com.example.mysocialmedia.di.modules.AlbumAndPhotosModule
import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.model.Albums
import com.example.mysocialmedia.utils.NetworkStatus
import com.example.mysocialmedia.utils.SessionController
import com.example.mysocialmedia.utils.hideOptionMenu
import com.example.mysocialmedia.viewmodelfactories.AlbumAndPhotosViewModelFactory
import io.github.rupinderjeet.kprogresshud.KProgressHUD
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumsFragment : Fragment() {


    private lateinit var viewModel: AlbumsViewModel

    private lateinit var binding: FragmentAlbumsBinding

    @Inject
    lateinit var albumAndPhotosDataSource: AlbumAndPhotosDataSource
    @Inject
    lateinit var sessionController: SessionController

    private lateinit var factory: AlbumAndPhotosViewModelFactory
    private lateinit var adapter: AlbumsAdapter
    private lateinit var albums: Albums
    private lateinit var hud: KProgressHUD


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_albums, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
        hideOptionMenu(requireContext())
        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setDetailsLabel("Fetching Albums...")
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)

        factory = AlbumAndPhotosViewModelFactory(albumAndPhotosDataSource)
        viewModel = ViewModelProvider(this, factory)[AlbumsViewModel::class.java]
        viewModel.userDataItem = sessionController.userDetails!!

        albums = Albums()

        AlbumsAdapter(albums) { albumItem ->
            val b = Bundle().apply {
                putInt("AlbumId", albumItem.id)
            }
            findNavController().navigate(R.id.action_albumsFragment_to_photosFragment, b)
        }.also { adapter ->
            this.adapter = adapter
        }
        viewModel.albums.observe(viewLifecycleOwner) { _albums ->
            adapter.setAlbum(_albums)
        }

        lifecycleScope.launchWhenCreated {

            viewModel.status.collectLatest { value ->
                when(value) {
                    NetworkStatus.REQUEST_SENT -> {
                        withContext(Main) {
                            hud.show()
                        }
                    }
                    else -> {
                        withContext(Main) {
                            if (hud.isShowing) {
                                hud.dismiss()
                            }
                        }
                    }
                }
            }
        }
        initRecycleView()
    }

    private fun initRecycleView() {
        binding.recycleViewAlbums.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.recycleViewAlbums.itemAnimator = DefaultItemAnimator()
        binding.recycleViewAlbums.adapter = adapter
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