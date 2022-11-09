package com.example.mysocialmedia.fragments.todos

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
import com.example.mysocialmedia.adapters.TodosAdapter
import com.example.mysocialmedia.data.datasource.TodoDataSource
import com.example.mysocialmedia.databinding.FragmentTodosBinding
import com.example.mysocialmedia.di.components.DaggerTodosComponents
import com.example.mysocialmedia.di.modules.CommonServicesProvider
import com.example.mysocialmedia.di.modules.TodosModule
import com.example.mysocialmedia.model.Todos
import com.example.mysocialmedia.utils.NetworkStatus
import com.example.mysocialmedia.utils.hideOptionMenu
import com.example.mysocialmedia.viewmodelfactories.TodosViewModelFactory
import io.github.rupinderjeet.kprogresshud.KProgressHUD
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodosFragment : Fragment() {

    private lateinit var viewModel: TodosViewModel
    private lateinit var binding: FragmentTodosBinding

    @Inject
    lateinit var dataSource: TodoDataSource

    private lateinit var factory: TodosViewModelFactory
    private lateinit var hud: KProgressHUD
    private lateinit var adapter: TodosAdapter
    private lateinit var todos: Todos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_todos, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
        hideOptionMenu(requireContext())
        hud = KProgressHUD.create(requireContext())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setDetailsLabel("Fetching Photos...")
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)

        factory = TodosViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, factory) [TodosViewModel::class.java]
        binding.todosViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        todos = Todos()
        TodosAdapter(todos).also {
            adapter = it
        }

        viewModel.todos.observe(viewLifecycleOwner) {
            adapter.setTodos(it)
        }

        lifecycleScope.launch {
            viewModel.status.collectLatest {
                when(it) {
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
        binding.recycleViewTodos.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.recycleViewTodos.itemAnimator = DefaultItemAnimator()
        binding.recycleViewTodos.adapter = adapter
    }

    private fun inject() {

        DaggerTodosComponents.builder()
            .apply {
                commonServicesProvider(CommonServicesProvider(requireContext().applicationContext))
                todosModule(TodosModule())
            }
            .build()
            .inject(this)
    }

}