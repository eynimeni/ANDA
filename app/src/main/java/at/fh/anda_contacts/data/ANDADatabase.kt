package at.fh.anda_contacts.data

import androidx.lifecycle.LiveData
import androidx.room.*
import at.fh.anda_contacts.Contact

@Database(entities = [Contact::class], version = 1)
//immer wenn man database-changes macht, höhere version einstellen
abstract class ANDADatabase: RoomDatabase() {
    abstract fun getContactDao(): ContactDao
}

@Dao
interface ContactDao {
    @Insert
    fun insertContacts(contacts: List<Contact>)
    //fun insertContacts(vararg contact: Contact) könnte man aufrufen mit insertContacts(Contact1, Contact2)

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("SELECT COUNT(*) FROM ContactsDB")
    fun getContactCount(): Int

    @Query("SELECT * FROM ContactsDB WHERE name LIKE '%' || :contactName || '%'")
    //entspricht einem SQL-"contains"
    fun getContacts(contactName: String): LiveData<List<Contact>>

    @Query("SELECT * FROM ContactsDB WHERE id = :contactId")
    fun getContact(contactId: Int): Contact


    //durch Live-Funktion immer aktuelle Daten anzeigen lassen möglich
    //in App immer eine Quelle an Daten, der man vertrauen kann - eine Quelle auserkoren die passt. DB dafür ein super Platz.


}

