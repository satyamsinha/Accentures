package test.accenture

import android.app.Application

/**
 * @author Satyam
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        AppPreference.init(this)
    }


}