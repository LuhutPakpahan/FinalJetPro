package robert.pakpahan.jetpro1.data.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.tvshow.DetailGenreTvShowEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.tvshow.DetailTvShowResponseEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.tvshow.DetailTvShowWithGenre
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.tvshow.*
import robert.pakpahan.jetpro1.data.datasource.remote.DetailTvShowResponse
import robert.pakpahan.jetpro1.data.datasource.remote.TvShowResponse
import robert.pakpahan.jetpro1.utils.DataMapper.detailTvShowResponseToEntity
import robert.pakpahan.jetpro1.utils.DataMapper.tvShowResponseToEntity
import robert.pakpahan.jetpro1.utils.DataMapper.tvShowToEntity

@Dao
abstract class TvShowDao : BaseDao<TvShowResponseEntity, TvShowEntity,
        TvShowResponseWithGenre, TvShowWithGenre,
        GenreTvShowEntity, DetailTvShowResponseEntity, DetailGenreTvShowEntity>() {

    companion object {
        private fun query(q: String, isFavorite: Boolean = false) =
            "SELECT * FROM TvShowEntity ${if (isFavorite) "WHERE isFavorite = 1" else ""} ORDER BY $q ASC"

        val SORT_BY_NAME = SimpleSQLiteQuery(query("original_name"))
        val SORT_BY_RELEASE = SimpleSQLiteQuery(query("first_air_date"))
        val SORT_FAV_BY_NAME = SimpleSQLiteQuery(query("original_name", true))
        val SORT_FAV_BY_RELEASE = SimpleSQLiteQuery(query("first_air_date", true))
    }

    @Transaction
    @Query("SELECT * FROM TvShowResponseEntity")
    abstract fun liveTvShow(): LiveData<TvShowResponseWithGenre>

    @Transaction
    @Query("SELECT * FROM TvShowEntity")
    abstract fun pageTvShow(): DataSource.Factory<Int, TvShowWithGenre>

    @Transaction
    @RawQuery(observedEntities = [TvShowWithGenre::class])
    abstract fun pageTvShow(query: SupportSQLiteQuery): DataSource.Factory<Int, TvShowWithGenre>?

    @Query("SELECT * FROM TvShowEntity WHERE id_tv_show=:id")
    abstract fun tvShow(id: Int): TvShowEntity

    @Transaction
    @Query("SELECT * FROM DetailTvShowResponseEntity WHERE id_detail_tv_show_response=:id")
    abstract fun liveDetailTvShow(id: Int): LiveData<DetailTvShowWithGenre>

    @Query("SELECT * FROM DetailTvShowResponseEntity WHERE id_detail_tv_show_response=:id")
    abstract fun detailTvShow(id: Int): DetailTvShowResponseEntity

    @Transaction
    @RawQuery(observedEntities = [TvShowWithGenre::class])
    abstract fun pageTvShowFavorite(query: SupportSQLiteQuery): DataSource.Factory<Int, TvShowWithGenre>?

    fun insertTvShow(response: TvShowResponse) {
        val fk = insertResponse(tvShowResponseToEntity(response))
        for (item in response.results) {
            val idInsertTvResult = insertResult(tvShowToEntity(fk, item))
            item.genre_ids.forEach {
                insertGenre(GenreTvShowEntity(fk = idInsertTvResult, genre = it))
            }
        }
    }

    fun insertDetailTvShow(response: DetailTvShowResponse) {
        val fk = insertDetailResponse(detailTvShowResponseToEntity(response))
        response.genres.forEach {
            insertDetailGenre(
                DetailGenreTvShowEntity(
                    genre_code = it.genre_code, fk = fk, name = it.name
                )
            )
        }
    }
}