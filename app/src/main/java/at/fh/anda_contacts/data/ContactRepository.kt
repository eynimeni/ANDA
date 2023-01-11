package at.fh.anda_contacts.data

import androidx.lifecycle.LiveData
import at.fh.anda_contacts.ApiContact
import at.fh.anda_contacts.Contact
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

//globale Variable, die Klasse ist in der Variable und mit repository.readAll kann ich überall drauf zugreifen
//Vorsicht, man könnte die Variable überschreiben
//Vorsicht, man könnte zeitversetzt irgendwo readAll und danach irgendwo load und dann readAll ist nicht aktuellste
lateinit var repository : ContactRepository

class ContactRepository(private val andaDatabase: ANDADatabase, private val httpClient: HttpClient) {
    //private var contacts = MutableLiveData(createContacts(50))
    //wenn Klasse im Konstruktor aufgerufen wird, werden alle Variablen durchgegangen und wenn Funktionen dahinter sind, diese ausgeführt
    //Variablen müssen initialisiert werden, können nicht Null sein
    // wenn man das vermeiden will, muss man "late init" verwenden
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
    //hier könnte man auch leicht auf eine DB zugreifen und müsste an den anderen Klassen nichts ändern


    suspend fun load(): List<Contact> {

        val apiContacts: List<ApiContact> =
            httpClient.get("https://my-json-server.typicode.com/GithubGenericUsername/find-your-pet/contacts")
                .body()
        val contacts =
            apiContacts.map { Contact(it.id, it.name, it.telephoneNumber.toString(), it.age) }
        //contactDao.insertContacts(contacts)
        return contacts;
    }


}