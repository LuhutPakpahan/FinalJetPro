package robert.pakpahan.jetpro1.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import robert.pakpahan.jetpro1.R
import robert.pakpahan.jetpro1.data.datalayout.detail.DetailFilm
import robert.pakpahan.jetpro1.data.datalayout.detail.DetailTvShow
import robert.pakpahan.jetpro1.data.datarepository.FilmRepository
import robert.pakpahan.jetpro1.data.datarepository.TvShowRepository
import robert.pakpahan.jetpro1.ui.DetailActivity.Companion.DATA_DESTINATION
import robert.pakpahan.jetpro1.ui.DetailActivity.Companion.DATA_ID
import robert.pakpahan.jetpro1.vo.Resource
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val filmRepo: FilmRepository,
    private val tvShowRepo: TvShowRepository
) : ViewModel() {

    private lateinit var dataExtra: MutableList<Int>

    val film by lazy { MediatorLiveData<Resource<DetailFilm>>() }
    val tvShow by lazy { MediatorLiveData<Resource<DetailTvShow>>() }

    fun init(dataDes: Int, dataId: Int) {
        dataExtra = mutableListOf(dataDes, dataId)
        when (dataExtra[DATA_DESTINATION]) {
            R.id.detail_film -> film.addSource(filmRepo.getDetail(dataExtra[DATA_ID])) {
                film.value = it
            }
            R.id.detail_tv_show -> tvShow.addSource(tvShowRepo.getDetail(dataExtra[DATA_ID])) {
                tvShow.value = it
            }
        }
    }

    fun getExtra(data: Int) = this.dataExtra[data]

    fun setFavoriteFilm(id: Int, isFavorite: Boolean) = filmRepo.setFavorite(id, isFavorite)

    fun setFavoriteTvShow(id: Int, isFavorite: Boolean) = tvShowRepo.setFavorite(id, isFavorite)
}