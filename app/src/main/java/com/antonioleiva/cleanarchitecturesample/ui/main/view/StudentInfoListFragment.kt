package com.antonioleiva.cleanarchitecturesample.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.antonioleiva.cleanarchitecturesample.R
import com.antonioleiva.cleanarchitecturesample.databinding.FragmentStudentListBinding
import com.antonioleiva.cleanarchitecturesample.ui.base.BaseFragment
import com.antonioleiva.cleanarchitecturesample.ui.main.adapter.StudentAdapter
import com.antonioleiva.cleanarchitecturesample.ui.main.navigator.Navigator
import com.antonioleiva.cleanarchitecturesample.ui.main.viewmodel.StudentViewModel
import com.antonioleiva.cleanarchitecturesample.utils.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StudentInfoListFragment @Inject constructor(
    private val navigator: Navigator
) : BaseFragment(), LifecycleOwner {
    private lateinit var binding: FragmentStudentListBinding
    override val layoutId: Int = R.layout.fragment_student_list
    var mContainerId: Int = -1
    private var studentAdapter: StudentAdapter? = null
    private val studentViewModel: StudentViewModel by viewModels()

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
//        this.lifecycle.addObserver(studentViewModel)
        fetchDataFromViewModel()
        binding.addStudentFloatingBtn.setOnClickListener {
            launchAddStudentFragment()
        }
        initAdapter()
    }

    override fun onResume() {
        super.onResume()

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
        studentViewModel.userFinalList.observe(viewLifecycleOwner
//        studentViewModel.fetchInsertedId().observe( this
        ) { t ->
            println("fetchData() Received UserInfo List $t")
            studentAdapter?.refreshAdapter(studentViewModel.studentsList)
        }
    }
}