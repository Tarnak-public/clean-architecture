package com.mindorks.framework.mvvm.presentation.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.data.model.User
import com.mindorks.framework.mvvm.databinding.FragmentApiUsageBinding
import com.mindorks.framework.mvvm.presentation.ui.main.adapter.ApiAdapter
import com.mindorks.framework.mvvm.presentation.ui.main.viewmodel.ApiUsageViewModel
import com.mindorks.framework.mvvm.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import io.supercharge.fragmentfactoryandhilt.base.BaseFragment
import io.supercharge.fragmentfactoryandhilt.navigator.Navigator
import javax.inject.Inject

@AndroidEntryPoint
class ApiUsageFragment @Inject constructor(
    val navigator: Navigator
) : BaseFragment() {
    private lateinit var binding: FragmentApiUsageBinding
    private val viewModel: ApiUsageViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_api_usage
    private lateinit var adapter: ApiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApiUsageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(root.context)
            adapter = ApiAdapter(arrayListOf())
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
            recyclerView.adapter = adapter
        }
    }

    private fun setupObserver() {
        viewModel.users.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}