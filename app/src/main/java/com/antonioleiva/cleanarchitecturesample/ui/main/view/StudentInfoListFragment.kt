package com.mindorks.framework.mvvm.presentation.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.FragmentStudentListBinding
import com.mindorks.framework.mvvm.presentation.ui.main.adapter.StudentAdapter
import com.mindorks.framework.mvvm.presentation.ui.main.viewmodel.StudentViewModel
import com.mindorks.framework.mvvm.utils.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import io.supercharge.fragmentfactoryandhilt.base.BaseFragment
import io.supercharge.fragmentfactoryandhilt.navigator.Navigator
import javax.inject.Inject

@AndroidEntryPoint
class StudentInfoListFragment @Inject constructor(
    val navigator: Navigator
) : BaseFragment(), LifecycleOwner {
    private lateinit var binding: FragmentStudentListBinding
    override val layoutId: Int = R.layout.fragment_student_list
    var mContainerId: Int = -1
    private var studentAdapter: StudentAdapter? = null
    private val mainViewModel: StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentStudentListBinding.inflate(layoutInflater, container, false)
        mContainerId = container?.id ?: -1
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.lifecycle.addObserver(mainViewModel)
        binding.addStudentFloatingBtn.setOnClickListener {
            launchAddStudentFragment()
        }
        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        fetchDataFromViewModel()
    }

    private fun launchAddStudentFragment() {
        activity?.replaceFragment(StudentInfoFragment(navigator), mContainerId)
    }

    private fun initAdapter() {
        studentAdapter = StudentAdapter(arrayListOf())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = studentAdapter
        }

    }

    private fun fetchDataFromViewModel() {
        // viewModel.fetchRoomData()
        mainViewModel.userFinalList.observe(this,
            { t ->
                println("Received UserInfo List $t")
                studentAdapter?.refreshAdapter(t)
            }
        )
    }
}