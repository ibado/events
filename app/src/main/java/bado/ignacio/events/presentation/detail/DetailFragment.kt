package bado.ignacio.events.presentation.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import bado.ignacio.events.R
import bado.ignacio.events.databinding.FragmentDetailBinding
import bado.ignacio.events.presentation.main.MainViewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailBinding.bind(view)
        with(binding) {
            val event = viewModel.selectedEvent
            tvName.text = event.name
            tvDescription.text = event.description ?: "No description"
            tvStartDate.text = event.startDate.toString()
            tvEndDate.text = event.endDate.toString()
            tvIsFree.text = if (event.isFree) "Free event" else "Non free (${event.currency})"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            parentFragmentManager.popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}