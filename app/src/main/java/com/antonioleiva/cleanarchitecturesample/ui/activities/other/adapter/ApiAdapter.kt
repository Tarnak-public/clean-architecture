package com.antonioleiva.cleanarchitecturesample.ui.activities.other.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antonioleiva.cleanarchitecturesample.databinding.ItemLayoutBinding
import com.antonioleiva.data.model.User
import com.bumptech.glide.Glide

class ApiAdapter(
    private val users: ArrayList<User>
) : RecyclerView.Adapter<ApiAdapter.DataViewHolder>() {
    class DataViewHolder(private var binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                textViewUserName.text = user.name
                textViewUserEmail.text = user.email
                Glide.with(imageViewAvatar.context)
                    .load(user.avatar)
                    .into(imageViewAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position])

    fun addData(list: List<User>) {
        users.addAll(list)
    }
}