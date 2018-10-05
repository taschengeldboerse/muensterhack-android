package de.muensterhack.start

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val startModule = module {

    viewModel { StartViewModel() }
}
