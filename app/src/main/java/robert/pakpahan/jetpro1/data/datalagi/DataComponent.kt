package robert.pakpahan.jetpro1.data.datalagi

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import robert.pakpahan.jetpro1.data.datarepository.FilmRepository
import robert.pakpahan.jetpro1.data.datarepository.TvShowRepository
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        DataModule :: class
    ]
)


interface DataComponent {
    @Component.Factory
    interface Factory {
        fun create (@BindsInstance application: Application) : DataComponent
    }
    fun provideFilmRepo() : FilmRepository
    fun provideTvShowRepo() : TvShowRepository

}