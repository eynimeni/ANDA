package at.fh.anda_contacts

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import at.fh.anda_contacts.data.ANDADatabase
import at.fh.anda_contacts.data.ContactRepository
import at.fh.anda_contacts.data.repository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("MainActivity", "onCreate()")

        val database = Room.databaseBuilder(
            this, ANDADatabase::class.java, "ANDA"
        ).allowMainThreadQueries()
            .build()

        val httpClient = createHttpClient()

        repository = ContactRepository(database, httpClient)
    }

    override fun onStart() {
        super.onStart()
        Log.e("MainActivity", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.e("MainActivity", "onResume()")
    }

    override fun onStop() {
        super.onStop()
        Log.e("MainActivity", "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity", "onDestroy()")
    }
}