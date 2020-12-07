package bado.ignacio.events.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bado.ignacio.events.databinding.EventItemBinding
import bado.ignacio.events.domain.Event
import bado.ignacio.events.pretty

class EventAdapter(
    private var events: List<Event> = emptyList(),
    private val onItemClick: (Event) -> Unit,
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EventViewHolder(
        EventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ).apply {
        itemView.setOnClickListener {
            val event = events[adapterPosition]
            onItemClick(event)
        }
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount() = events.size

    fun updateEvents(items: List<Event>) {
        // ToDo: implement DiffCallback
        events = items
        notifyDataSetChanged()
    }

    class EventViewHolder(
        private val binding: EventItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.tvTitle.text = event.name
            binding.tvStartDate.text = event.startDate.pretty()
        }
    }
}