package mx.com.test.android.pokemon.util.initializers

import android.content.Context
import androidx.startup.Initializer
import mx.com.test.android.pokemon.util.CrashlyticsTree
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        Timber.plant(Timber.DebugTree())
        Timber.plant(CrashlyticsTree())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}