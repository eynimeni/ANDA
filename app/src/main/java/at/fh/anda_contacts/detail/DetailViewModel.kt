package at.fh.anda_contacts.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import at.fh.anda_contacts.data.repository

class DetailViewModel(private val savedStateHandle: SavedStateHandle) :ViewModel() {

    private val contact = repository.read(
        savedStateHandle["contact"]!!
    )

    override fun onCleared() {
        super.onCleared()
        Log.e("DetailViewModel","onCleared")
    }

    fun onNameChanged(newName: String) {
        contact.name = newName
    }

    fun save() {
        repository.save(contact)
    }

    fun delete() {
        repository.delete(contact)
    }

}