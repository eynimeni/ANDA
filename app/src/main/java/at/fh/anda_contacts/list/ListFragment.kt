package at.fh.anda_contacts.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import at.fh.anda_contacts.Contact
import at.fh.anda_contacts.LoggingObserver
import at.fh.anda_contacts.R
import kotlinx.coroutines.launch

class ListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var refresher: SwipeRefreshLayout
    private lateinit var searchView: SearchView
    private val adapter = ContactsAdapter(ArrayList())

    private val viewModel: ListViewModel by viewModels()
    //mit by ist es per se eine lateinit, wird erst aufgerufen wenn viewModel. aufgerufen wird

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvContacts)
        refresher = view.findViewById(R.id.swRefresher)
        searchView = view.findViewById(R.id.svContactName)
        lifecycle.addObserver(LoggingObserver())
        //Observer kann man wieder verwenden - zB permission observer...
        //dadurch lifecycle-aware events feuern
        setupList()
        setupSearch()
        viewModel.readAll().observe(viewLifecycleOwner) {currentContacts ->
            adapter.updateContacts(ArrayList(currentContacts))
        }
    }

    private fun setupSearch() {
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.onSearchTermEntered(newText)
                return true
            }
        })
    }

    private fun setupList() {
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            linearLayoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        refresher.setOnRefreshListener {

            lifecycleScope.launch(){
                val contacts = viewModel.load()
                adapter.updateContacts(contacts as ArrayList<Contact>)
            }
            refresher.isRefreshing = false
        }
    }
}
