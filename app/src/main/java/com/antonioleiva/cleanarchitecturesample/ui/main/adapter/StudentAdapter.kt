package com.mindorks.framework.mvvm.presentation.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antonioleiva.data.db.entity.Student
import com.mindorks.framework.mvvm.databinding.StudentListItemBinding

class StudentAdapter(
    var postList : ArrayList<Student>
) : RecyclerView.Adapter<StudentAdapter.DataViewHolder>() {
    class DataViewHolder(private var binding: StudentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model : Student) {
            binding.stuName.text = model.fName.toString()
            binding.stuAge.text = model.age.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            StudentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int)  =  holder.bind(postList[position])

    fun refreshAdapter(newPostList : List<Student>){
        postList.clear()
        postList.addAll(newPostList)
        notifyDataSetChanged()

    }
    override fun getItemCount(): Int  = postList.size
}
