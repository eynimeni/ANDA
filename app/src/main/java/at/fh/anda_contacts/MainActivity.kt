package at.fh.anda_contacts

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import at.fh.anda_contacts.data.ANDADatabase
import at.fh.anda_contacts.data.ContactRepository
import at.fh.anda_contacts.data.repository

class MainActivity : AppCompatActivity() {

    //wird bei App Start gerufen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mehrere Möglichkeiten Log zu schreiben (e error, i info, w warning, v verbose, d debug...)
        //häufig nimmt man i
        //statt "MainActiviy" hardcoded geht auch MainActivity::class.simpleName
        Log.e("MainActivity", "onCreate()")

        val database = Room.databaseBuilder(
            this, ANDADatabase::class.java, "ANDA"
        ).allowMainThreadQueries()
            .build()
        //mit diesem database-builder können wir so viele datenbanken haben wie wir wollen

        val httpClient = createHttpClient()

        repository = ContactRepository(database, httpClient)
    }

    //bevor die View sichtbar ist
    override fun onStart() {
        super.onStart()
        Log.e("MainActivity", "onStart()")
    }

    //nachdem die App sichtbar wurde
    override fun onResume() {
        super.onResume()
        Log.e("MainActivity", "onResume()")
    }

    //Bevor die View verschwunden ist
    override fun onStop() {
        super.onStop()
        Log.e("MainActivity", "onStop()")
    }

    //wenn die View zerstört wird
    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity", "onDestroy()")
    }
}