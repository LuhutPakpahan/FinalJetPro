package robert.pakpahan.jetpro1.data.datasource.local.entity

import androidx.sqlite.db.SupportSQLiteQuery
import robert.pakpahan.jetpro1.data.datasource.local.IMainDataSource
import robert.pakpahan.jetpro1.data.datasource.local.dao.TvShowDao
import robert.pakpahan.jetpro1.data.datasource.local.dao.TvShowDao.Companion.SORT_BY_NAME
import robert.pakpahan.jetpro1.data.datasource.local.dao.TvShowDao.Companion.SORT_FAV_BY_NAME
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.tvshow.DetailTvShowWithGenre
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.tvshow.TvShowResponseWithGenre
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.tvshow.TvShowWithGenre
import robert.pakpahan.jetpro1.data.datasource.remote.DetailTvShowResponse
import robert.pakpahan.jetpro1.data.datasource.remote.TvShowResponse

class TvShowDataSource private constructor(private val tvShowDao: TvShowDao) :
    IMainDataSource<TvShowResponse, DetailTvShowResponse, TvShowResponseWithGenre, TvShowWithGenre, DetailTvShowWithGenre> {

    companion object {
        @Volatile
        private var instance: TvShowDataSource? = null

        fun getInstance(tvShowDao: TvShowDao): TvShowDataSource =
            instance ?: synchronized(this) { instance ?: TvShowDataSource(tvShowDao) }
    }

    override fun getResponse() = tvShowDao.liveTvShow()

    override fun getResult() = tvShowDao.pageTvShow()

    override fun getResultRawQuery(supportSQLiteQuery: SupportSQLiteQuery?) =
        tvShowDao.pageTvShow(supportSQLiteQuery ?: run { SORT_BY_NAME })

    override fun getDetail(id: Int) = tvShowDao.liveDetailTvShow(id)

    override fun insertResponse(data: TvShowResponse) = tvShowDao.insertTvShow(data)

    override fun insertDetailResponse(data: DetailTvShowResponse) = tvShowDao.insertDetailTvShow(data)

    override fun updateFavorite(id: Int, isFavorite: Boolean) {
        tvShowDao.updateDetailFavorite(tvShowDao.detailTvShow(id).apply { this.isFavorite = isFavorite })
        tvShowDao.updateResultFavorite(tvShowDao.tvShow(id).apply { this.isFavorite = isFavorite })
    }

    override fun getFavorite(supportSQLiteQuery: SupportSQLiteQuery?) =
        tvShowDao.pageTvShowFavorite(supportSQLiteQuery ?: run { SORT_FAV_BY_NAME })
}