package com.antonioleiva.cleanarchitecturesample.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.activityViewModels
import com.antonioleiva.cleanarchitecturesample.R
import com.antonioleiva.cleanarchitecturesample.databinding.FragmentStudentInfoBinding
import com.antonioleiva.cleanarchitecturesample.ui.base.BaseFragment
import com.antonioleiva.cleanarchitecturesample.ui.main.navigator.Navigator
import com.antonioleiva.cleanarchitecturesample.ui.main.viewmodel.StudentViewModel
import com.antonioleiva.data.db.entity.Student
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StudentInfoFragment @Inject constructor(
    val navigator: Navigator
) : BaseFragment() {
    private val tagDebug: String = "fetchData()"
    private lateinit var binding: FragmentStudentInfoBinding

    //    private val studentViewModel: StudentViewModel by viewModels()
    private val studentViewModel by activityViewModels<StudentViewModel>()
    override val layoutId: Int = R.layout.fragment_student_info

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {
            val student = getEnteredStudentDetails()
            studentViewModel.insertStudentInfo(student)
        }
        binding.buttonCancel.setOnClickListener {
            activity?.let {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
        observeViewModel()
    }


    private fun getEnteredStudentDetails(): Student {
        var age: Int = 0
        if (binding.edAge.text.toString().isNotEmpty() && binding.edAge.text.toString()
                .isDigitsOnly()
        ) {
            age = Integer.parseInt(binding.edAge.text.toString())
        }

        return Student(
            0L, binding.edStudentName.text.toString(),
            binding.edLastName.text.toString(),
            binding.edStandard.text.toString(), age
        )
    }


    private fun observeViewModel() {
        studentViewModel.fetchError().observe(viewLifecycleOwner,
            { t -> Toast.makeText(activity, t, Toast.LENGTH_LONG).show() })

        studentViewModel.fetchInsertedId().observe(viewLifecycleOwner,
            { t ->
                if (t != -1L) {
                    Toast.makeText(activity, "Inserted Successfully in DB $t", Toast.LENGTH_LONG)
                        .show()
                    activity?.let {
                        activity?.supportFragmentManager?.popBackStack()
                    }
                } else {
                    Toast.makeText(activity, "Insert Failed", Toast.LENGTH_LONG).show()
                }
            })
    }
}