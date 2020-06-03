package com.ergo.notch.sbquiz.ui.view.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ergo.notch.sbquiz.R
import com.ergo.notch.sbquiz.ui.model.StationEntity
import com.ergo.notch.sbquiz.utils.inflate
import kotlinx.android.synthetic.main.station_item_layout.view.*

class ListFragmentAdapter(private val stationList: List<StationEntity>) :
    RecyclerView.Adapter<ListFragmentAdapter.Viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder =
        Viewholder(parent.inflate(R.layout.station_item_layout))

    override fun getItemCount(): Int = this.stationList.size

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.updateItem(this.stationList[position])
    }

    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun updateItem(stationEntity: StationEntity) {
            itemView.tvBikes.text = stationEntity.bikes
            itemView.tvSlots.text = stationEntity.slots
            itemView.tvStationName.text = stationEntity.name
            itemView.tvStationAddress.text =
                "${stationEntity.address} ${stationEntity.addressNumber}"
            itemView.tvDistance.text = "${stationEntity.distance} Km"
        }

    }


}
