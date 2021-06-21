package robert.pakpahan.jetpro1.data.datasource.local

import robert.pakpahan.jetpro1.data.datasource.local.dao.FilmDao
import robert.pakpahan.jetpro1.data.datasource.local.dao.TvShowDao
import robert.pakpahan.jetpro1.data.datasource.local.entity.TvShowDataSource

class LocalDataSource private constructor(filmDao: FilmDao, tvShowDao: TvShowDao) {

    val film = FilmDataSource.getInstance(filmDao)
    val tvShow = TvShowDataSource.getInstance(tvShowDao)

    companion object {
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(filmDao: FilmDao, tvShowDao: TvShowDao): LocalDataSource =
            instance ?: synchronized(this) { instance ?: LocalDataSource(filmDao, tvShowDao) }
    }
}