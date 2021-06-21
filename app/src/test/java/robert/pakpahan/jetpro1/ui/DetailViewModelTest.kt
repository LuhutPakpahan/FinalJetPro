package robert.pakpahan.jetpro1.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import robert.pakpahan.jetpro1.utils.LiveDataTestUtil.getValue
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import robert.pakpahan.jetpro1.R
import robert.pakpahan.jetpro1.data.datalayout.detail.DetailFilm
import robert.pakpahan.jetpro1.data.datalayout.detail.DetailTvShow
import robert.pakpahan.jetpro1.data.datarepository.FilmRepository
import robert.pakpahan.jetpro1.data.datarepository.TvShowRepository
import robert.pakpahan.jetpro1.ui.DetailActivity.Companion.DATA_DESTINATION
import robert.pakpahan.jetpro1.ui.DetailActivity.Companion.DATA_ID
import robert.pakpahan.jetpro1.utils.DummyData
import robert.pakpahan.jetpro1.vo.Resource

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest{
    private lateinit var viewModel: DetailViewModel

    @Mock
    lateinit var movieRepo: FilmRepository

    @Mock
    lateinit var tvShowRepo: TvShowRepository

    @Mock
    lateinit var observerMovie: Observer<Resource<DetailFilm>>

    @Mock
    lateinit var observerTvShow: Observer<Resource<DetailTvShow>>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = DetailViewModel(movieRepo, tvShowRepo)
    }

    @Test
    fun getDetailMovieResourceSuccess() {
        val dummyData = DummyData.getDetailFilm()
        val dataDes = R.id.detail_film
        val dataId = dummyData.id

        Mockito.`when`(movieRepo.getDetail(dataId)).thenReturn(MutableLiveData(Resource.Success(dummyData)))

        viewModel.init(dataDes, dataId)
        assertEquals(dataDes, viewModel.getExtra(DATA_DESTINATION))
        assertEquals(dataId, viewModel.getExtra(DATA_ID))
        verify(movieRepo).getDetail(dataId)
        assertNotNull(viewModel.film)

        viewModel.film.observeForever(observerMovie)
        verify(observerMovie).onChanged(Resource.Success(dummyData))

        val resource = getValue(viewModel.film)
        assertNotNull(resource)
        assertTrue(resource is Resource.Success)
        when (resource) {
            is Resource.Success -> {
                assertEquals(dummyData, resource.data)
                assertEquals(dataId, resource.data.id)
            } else -> return
        }
    }

    @Test
    fun getDetailTvShowResourceSuccess() {
        val dummyData = DummyData.getDetailTvShow()
        val dataDes = R.id.detail_tv_show
        val dataId = dummyData.id

        Mockito.`when`(tvShowRepo.getDetail(dataId)).thenReturn(MutableLiveData(Resource.Success(dummyData)))

        viewModel.init(dataDes, dataId)
        assertEquals(dataDes, viewModel.getExtra(DATA_DESTINATION))
        assertEquals(dataId, viewModel.getExtra(DATA_ID))
        verify(tvShowRepo).getDetail(dataId)
        assertNotNull(viewModel.tvShow)

        viewModel.tvShow.observeForever(observerTvShow)
        verify(observerTvShow).onChanged(Resource.Success(dummyData))

        val resource = getValue(viewModel.tvShow)
        assertNotNull(resource)
        assertTrue(resource is Resource.Success)
        when (resource) {
            is Resource.Success -> {
                assertEquals(dummyData, resource.data)
                assertEquals(dataId, resource.data.id)
            } else -> return
        }
    }
}