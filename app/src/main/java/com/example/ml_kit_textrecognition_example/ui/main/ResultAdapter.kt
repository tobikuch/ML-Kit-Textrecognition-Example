package com.example.ml_kit_textrecognition_example.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ml_kit_textrecognition_example.R
import kotlinx.android.synthetic.main.result_view_holder.view.*

class ResultAdapter(var blocks: List<String>) : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(position: Int, blockText: String){
            itemView.text_block.text = blockText
            val positionPlusOne = position + 1
            val textPosition = "$positionPlusOne."
            itemView.number_block.text = textPosition
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.result_view_holder, parent, false)
        return ResultViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int)
        = holder.bind(position, blocks[position])

    override fun getItemCount(): Int =
        blocks.size
}