package at.fh.anda_contacts

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class LoggingObserver: DefaultLifecycleObserver, LifecycleEventObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        // Starte hier GPS Abruf
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        //Beende GPS Abruf wieder
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.e("Logger", event.toString())

    }
}