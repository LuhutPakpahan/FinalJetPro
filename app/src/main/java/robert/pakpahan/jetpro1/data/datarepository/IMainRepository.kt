package robert.pakpahan.jetpro1.data.datarepository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.sqlite.db.SupportSQLiteQuery
import robert.pakpahan.jetpro1.data.datalayout.detail.Film
import robert.pakpahan.jetpro1.data.datarepository.Utils.config
import robert.pakpahan.jetpro1.vo.Resource

interface IMainRepository<DataResultModel, DataDetailModel> {
    fun <T> toLiveData(dataSourceFactory: () -> DataSource.Factory<Int, T>): LiveData<PagedList<T>> {
        return LivePagedListBuilder(dataSourceFactory.invoke(), config()).build()
    }
    fun getResult(supportSQLiteQuery: SupportSQLiteQuery? = null): LiveData<Resource<PagedList<DataResultModel>>>
    fun getDetail(id: Int): LiveData<Resource<DataDetailModel>>
    fun getFavorite(supportSQLiteQuery: SupportSQLiteQuery? = null): LiveData<Resource<PagedList<DataResultModel>>>
    fun setFavorite(id: Int, isFavorite: Boolean)
}