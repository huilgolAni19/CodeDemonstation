package com.example.mysocialmedia.fragments.postsfragments

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
import com.example.mysocialmedia.adapters.PostsAdapter
import com.example.mysocialmedia.data.datasource.PostsAndCommentsDataSource
import com.example.mysocialmedia.databinding.FragmentPostsBinding
import com.example.mysocialmedia.di.components.DaggerPostAndCommentComponent
import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.di.modules.PostAndCommentModule
import com.example.mysocialmedia.model.Posts
import com.example.mysocialmedia.utils.NetworkStatus
import com.example.mysocialmedia.utils.SessionController
import com.example.mysocialmedia.utils.hideOptionMenu
import com.example.mysocialmedia.viewmodelfactories.PostsAndCommentsViewModelFactory
import io.github.rupinderjeet.kprogresshud.KProgressHUD
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostsFragment : Fragment() {


    private lateinit var viewModel: PostsViewModel
    private lateinit var binding: FragmentPostsBinding

    @Inject
    lateinit var dataSource: PostsAndCommentsDataSource

    @Inject
    lateinit var sessionController: SessionController
    private lateinit var factory: PostsAndCommentsViewModelFactory

    private lateinit var adapter: PostsAdapter
    private lateinit var posts: Posts
    private lateinit var hud: KProgressHUD

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_posts, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
        hideOptionMenu(requireContext())
        factory = PostsAndCommentsViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, factory)[PostsViewModel::class.java]
        posts = Posts()
        viewModel.userDataItem = sessionController.userDetails

        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setDetailsLabel("Fetching Posts...")
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)

        PostsAdapter(posts) { postItem ->
            val b: Bundle = Bundle().apply {
                putInt("PostId", postItem.id)
            }
            findNavController().navigate(R.id.action_PostsFragment_to_commentsFragment, b)
        }.also { adapter = it }

        initRecycleView()
        viewModel.posts.observe(viewLifecycleOwner) { postsData ->
            adapter.setPosts(postsData)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.status.collectLatest { networkStatus: NetworkStatus ->
                when(networkStatus) {
                    NetworkStatus.RESPONSE_RECEIVED -> {
                        withContext(Main) {
                            if(hud.isShowing) {
                                hud.dismiss()
                            }
                        }
                    }

                    NetworkStatus.REQUEST_SENT -> {
                        withContext(Main) {
                            hud.show()
                        }
                    }

                    NetworkStatus.ERROR_OCCURRED -> {
                        withContext(Main) {
                            if(hud.isShowing) {
                                hud.dismiss()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initRecycleView() {
        binding.recycleViewPostItems.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.recycleViewPostItems.itemAnimator = DefaultItemAnimator()
        binding.recycleViewPostItems.adapter = adapter
    }

    private fun inject() {
        DaggerPostAndCommentComponent.builder().apply {
            commonServicesProvider(CommonServicesProvider(requireContext().applicationContext))
            postAndCommentModule(PostAndCommentModule())
        }
            .build()
            .inject(this)
    }
}