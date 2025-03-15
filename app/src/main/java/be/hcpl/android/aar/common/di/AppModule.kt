package be.hcpl.android.aar.common.di

import be.hcpl.android.aar.mvc.TaskRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    // example of creating viewModels
    //viewModelOf(::MainViewModel)

    factoryOf(::TaskRepository)

}