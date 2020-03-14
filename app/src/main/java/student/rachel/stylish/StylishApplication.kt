package student.rachel.stylish

import android.app.Application
import android.content.Context

class StylishApplication : Application() {
    companion object {
        lateinit var appContext: Context
    }
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}