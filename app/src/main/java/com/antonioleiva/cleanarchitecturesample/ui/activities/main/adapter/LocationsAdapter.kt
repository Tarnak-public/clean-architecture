package com.antonioleiva.cleanarchitecturesample.ui.activities.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antonioleiva.cleanarchitecturesample.databinding.ViewLocationItemBinding
import com.antonioleiva.cleanarchitecturesample.utils.Location

class LocationsAdapter(

) : RecyclerView.Adapter<LocationsAdapter.DataViewHolder>() {
    var items: List<Location> = emptyList()

    class DataViewHolder(private var binding: ViewLocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: Location) {
            binding.locationCoordinates.text = location.coordinates
            binding.locationDate.text = location.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            ViewLocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
