package me.dungngminh.verygoodblogapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import me.dungngminh.verygoodblogapp.features.authentication.login.LoginContract
import me.dungngminh.verygoodblogapp.features.authentication.login.LoginInteractor
import me.dungngminh.verygoodblogapp.features.authentication.register.RegisterContract
import me.dungngminh.verygoodblogapp.features.authentication.register.RegisterInteractor
import me.dungngminh.verygoodblogapp.repositories.AuthenticationRepository
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
internal interface ViewModelModule {
    @Binds
    fun provideLoginContract(loginInteractor: LoginInteractor): LoginContract.Interactor

    @Binds
    fun provideRegisterContract(registerInteractor: RegisterInteractor): RegisterContract.Interactor

    companion object {
    }

}