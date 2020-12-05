package bado.ignacio.events.di

import bado.ignacio.events.data.EventRepositoryImpl
import bado.ignacio.events.domain.EventRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindEventRepository(repository: EventRepositoryImpl): EventRepository
}