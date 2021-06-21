package robert.pakpahan.jetpro1.data.datalagi

import android.app.Application
import dagger.Module
import dagger.Provides
import robert.pakpahan.jetpro1.data.datarepository.FilmRepository
import robert.pakpahan.jetpro1.data.datarepository.MainRepository
import robert.pakpahan.jetpro1.data.datarepository.TvShowRepository
import robert.pakpahan.jetpro1.data.datasource.local.LocalDataSource
import robert.pakpahan.jetpro1.data.datasource.local.room.AppDatabase
import robert.pakpahan.jetpro1.data.datasource.remote.ApiService
import robert.pakpahan.jetpro1.data.datasource.remote.RemoteDataSource
import robert.pakpahan.jetpro1.data.datasource.remote.RetrofitBuilder
import robert.pakpahan.jetpro1.utils.Constants
import robert.pakpahan.jetpro1.utils.Executors
import javax.inject.Singleton


@Module
class DataModule {
    @Provides
    @Singleton
    internal fun provideApiServiceTheFilm() =
        RetrofitBuilder.service(Constants.TMDB_BASE_URL, ApiService::class.java)

    @Provides
    @Singleton
    internal fun provideRemoteDataSource(apiService: ApiService) =
        RemoteDataSource.getInstance(apiService)

    @Provides
    @Singleton
    internal fun provideTheFilmDatabase(application: Application) =
        AppDatabase.getDatabase(application)

    @Provides
    @Singleton
    internal fun provideLocalDataSource(database: AppDatabase) =
        LocalDataSource.getInstance(database.filmDao(), database.tvShowDao())

    @Provides
    @Singleton
    internal fun provideTheFilmRepository(
        remote: RemoteDataSource, local: LocalDataSource
    ) = MainRepository.getInstance(remote, local, Executors())

    @Provides
    @Singleton
    internal fun provideFilmRepository(
        remote: RemoteDataSource, local: LocalDataSource
    ) = FilmRepository.getInstance(Executors(), remote, local.film)

    @Provides
    @Singleton
    internal fun provideTvRepository(
        remote: RemoteDataSource, local: LocalDataSource
    ) = TvShowRepository.getInstance(Executors(), remote, local.tvShow)

}