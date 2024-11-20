package ru.android.cars7

import android.app.Application
import ru.dgis.sdk.Context
import ru.dgis.sdk.DGis
import ru.dgis.sdk.platform.LogLevel
import ru.dgis.sdk.platform.LogOptions

class Application : Application() {
    lateinit var sdkContext: Context

    override fun onCreate() {
        super.onCreate()

        sdkContext = DGis.initialize(
            this,
            logOptions = LogOptions(
                customLevel = LogLevel.WARNING,
            )
        )
    }
}

val Application.sdkContext: Context
    get() = (this as ru.android.cars7.Application).sdkContext
