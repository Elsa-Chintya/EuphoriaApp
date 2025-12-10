package com.euphoria.selfcare.euphoria

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.euphoria.selfcare.euphoria.databinding.ItemJournalBinding

class JournalAdapter(
    private val list: List<Journal>,
    private val onClick: (Journal) -> Unit
) : RecyclerView.Adapter<JournalAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemJournalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClick(list[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemJournalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val journal = list[position]
        holder.binding.tvDate.text = journal.date
        holder.binding.tvAffirmation.text = journal.affirmation
        holder.binding.tvReflection.text = journal.reflection
    }

    override fun getItemCount() = list.size
}
