package com.mindorks.framework.mvvm.presentation.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.FragmentEmptyBinding
import com.mindorks.framework.mvvm.presentation.ui.main.viewmodel.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.supercharge.fragmentfactoryandhilt.base.BaseFragment
import io.supercharge.fragmentfactoryandhilt.navigator.Navigator
import javax.inject.Inject

@AndroidEntryPoint
class EmptyFragment @Inject constructor(
    val navigator: Navigator
) : BaseFragment() {
    private lateinit var binding: FragmentEmptyBinding
    private val viewModel: EmptyViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_empty

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmptyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//    }

}