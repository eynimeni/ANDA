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
import at.fh.anda_contacts.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
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
            // Hier kann der Refresh prozess angestoßen werden. Der Refresher kann mit isRefreshing = false wieder versteckt werden
            // viewModel.load() das war vor ktor die funktion!

            //diese funktion hab ich jetzt auch beim constructor vom repository in der main

            val httpClient = createHttpClient()
            lifecycleScope.launch(){
                val apiContacts: List<ApiContact> = httpClient.get("https://my-json-server.typicode.com/GithubGenericUsername/find-your-pet/contacts").body()
                val contacts = apiContacts.map { Contact(it.id, it.name, it.telephoneNumber.toString(), it.age) }
                adapter.updateContacts(ArrayList(contacts))


                httpClient.post("https://my-json-server.typicode.com/GithubGenericUsername/find-your-pet/contacts") {
                    contentType(ContentType.Application.Json)
                    setBody(ApiContact(66,"Neuer Name", 123456, 34))
                }

                refresher.isRefreshing = false
            }


        }
    }
}