package at.fh.anda_contacts.data

import androidx.lifecycle.LiveData
import androidx.room.*
import at.fh.anda_contacts.Contact

@Database(entities = [Contact::class], version = 1)

abstract class ANDADatabase: RoomDatabase() {
    abstract fun getContactDao(): ContactDao
}

@Dao
interface ContactDao {
    @Insert
    fun insertContacts(contacts: List<Contact>)

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("SELECT COUNT(*) FROM ContactsDB")
    fun getContactCount(): Int

    @Query("SELECT * FROM ContactsDB WHERE name LIKE '%' || :contactName || '%'")
    fun getContacts(contactName: String): LiveData<List<Contact>>

    @Query("SELECT * FROM ContactsDB WHERE id = :contactId")
    fun getContact(contactId: Int): Contact

}

