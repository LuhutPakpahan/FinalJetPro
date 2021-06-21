package robert.pakpahan.jetpro1

import android.app.Application
import robert.pakpahan.jetpro1.app.AppComponent
import robert.pakpahan.jetpro1.app.DaggerAppComponent
import robert.pakpahan.jetpro1.data.datalagi.DataComponent
import robert.pakpahan.jetpro1.data.datalagi.DaggerDataComponent
import robert.pakpahan.jetpro1.utils.ReleaseTree
import timber.log.Timber

class MyApp() : Application(){
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    private val dataComponent: DataComponent by lazy {
        DaggerDataComponent.factory().create(this)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(dataComponent)
    }
}
