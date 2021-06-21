package robert.pakpahan.jetpro1.data.datasource.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import robert.pakpahan.jetpro1.utils.DummyDataResponse
import robert.pakpahan.jetpro1.utils.LiveDataTestUtil.getValue

@Suppress("UNCHECKED_CAST")
@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceTest {

    companion object {
        const val errorMessage = "error message"
    }

    private lateinit var remoteDataSource: RemoteDataSourceFake

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var callFilmResponse: Call<FilmResponse>

    @Mock
    private lateinit var callTvShowResponse: Call<TvShowResponse>

    @Mock
    private lateinit var callDetailFilmResponse: Call<DetailFilmResponse>

    @Mock
    private lateinit var callDetailTvShowResponse: Call<DetailTvShowResponse>

    @Mock
    private lateinit var observerApiResponseFilm: Observer<ApiResponse<FilmResponse>>

    @Mock
    private lateinit var observerApiResponseTvShow: Observer<ApiResponse<TvShowResponse>>

    @Mock
    private lateinit var observerApiResponseDetailFilm: Observer<ApiResponse<DetailFilmResponse>>

    @Mock
    private lateinit var observerApiResponseDetailTvShow: Observer<ApiResponse<DetailTvShowResponse>>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        remoteDataSource = RemoteDataSourceFake(apiService)
    }

    @Test
    fun FilmResponseSuccess() {
        val dataDummy = DummyDataResponse.filmResponse()
        Mockito.`when`(apiService.getFilms()).thenReturn(callFilmResponse)

        Mockito.doAnswer {
            (it.arguments[0] as Callback<FilmResponse>).onResponse(callFilmResponse, Response.success(dataDummy))
        }.`when`(callFilmResponse)?.enqueue(Mockito.any())

        val apiResponse = getValue(remoteDataSource.getFilms())

        Mockito.verify(apiService).getFilms()
        Mockito.verify(apiService.getFilms())?.enqueue(Mockito.any())

        remoteDataSource.getFilms().observeForever(observerApiResponseFilm)
        Mockito.verify(observerApiResponseFilm).onChanged(ApiResponse.Success(dataDummy))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Success)
        when (apiResponse) {
            is ApiResponse.Success -> {
                assertEquals(dataDummy, apiResponse.data)
                assertEquals(dataDummy.results, apiResponse.data.results)
                assertEquals(dataDummy.results.size, apiResponse.data.results.size)
            }
            else -> return
        }
    }

    @Test
    fun FilmResponseError() {
        Mockito.`when`(apiService.getFilms()).thenReturn(callFilmResponse)

        Mockito.doAnswer {
            (it.arguments[0] as Callback<FilmResponse>).onFailure(callFilmResponse, Throwable(errorMessage))
        }.`when`(callFilmResponse)?.enqueue(Mockito.any())

        val apiResponse = getValue(remoteDataSource.getFilms())

        Mockito.verify(apiService).getFilms()
        Mockito.verify(apiService.getFilms())?.enqueue(Mockito.any())

        remoteDataSource.getFilms().observeForever(observerApiResponseFilm)
        Mockito.verify(observerApiResponseFilm).onChanged(ApiResponse.Error(errorMessage))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Error)
        when (apiResponse) {
            is ApiResponse.Error -> {
                assertEquals(errorMessage, apiResponse.message)
            } else -> return
        }
    }

    @Test
    fun FilmResponseEmpty() {
        Mockito.`when`(apiService.getFilms()).thenReturn(callFilmResponse)

        Mockito.doAnswer {
            (it.arguments[0] as Callback<FilmResponse>).onResponse(callFilmResponse, Response.success(null))
        }.`when`(callFilmResponse)?.enqueue(Mockito.any())

        val apiResponse = getValue(remoteDataSource.getFilms())

        Mockito.verify(apiService).getFilms()
        Mockito.verify(apiService.getFilms())?.enqueue(Mockito.any())

        remoteDataSource.getFilms().observeForever(observerApiResponseFilm)
        Mockito.verify(observerApiResponseFilm).onChanged(ApiResponse.Empty(null))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Empty)
        when (apiResponse) {
            is ApiResponse.Empty -> {
                assertNull(apiResponse.data)
            } else -> return
        }
    }

    @Test
    fun tvShowResponseSuccess() {
        val dataDummy = DummyDataResponse.tvShowResponse()
        Mockito.`when`(apiService.getTvShows()).thenReturn(callTvShowResponse)

        Mockito.doAnswer {
            (it.arguments[0] as Callback<TvShowResponse>).onResponse(callTvShowResponse, Response.success(dataDummy))
        }.`when`(callTvShowResponse)?.enqueue(Mockito.any())

        val apiResponse = getValue(remoteDataSource.getTvShows())

        Mockito.verify(apiService).getTvShows()
        Mockito.verify(apiService.getTvShows())?.enqueue(Mockito.any())

        remoteDataSource.getTvShows().observeForever(observerApiResponseTvShow)
        Mockito.verify(observerApiResponseTvShow).onChanged(ApiResponse.Success(dataDummy))

        Assert.assertNotNull(apiResponse)
        Assert.assertTrue(apiResponse is ApiResponse.Success)
        when (apiResponse) {
            is ApiResponse.Success -> {
                assertEquals(dataDummy, apiResponse.data)
                assertEquals(dataDummy.results, apiResponse.data.results)
                assertEquals(dataDummy.results.size, apiResponse.data.results.size)
            } else -> return
        }
    }

    @Test
    fun tvShowResponseError() {
        Mockito.`when`(apiService.getTvShows()).thenReturn(callTvShowResponse)

        Mockito.doAnswer {
            (it.arguments[0] as Callback<TvShowResponse>)
                .onFailure(callTvShowResponse, Throwable(errorMessage))
        }.`when`(callTvShowResponse)?.enqueue(Mockito.any())

        val apiResponse = getValue(remoteDataSource.getTvShows())

        Mockito.verify(apiService).getTvShows()
        Mockito.verify(apiService.getTvShows())?.enqueue(Mockito.any())

        remoteDataSource.getTvShows().observeForever(observerApiResponseTvShow)
        Mockito.verify(observerApiResponseTvShow).onChanged(ApiResponse.Error(errorMessage))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Error)
        when (apiResponse) {
            is ApiResponse.Error -> {
                assertEquals(errorMessage, apiResponse.message)
            } else -> return
        }
    }

    @Test
    fun tvShowResponseEmpty() {
        Mockito.`when`(apiService.getTvShows()).thenReturn(callTvShowResponse)

        Mockito.doAnswer {
            (it.arguments[0] as Callback<TvShowResponse>)
                .onResponse(callTvShowResponse, Response.success(null))
        }.`when`(callTvShowResponse)?.enqueue(Mockito.any())

        val apiResponse = getValue(remoteDataSource.getTvShows())

        Mockito.verify(apiService).getTvShows()
        Mockito.verify(apiService.getTvShows())?.enqueue(Mockito.any())

        remoteDataSource.getTvShows().observeForever(observerApiResponseTvShow)
        Mockito.verify(observerApiResponseTvShow).onChanged(ApiResponse.Empty(null))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Empty)
        when (apiResponse) {
            is ApiResponse.Empty -> {
                assertNull(apiResponse.data)
            } else -> return
        }
    }

    @Test
    fun detailFilmResponseSuccess() {
        val dataDummy = DummyDataResponse.detailFilmResponse()
        val idData = dataDummy.id
        Mockito.`when`(apiService.getFilmById(idData)).thenReturn(callDetailFilmResponse)

        Mockito.doAnswer {
            (it.arguments[0] as Callback<DetailFilmResponse>)
                .onResponse(callDetailFilmResponse, Response.success(dataDummy))
        }.`when`(callDetailFilmResponse)?.enqueue(Mockito.any())

        val apiResponse = getValue(remoteDataSource.getFilmById(idData))

        Mockito.verify(apiService).getFilmById(idData)
        Mockito.verify(apiService.getFilmById(idData))?.enqueue(Mockito.any())

        remoteDataSource.getFilmById(idData).observeForever(observerApiResponseDetailFilm)
        Mockito.verify(observerApiResponseDetailFilm).onChanged(ApiResponse.Success(dataDummy))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Success)
        when (apiResponse) {
            is ApiResponse.Success -> {
                assertEquals(dataDummy, apiResponse.data)
            } else -> return
        }
    }

    @Test
    fun detailTvShowResponseSuccess() {
        val dataDummy = DummyDataResponse.detailTvShowResponse()
        val idData = dataDummy.id
        Mockito.`when`(apiService.getTvShowById(idData)).thenReturn(callDetailTvShowResponse)

        Mockito.doAnswer {
            (it.arguments[0] as Callback<DetailTvShowResponse>)
                .onResponse(callDetailTvShowResponse, Response.success(dataDummy))
        }.`when`(callDetailTvShowResponse)?.enqueue(Mockito.any())

        val apiResponse = getValue(remoteDataSource.getTvShowById(idData))

        Mockito.verify(apiService).getTvShowById(idData)
        Mockito.verify(apiService.getTvShowById(idData))?.enqueue(Mockito.any())

        remoteDataSource.getTvShowById(idData).observeForever(observerApiResponseDetailTvShow)
        Mockito.verify(observerApiResponseDetailTvShow).onChanged(ApiResponse.Success(dataDummy))

        assertNotNull(apiResponse)
        assertTrue(apiResponse is ApiResponse.Success)
        when (apiResponse) {
            is ApiResponse.Success -> {
                assertEquals(dataDummy, apiResponse.data)
            } else -> return
        }
    }
}