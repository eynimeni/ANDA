package at.fh.anda_contacts.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import at.fh.anda_contacts.Contact
import at.fh.anda_contacts.data.repository

class DetailViewModel(private val savedStateHandle: SavedStateHandle) :ViewModel() {

    private val contact = repository.read(
        savedStateHandle["contact"]!!
    )

    fun read(): Contact {
        return contact
        //das ist der get call von savedStateHandle
    }

    override fun onCleared() {
        super.onCleared()
        //immer zuerst die Super-Klasse aufrufen, aus der Basisklasse
        Log.e("DetailViewModel","onCleared")
    }

    fun onNameChanged(newName: String) {
        contact.name = newName
        //für Auto-Save: this.save() rufen, dann müsste man aber nicht mehr den SAVE Button haben
    }

    fun save() {
        repository.save(contact)
    }

    fun delete() {
        repository.delete(contact)
    }

}