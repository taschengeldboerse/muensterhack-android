package de.muensterhack.preferences

import org.koin.dsl.module.module

val preferencesModule = module {

    factory<Preferences> { PreferencesImpl(get()) }
}
