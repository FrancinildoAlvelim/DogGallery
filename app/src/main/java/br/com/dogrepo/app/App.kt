package br.com.dogrepo.app

import android.app.Application
import br.com.dogrepo.ui.main.viewmodels.mainViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    init {
        startKoin {
            androidLogger()

            androidContext(this@App)

            androidFileProperties()

            modules(mainViewModelModule)
        }

    }
}