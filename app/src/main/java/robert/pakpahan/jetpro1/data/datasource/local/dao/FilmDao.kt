package robert.pakpahan.jetpro1.data.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.film.DetailFilmResponseEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.film.DetailFilmWithGenre
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.film.DetailGenreFilmEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.film.*
import robert.pakpahan.jetpro1.data.datasource.remote.DetailFilmResponse
import robert.pakpahan.jetpro1.data.datasource.remote.FilmResponse
import robert.pakpahan.jetpro1.utils.DataMapper.detailFilmResponseToEntity
import robert.pakpahan.jetpro1.utils.DataMapper.filmResponseToEntity
import robert.pakpahan.jetpro1.utils.DataMapper.filmToEntity

@Dao
abstract class FilmDao : BaseDao<FilmResponseEntity, FilmEntity,
        FilmResponseWithGenre, FilmWithGenre,
        GenreFilmEntity, DetailFilmResponseEntity, DetailGenreFilmEntity>() {

    companion object {
        private fun query(q: String, isFavorite: Boolean = false) =
            "SELECT * FROM FilmEntity ${if (isFavorite) "WHERE isFavorite = 1" else ""} ORDER BY $q ASC"

        val SORT_BY_NAME = SimpleSQLiteQuery(query("title"))
        val SORT_BY_RELEASE = SimpleSQLiteQuery(query("release_date"))
        val SORT_FAV_BY_NAME = SimpleSQLiteQuery(query("title", true))
        val SORT_FAV_BY_RELEASE = SimpleSQLiteQuery(query("release_date", true))
    }

    @Transaction
    @Query("SELECT * FROM FilmResponseEntity")
    abstract fun liveFilm(): LiveData<FilmResponseWithGenre>

    @Transaction
    @Query("SELECT * FROM FilmEntity")
    abstract fun pageFilm(): DataSource.Factory<Int, FilmWithGenre>

    @Transaction
    @RawQuery(observedEntities = [FilmWithGenre::class])
    abstract fun pageFilm(query: SupportSQLiteQuery): DataSource.Factory<Int, FilmWithGenre>?

    @Query("SELECT * FROM FilmEntity WHERE id_film=:id")
    abstract fun film(id: Int): FilmEntity

    @Query("SELECT * FROM DetailFilmResponseEntity WHERE id_detail_film_response=:id")
    abstract fun detailFilm(id: Int): DetailFilmResponseEntity

    @Transaction
    @Query("SELECT * FROM DetailFilmResponseEntity WHERE id_detail_film_response=:id")
    abstract fun liveDetailFilm(id: Int): LiveData<DetailFilmWithGenre>

    @Transaction
    @RawQuery(observedEntities = [FilmWithGenre::class])
    abstract fun pageFilmFavorite(query: SupportSQLiteQuery): DataSource.Factory<Int, FilmWithGenre>?

    fun insertFilm(response: FilmResponse) {
        val idInsertResponse = insertResponse(filmResponseToEntity(response))
        for (item in response.results) {
            val idInsertFilmResult = insertResult(filmToEntity(idInsertResponse, item))
            item.genre_ids?.forEach {
                insertGenre(GenreFilmEntity(fk = idInsertFilmResult, genre = it))
            }
        }
    }

    fun insertFilmDetail(response: DetailFilmResponse) {
        val idResult = insertDetailResponse(detailFilmResponseToEntity(response))
        response.genres.forEach {
            insertDetailGenre(
                DetailGenreFilmEntity(
                    genre_code = it.genre_code, fk = idResult, name = it.name
                )
            )
        }
    }
}