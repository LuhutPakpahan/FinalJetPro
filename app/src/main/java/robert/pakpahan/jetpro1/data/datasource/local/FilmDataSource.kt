package robert.pakpahan.jetpro1.data.datasource.local

import androidx.sqlite.db.SupportSQLiteQuery
import robert.pakpahan.jetpro1.data.datasource.local.dao.FilmDao
import robert.pakpahan.jetpro1.data.datasource.local.dao.FilmDao.Companion.SORT_BY_NAME
import robert.pakpahan.jetpro1.data.datasource.local.dao.FilmDao.Companion.SORT_FAV_BY_NAME
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.film.DetailFilmWithGenre
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.film.FilmResponseWithGenre
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.film.FilmWithGenre
import robert.pakpahan.jetpro1.data.datasource.remote.DetailFilmResponse
import robert.pakpahan.jetpro1.data.datasource.remote.FilmResponse

class FilmDataSource private constructor(private val filmDao: FilmDao) :
    IMainDataSource<FilmResponse, DetailFilmResponse, FilmResponseWithGenre, FilmWithGenre, DetailFilmWithGenre> {

    companion object {
        @Volatile
        private var instance: FilmDataSource? = null

        fun getInstance(filmDao: FilmDao): FilmDataSource =
            instance ?: synchronized(this) { instance ?: FilmDataSource(filmDao) }
    }

    override fun getResponse() = filmDao.liveFilm()

    override fun getResult() = filmDao.pageFilm()

    override fun getResultRawQuery(supportSQLiteQuery: SupportSQLiteQuery?) =
        filmDao.pageFilm(supportSQLiteQuery ?: run { SORT_BY_NAME })

    override fun getDetail(id: Int) = filmDao.liveDetailFilm(id)

    override fun insertResponse(data: FilmResponse) = filmDao.insertFilm(data)

    override fun insertDetailResponse(data: DetailFilmResponse) = filmDao.insertFilmDetail(data)

    override fun updateFavorite(id: Int, isFavorite: Boolean) {
        filmDao.updateDetailFavorite(filmDao.detailFilm(id).apply { this.isFavorite = isFavorite })
        filmDao.updateResultFavorite(filmDao.film(id).apply { this.isFavorite = isFavorite })
    }

    override fun getFavorite(supportSQLiteQuery: SupportSQLiteQuery?) =
        filmDao.pageFilmFavorite(supportSQLiteQuery ?: run { SORT_FAV_BY_NAME })
}