package robert.pakpahan.jetpro1.data.datarepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.sqlite.db.SupportSQLiteQuery
import robert.pakpahan.jetpro1.data.NetworkBoundResource
import robert.pakpahan.jetpro1.data.datalayout.detail.DetailFilm
import robert.pakpahan.jetpro1.data.datalayout.detail.Film
import robert.pakpahan.jetpro1.data.datasource.local.FilmDataSource
import robert.pakpahan.jetpro1.data.datasource.remote.ApiResponse
import robert.pakpahan.jetpro1.data.datasource.remote.DetailFilmResponse
import robert.pakpahan.jetpro1.data.datasource.remote.FilmResponse
import robert.pakpahan.jetpro1.data.datasource.remote.RemoteDataSource
import robert.pakpahan.jetpro1.utils.DataMapper
import robert.pakpahan.jetpro1.utils.Executors
import robert.pakpahan.jetpro1.vo.Resource

class FilmRepositoryFake(
    private val executors: Executors,
    private val localData: FilmDataSource,
    private val remoteData: RemoteDataSource
) : IMainRepository<Film, DetailFilm> {

    override fun getResult(supportSQLiteQuery: SupportSQLiteQuery?): LiveData<Resource<PagedList<Film>>> {
        return object :
            NetworkBoundResource<PagedList<Film>, FilmResponse>(executors) {
            override fun loadFromDB(): LiveData<PagedList<Film>> {
                val result = localData.getResultRawQuery(supportSQLiteQuery)
                val convert = result?.mapByPage { DataMapper.listFilmWithGenreToFilms(it) }
                return convert?.let {
                    LivePagedListBuilder(it, Utils.config()).build()
                } ?: MutableLiveData()
            }
            override fun shouldFetch(data: PagedList<Film>?) = data == null || data.isEmpty()
            override fun createCall(): LiveData<ApiResponse<FilmResponse>> = remoteData.getFilms()
            override fun saveCallResult(data: FilmResponse) {
                localData.insertResponse(data)
            }
        }.asLiveData()
    }

    override fun getDetail(id: Int): LiveData<Resource<DetailFilm>> {
        return object : NetworkBoundResource<DetailFilm, DetailFilmResponse>(executors) {
            override fun loadFromDB(): LiveData<DetailFilm> =
                Transformations.map(localData.getDetail(id)) {
                    DataMapper.detailFilmWithGenreToDetailFilm(
                        it
                    )
                }
            override fun shouldFetch(data: DetailFilm?): Boolean = (data == null)
            override fun createCall(): LiveData<ApiResponse<DetailFilmResponse>> = remoteData.getFilmById(id)
            override fun saveCallResult(data: DetailFilmResponse) = localData.insertDetailResponse(data)
        }.asLiveData()
    }

    override fun getFavorite(supportSQLiteQuery: SupportSQLiteQuery?) =
        object : NetworkBoundResource<PagedList<Film>, FilmResponse>(executors) {
            override fun loadFromDB(): LiveData<PagedList<Film>> {
                val result = localData.getFavorite(supportSQLiteQuery)
                val convert = result?.mapByPage { DataMapper.listFilmWithGenreToFilms(it) }
                return convert?.let {
                    LivePagedListBuilder(it, Utils.config()).build()
                } ?: MutableLiveData()
            }
            override fun shouldFetch(data: PagedList<Film>?) = false
            override fun createCall(): LiveData<ApiResponse<FilmResponse>> = remoteData.getFilms()
            override fun saveCallResult(data: FilmResponse) {}
        }.asLiveData()

    override fun setFavorite(id: Int, isFavorite: Boolean) {
        executors.diskIO().execute { localData.updateFavorite(id, isFavorite) }
    }
}