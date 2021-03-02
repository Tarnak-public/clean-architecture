package com.antonioleiva.cleanarchitecturesample.ui.activities.other.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.antonioleiva.cleanarchitecturesample.R
import com.antonioleiva.cleanarchitecturesample.databinding.FragmentStudentListBinding
import com.antonioleiva.cleanarchitecturesample.ui.factory.BaseFragment
import com.antonioleiva.cleanarchitecturesample.ui.activities.other.adapter.StudentAdapter
import com.antonioleiva.cleanarchitecturesample.ui.activities.other.navigator.Navigator
import com.antonioleiva.cleanarchitecturesample.ui.activities.other.viewmodel.StudentViewModel
import com.antonioleiva.cleanarchitecturesample.utils.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StudentInfoListFragment @Inject constructor(
    private val navigator: Navigator
) : BaseFragment(), LifecycleOwner {
    private val tagDebug: String = "fetchData()"
    private lateinit var binding: FragmentStudentListBinding
    override val layoutId: Int = R.layout.fragment_student_list
    var mContainerId: Int = -1
    private var studentAdapter: StudentAdapter? = null

    //    private val studentViewModel: StudentViewModel by viewModels() // create new instance of viewmodel
    private val studentViewModel by activityViewModels<StudentViewModel>() //create singleton viewmodel

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
        Log.d(tagDebug, "onViewCreated() StudentInfoListFragment $studentViewModel")
        initAdapter()

        addObservers()

        binding.addStudentFloatingBtn.setOnClickListener {
            launchAddStudentFragment()
        }
    }

    private fun addObservers() {
        studentViewModel.fetchDatabaseChanges().observe(viewLifecycleOwner) { t ->
            studentAdapter?.refreshAdapter(t)
        }

//        studentViewModel.fetchInsertedId().observe(viewLifecycleOwner) { t ->
//            Log.d(tagDebug, "Activity fetchInsertedId() $t")
//            studentViewModel.rereadDatabase()
//        }
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
}