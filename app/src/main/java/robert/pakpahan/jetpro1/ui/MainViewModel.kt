package robert.pakpahan.jetpro1.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import robert.pakpahan.jetpro1.data.datalayout.detail.Film
import robert.pakpahan.jetpro1.data.datalayout.detail.TvShow
import robert.pakpahan.jetpro1.data.datarepository.FilmRepository
import robert.pakpahan.jetpro1.data.datarepository.TvShowRepository
import robert.pakpahan.jetpro1.data.datasource.local.dao.FilmDao
import robert.pakpahan.jetpro1.data.datasource.local.dao.TvShowDao
import robert.pakpahan.jetpro1.vo.Resource
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val filmRepo: FilmRepository,
    private val tvShowRepo: TvShowRepository
) : ViewModel() {

    val film = MediatorLiveData<Resource<PagedList<Film>>>()
    val tvShow = MediatorLiveData<Resource<PagedList<TvShow>>>()
    val filmFavorite = MediatorLiveData<Resource<PagedList<Film>>>()
    val tvShowFavorite = MediatorLiveData<Resource<PagedList<TvShow>>>()

    init {
        film.addSource(filmRepo.getResult()) {film.value = it }
        tvShow.addSource(tvShowRepo.getResult()) { tvShow.value = it }
        filmFavorite.addSource(filmRepo.getFavorite()) { filmFavorite.value = it }
        tvShowFavorite.addSource(tvShowRepo.getFavorite()) { tvShowFavorite.value = it }
    }

    fun sorting(type: Type) {
        when (type) {
            Type.NAME -> {
                film.addSource(filmRepo.getResult(FilmDao.SORT_BY_NAME)) { film.value = it }
                tvShow.addSource(tvShowRepo.getResult(TvShowDao.SORT_BY_NAME)) { tvShow.value = it }
                filmFavorite.addSource(filmRepo.getFavorite(FilmDao.SORT_FAV_BY_NAME)) {
                    filmFavorite.value = it
                }
                tvShowFavorite.addSource(tvShowRepo.getFavorite(TvShowDao.SORT_FAV_BY_NAME)) { tvShowFavorite.value = it }
            }
            Type.RELEASE_DATA -> {
                film.addSource(filmRepo.getResult(FilmDao.SORT_BY_RELEASE)) { film.value = it }
                tvShow.addSource(tvShowRepo.getResult(TvShowDao.SORT_BY_RELEASE)) { tvShow.value = it }
                filmFavorite.addSource(filmRepo.getFavorite(FilmDao.SORT_FAV_BY_RELEASE)) {
                    filmFavorite.value = it
                }
                tvShowFavorite.addSource(tvShowRepo.getFavorite(TvShowDao.SORT_FAV_BY_RELEASE)) { tvShowFavorite.value = it }
            }
        }
    }

    enum class Type {
        NAME, RELEASE_DATA
    }
}