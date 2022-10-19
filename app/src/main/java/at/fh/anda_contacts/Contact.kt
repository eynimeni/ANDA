package at.fh.anda_contacts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "ContactsDB")
class Contact(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String,
    @ColumnInfo(name = "telephone_number") var telephoneNumber: String,
    var age: Int
) : Serializable

fun createContacts(contactCount: Int) =
    (1..contactCount)
        .map { index -> Contact(0, "Name $index", "Tel Number", 20 + contactCount) }
