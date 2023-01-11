package at.fh.anda_contacts.data

import androidx.lifecycle.LiveData
import at.fh.anda_contacts.ApiContact
import at.fh.anda_contacts.Contact
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

lateinit var repository : ContactRepository

class ContactRepository(private val andaDatabase: ANDADatabase, private val httpClient: HttpClient) {

    private val contactDao = andaDatabase.getContactDao()

    fun save(contact: Contact) {
        contactDao.updateContact(contact)
    }

    fun delete(contact: Contact) {
        contactDao.deleteContact(contact)
    }

    fun read(contactId: Int): Contact {
        return contactDao.getContact(contactId)
    }

    fun readAll(contactName: String): LiveData<List<Contact>> {
        return contactDao.getContacts(contactName)
    }

    suspend fun load(): List<Contact> {

        val apiContacts: List<ApiContact> =
            httpClient.get("https://my-json-server.typicode.com/GithubGenericUsername/find-your-pet/contacts")
                .body()
        val contacts =
            apiContacts.map { Contact(it.id, it.name, it.telephoneNumber.toString(), it.age) }
        return contacts
    }


}