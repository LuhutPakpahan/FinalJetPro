package robert.pakpahan.jetpro1.app

import dagger.Component
import robert.pakpahan.jetpro1.MainActivity
import robert.pakpahan.jetpro1.data.datalagi.DataComponent
import robert.pakpahan.jetpro1.ui.*
import robert.pakpahan.jetpro1.viewmodel.ViewModelModule


@AppScope
@Component(
    dependencies = [DataComponent::class],
    modules = [
        ViewModelModule::class,
    ]
)

interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(dataComponent: DataComponent): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: DetailActivity)
    fun inject(fragment: FilmFragment)
    fun inject(fragment: TvShowFragment)
    fun inject(fragment: FavoriteFragment)
    fun inject(fragment: FavoriteFilmFragment)
    fun inject(fragment: FavoriteTvShowFragment)
    fun inject(detailFilmFragment: DetailFilmFragment)
    fun inject(detailTvShowFragment: DetailTvShowFragment)
}