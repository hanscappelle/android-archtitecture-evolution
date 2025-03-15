package be.hcpl.android.aar.common.di

import be.hcpl.android.aar.common.TaskRepository
import be.hcpl.android.aar.mvi.MviViewModel
import be.hcpl.android.aar.mvp.PresenterImpl
import be.hcpl.android.aar.mvvm.MvvmViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::MvvmViewModel)
    viewModelOf(::MviViewModel)

    factoryOf(::TaskRepository)
    factoryOf(::PresenterImpl) // TODO should be based on interface really

}