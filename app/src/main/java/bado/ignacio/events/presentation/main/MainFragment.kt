package bado.ignacio.events.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import bado.ignacio.events.R
import bado.ignacio.events.databinding.MainFragmentBinding
import bado.ignacio.events.presentation.State
import bado.ignacio.events.presentation.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
        val TAG = MainFragment::class.simpleName
    }

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = MainFragmentBinding.bind(view)
        val adapter = EventAdapter { event ->
            viewModel.selectedEvent = event
            addDetailFragment()
        }
        binding.rvMain.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            this.adapter = adapter
        }
        viewModel.events.observe(viewLifecycleOwner) {
            binding.loading.visibility = View.GONE
            when (it) {
                is State.Success -> adapter.updateEvents(it.value)
                is State.Error -> showError(it.error)
                is State.Loading -> binding.loading.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        val search = menu.findItem(R.id.search_action).actionView as SearchView
        search.setQuery("", false)
        search.setOnQueryTextListener(EventQueryTextListener {
            viewModel.search(it)
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.order_by_name -> viewModel.orderByName()
            R.id.order_by_date -> viewModel.orderByDate()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun addDetailFragment() {
        parentFragmentManager.commit {
            setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,
                R.anim.slide_out_left,
                R.anim.slide_in_right,
            )
            add(R.id.container, DetailFragment())
            addToBackStack(null)
        }
    }

    private fun showError(error: Throwable) {
        Toast.makeText(context, getString(R.string.event_loading_error), Toast.LENGTH_SHORT).show()
        Log.e(TAG, "Error fetching events. Error: $error")
    }
}