package at.fh.anda_contacts.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import at.fh.anda_contacts.data.repository

class ListViewModel : ViewModel() {

    private val searchTerm = MutableLiveData("")

    fun onSearchTermEntered(searchTerm: String) {
        this.searchTerm.value = searchTerm
    }

    fun readAll() = Transformations.switchMap(searchTerm) {
        repository.readAll(it)
    }

    suspend fun load() = repository.load()

    override fun onCleared() {
        super.onCleared()
    }


}