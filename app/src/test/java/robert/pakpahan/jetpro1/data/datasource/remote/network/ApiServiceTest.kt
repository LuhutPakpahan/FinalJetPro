package robert.pakpahan.jetpro1.data.datasource.remote.network

import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import robert.pakpahan.jetpro1.data.datasource.remote.*
import robert.pakpahan.jetpro1.utils.Constants
import robert.pakpahan.jetpro1.utils.Constants.TIME_OUT
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class ApiServiceTest {
    private val apiService = RetrofitBuilder.service(Constants.TMDB_BASE_URL, ApiService::class.java)
    private val contDownLatch = CountDownLatch(1)

    @Test
    fun getFilmFromApi() {
        apiService.getFilms()?.enqueue(object : Callback<FilmResponse> {
            override fun onResponse(call: Call<FilmResponse>, response: Response<FilmResponse>) {
                contDownLatch.countDown()
                print(response.body()?.results)
            }

            override fun onFailure(call: Call<FilmResponse>, t: Throwable) {
                contDownLatch.countDown()
                t.printStackTrace()
            }

        })
        contDownLatch.await(TIME_OUT, TimeUnit.SECONDS)
    }

    @Test
    fun getTvShowFromApi() {
        apiService.getTvShows()?.enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(call: Call<TvShowResponse>, showResponse: Response<TvShowResponse>) {
                contDownLatch.countDown()
                print(showResponse.body()?.results)
            }
            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
        contDownLatch.await(TIME_OUT, TimeUnit.SECONDS)
    }

    @Test
    fun getFilmByIdFromApi() {
        apiService.getFilmById(650747).enqueue(object : Callback<DetailFilmResponse> {
            override fun onResponse(
                call: Call<DetailFilmResponse>, filmResponse: Response<DetailFilmResponse>
            ) {
                contDownLatch.countDown()
                print(filmResponse.body())
            }
            override fun onFailure(call: Call<DetailFilmResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
        contDownLatch.await(TIME_OUT, TimeUnit.SECONDS)
    }

    @Test
    fun getTvShowByIdFromApi() {
        apiService.getTvShowById(71712).enqueue(object : Callback<DetailTvShowResponse> {
            override fun onResponse(
                call: Call<DetailTvShowResponse>, tvShowResponse: Response<DetailTvShowResponse>
            ) {
                contDownLatch.countDown()
                print(tvShowResponse.body())
            }
            override fun onFailure(call: Call<DetailTvShowResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
        contDownLatch.await(TIME_OUT, TimeUnit.SECONDS)
    }
}