package bado.ignacio.events.presentation.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import bado.ignacio.events.R
import bado.ignacio.events.databinding.FragmentDetailBinding
import bado.ignacio.events.presentation.displayHomeAsUp
import bado.ignacio.events.presentation.main.MainViewModel
import bado.ignacio.events.presentation.pretty

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        displayHomeAsUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailBinding.bind(view)
        with(binding) {
            val event = viewModel.selectedEvent
            tvName.text = event.name
            tvDescription.text = event.description ?: getString(R.string.no_description_label)
            tvStartDate.text = getString(R.string.start_date_template, event.startDate.pretty())
            tvEndDate.text = getString(R.string.end_date_template, event.endDate.pretty())
            if (event.isFree) {
                tvIsFree.text = getString(R.string.free_event)
                tvCurrency.text = ""
            } else {
                tvIsFree.text = getString(R.string.non_free_event)
                tvCurrency.text = getString(R.string.currency_template, event.currency)
            }
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