package bado.ignacio.events.presentation.main

import android.os.Handler
import android.os.Looper
import androidx.appcompat.widget.SearchView

class EventQueryTextListener(
    private val searchAction: (String) -> Unit,
) : SearchView.OnQueryTextListener {

    private val uiHandler = Handler(Looper.getMainLooper())

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { searchAction(it) }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        uiHandler.removeCallbacksAndMessages(null)
        newText?.let {
            uiHandler.postDelayed({
                searchAction(newText)
            }, 400)
        }
        return true
    }
}