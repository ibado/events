package bado.ignacio.events.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bado.ignacio.events.databinding.EventItemBinding
import bado.ignacio.events.domain.Event
import bado.ignacio.events.presentation.hide
import bado.ignacio.events.presentation.pretty
import bado.ignacio.events.presentation.show

class EventAdapter(
    private var events: MutableList<Event> = mutableListOf(),
    private val onItemClick: (Event) -> Unit,
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val loading = Event.empty().copy(name = LOADING)

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
        if (!events.contains(loading)) {
            events.clear()
        } else {
            events.remove(loading)
        }
        events.addAll(items)
        notifyDataSetChanged()
    }

    fun showLoadingItem() {
        events.add(loading)
        notifyItemInserted(events.size - 1)
    }

    class EventViewHolder(
        private val binding: EventItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            with(binding) {
                if (event.name == LOADING) loading.show() else loading.hide()
                tvTitle.text = event.name
                tvStartDate.text = event.startDate.pretty()
            }
        }
    }

    companion object {
        const val LOADING = "loading_key"
    }
}