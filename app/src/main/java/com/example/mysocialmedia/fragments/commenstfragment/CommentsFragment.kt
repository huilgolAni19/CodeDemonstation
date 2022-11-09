package com.example.mysocialmedia.fragments.commenstfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysocialmedia.R
import com.example.mysocialmedia.adapters.CommentsAdapter
import com.example.mysocialmedia.data.datasource.PostsAndCommentsDataSource
import com.example.mysocialmedia.databinding.FragmentCommentsBinding
import com.example.mysocialmedia.di.components.DaggerPostAndCommentComponent
import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.di.modules.PostAndCommentModule
import com.example.mysocialmedia.model.Comments
import com.example.mysocialmedia.utils.NetworkStatus
import com.example.mysocialmedia.viewmodelfactories.PostsAndCommentsViewModelFactory
import io.github.rupinderjeet.kprogresshud.KProgressHUD
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommentsFragment : Fragment() {

    private lateinit var viewModel: CommentsViewModel
    private lateinit var binding: FragmentCommentsBinding

    @Inject
    lateinit var dataSource: PostsAndCommentsDataSource

    private lateinit var factory: PostsAndCommentsViewModelFactory

    private lateinit var hud: KProgressHUD
    private lateinit var comments: Comments
    private lateinit var adapter: CommentsAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_comments, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()

        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setDetailsLabel("Fetching Comments...")
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)

        factory = PostsAndCommentsViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, factory) [CommentsViewModel::class.java]

        viewModel.id = requireArguments().getInt("PostId")
        comments = Comments()
        adapter = CommentsAdapter(comments)
        initRecycleView()
        viewModel.comments.observe(viewLifecycleOwner) { commentsData ->
            adapter.setComments(commentsData)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.status.collectLatest { networkStatus: NetworkStatus ->
                when(networkStatus) {

                    NetworkStatus.REQUEST_SENT -> {
                        withContext(Main) {
                            hud.show()
                        }
                    } else -> {
                        withContext(Main) {
                            if (hud.isShowing) {
                                hud.dismiss()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initRecycleView() {
        binding.recycleViewComments.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.recycleViewComments.itemAnimator = DefaultItemAnimator()
        binding.recycleViewComments.adapter = adapter
    }

    fun inject() {
        DaggerPostAndCommentComponent.builder().apply {
            commonServicesProvider(CommonServicesProvider(requireContext().applicationContext))
            postAndCommentModule(PostAndCommentModule())
        }
            .build()
            .inject(this)
    }

}