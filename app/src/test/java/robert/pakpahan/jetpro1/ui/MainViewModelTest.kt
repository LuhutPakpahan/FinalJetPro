package robert.pakpahan.jetpro1.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import robert.pakpahan.jetpro1.data.datalayout.detail.Film
import robert.pakpahan.jetpro1.data.datalayout.detail.TvShow
import robert.pakpahan.jetpro1.data.datarepository.FilmRepository
import robert.pakpahan.jetpro1.data.datarepository.TvShowRepository
import robert.pakpahan.jetpro1.utils.DummyData.getFilm
import robert.pakpahan.jetpro1.utils.DummyData.getTvShow
import robert.pakpahan.jetpro1.utils.PagedListTestUtil
import robert.pakpahan.jetpro1.vo.Resource
import robert.pakpahan.jetpro1.vo.Resource.*

@RunWith(MockitoJUnitRunner.Silent::class)
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private val filmResourceSuccess = Success(PagedListTestUtil.mockPagedList(getFilm()))
    private val tvShowResourceSuccess = Success(PagedListTestUtil.mockPagedList(getTvShow()))
    private val resourceError = Error("error", null)
    private val resourceEmpty = Empty(null)

    @Mock
    lateinit var filmRepo: FilmRepository

    @Mock
    lateinit var tvShowRepo: TvShowRepository

    @Mock
    lateinit var observerFilm: Observer<Resource<PagedList<Film>>>

    @Mock
    lateinit var observerTvShow: Observer<Resource<PagedList<TvShow>>>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(filmRepo, tvShowRepo)
    }

    @Test
    fun getResourceFilmSuccess() {
        Mockito.`when`(filmRepo.getResult()).thenReturn(MutableLiveData(filmResourceSuccess))
        Mockito.verify(filmRepo).getResult()

        MutableLiveData(filmResourceSuccess).observeForever(observerFilm)
        Mockito.verify(observerFilm).onChanged(filmResourceSuccess)

        assertEquals(getFilm(), filmResourceSuccess.data.snapshot())
    }

    @Test
    fun getResourceFilmError() {
        Mockito.`when`(filmRepo.getResult()).thenReturn(MutableLiveData(resourceError))
        Mockito.verify(filmRepo).getResult()

        MutableLiveData(resourceError).observeForever(observerFilm)
        Mockito.verify(observerFilm).onChanged(resourceError)
    }

    @Test
    fun getResourceFilmEmpty() {
        Mockito.`when`(filmRepo.getResult()).thenReturn(MutableLiveData(resourceEmpty))
        Mockito.verify(filmRepo).getResult()

        MutableLiveData(resourceEmpty).observeForever(observerFilm)
        Mockito.verify(observerFilm).onChanged(resourceEmpty)
    }

    @Test
    fun getResourceTvShowSuccess() {
        Mockito.`when`(tvShowRepo.getResult()).thenReturn(MutableLiveData(tvShowResourceSuccess))
        Mockito.verify(tvShowRepo).getResult()

        MutableLiveData(tvShowResourceSuccess).observeForever(observerTvShow)
        Mockito.verify(observerTvShow).onChanged(tvShowResourceSuccess)

        assertEquals(getTvShow(), tvShowResourceSuccess.data.snapshot())
    }

    @Test
    fun getResourceTvShowError() {
        Mockito.`when`(tvShowRepo.getResult()).thenReturn(MutableLiveData(resourceError))
        Mockito.verify(tvShowRepo).getResult()

        MutableLiveData(resourceError).observeForever(observerTvShow)
        Mockito.verify(observerTvShow).onChanged(resourceError)
    }

    @Test
    fun getResourceTvShowEmpty() {
        Mockito.`when`(tvShowRepo.getResult()).thenReturn(MutableLiveData(resourceEmpty))
        Mockito.verify(tvShowRepo).getResult()

        MutableLiveData(resourceEmpty).observeForever(observerTvShow)
        Mockito.verify(observerTvShow).onChanged(resourceEmpty)
    }

    @Test
    fun getResourceFavoriteFilmSuccess() {
        Mockito.`when`(filmRepo.getFavorite()).thenReturn(MutableLiveData(filmResourceSuccess))
        Mockito.verify(filmRepo).getFavorite()

        MutableLiveData(filmResourceSuccess).observeForever(observerFilm)
        Mockito.verify(observerFilm).onChanged(filmResourceSuccess)

        assertEquals(getFilm(), filmResourceSuccess.data.snapshot())
    }

    @Test
    fun getResourceFavoriteFilmError() {
        Mockito.`when`(filmRepo.getFavorite()).thenReturn(MutableLiveData(resourceError))
        Mockito.verify(filmRepo).getFavorite()

        MutableLiveData(resourceError).observeForever(observerFilm)
        Mockito.verify(observerFilm).onChanged(resourceError)
    }

    @Test
    fun getResourceFavoriteFilmEmpty() {
        Mockito.`when`(filmRepo.getFavorite()).thenReturn(MutableLiveData(resourceEmpty))
        Mockito.verify(filmRepo).getFavorite()

        MutableLiveData(resourceEmpty).observeForever(observerFilm)
        Mockito.verify(observerFilm).onChanged(resourceEmpty)
    }

    @Test
    fun getResourceFavoriteTVShowSuccess() {
        Mockito.`when`(tvShowRepo.getFavorite()).thenReturn(MutableLiveData(tvShowResourceSuccess))
        Mockito.verify(tvShowRepo).getFavorite()

        MutableLiveData(tvShowResourceSuccess).observeForever(observerTvShow)
        Mockito.verify(observerTvShow).onChanged(tvShowResourceSuccess)

        assertEquals(getTvShow(), tvShowResourceSuccess.data.snapshot())
    }

    @Test
    fun getResourceFavoriteTVShowError() {
        Mockito.`when`(tvShowRepo.getFavorite()).thenReturn(MutableLiveData(resourceError))
        Mockito.verify(tvShowRepo).getFavorite()

        MutableLiveData(resourceError).observeForever(observerTvShow)
        Mockito.verify(observerTvShow).onChanged(resourceError)
    }

    @Test
    fun getResourceFavoriteTVShowEmpty() {
        Mockito.`when`(tvShowRepo.getFavorite()).thenReturn(MutableLiveData(resourceEmpty))
        Mockito.verify(tvShowRepo).getFavorite()

        MutableLiveData(resourceEmpty).observeForever(observerTvShow)
        Mockito.verify(observerTvShow).onChanged(resourceEmpty)
    }
}