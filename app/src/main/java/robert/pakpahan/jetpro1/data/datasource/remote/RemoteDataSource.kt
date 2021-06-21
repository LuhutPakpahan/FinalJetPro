package robert.pakpahan.jetpro1.data.datasource.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import robert.pakpahan.jetpro1.data.datasource.remote.ApiResponse
import robert.pakpahan.jetpro1.data.datasource.remote.DetailFilmResponse
import robert.pakpahan.jetpro1.data.datasource.remote.ApiService
import robert.pakpahan.jetpro1.data.datasource.remote.FilmResponse
import robert.pakpahan.jetpro1.data.datasource.remote.DetailTvShowResponse
import robert.pakpahan.jetpro1.data.datasource.remote.TvShowResponse
import robert.pakpahan.jetpro1.utils.EspressoIdlingResource

class RemoteDataSource private constructor(private val apiService: ApiService) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiServiceTheFilmDB: ApiService): RemoteDataSource =
            instance ?: synchronized(this) { instance ?: RemoteDataSource(apiServiceTheFilmDB) }
    }

    private fun <T> enqueueCallback(mutableLiveData: MutableLiveData<ApiResponse<T>>): Callback<T?> {
        EspressoIdlingResource.increment()
        return object : Callback<T?> {
            override fun onResponse(call: Call<T?>, response: Response<T?>) {
                val data = response.body()
                mutableLiveData.postValue(
                    if (data != null) ApiResponse.Success(data) else ApiResponse.Empty()
                )
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<T?>, t: Throwable) {
                mutableLiveData.postValue(ApiResponse.Error(t.message.toString()))
                Timber.e(t.message.toString())
                EspressoIdlingResource.decrement()
            }
        }
    }

    fun getFilms(): LiveData<ApiResponse<FilmResponse>> {
        val resultData = MutableLiveData<ApiResponse<FilmResponse>>()
        apiService.getFilms()?.enqueue(enqueueCallback(resultData))
        return resultData
    }

    fun getTvShows(): LiveData<ApiResponse<TvShowResponse>> {
        val resultData = MutableLiveData<ApiResponse<TvShowResponse>>()
        apiService.getTvShows()?.enqueue(enqueueCallback(resultData))
        return resultData
    }

    fun getFilmById(id: Int): LiveData<ApiResponse<DetailFilmResponse>> {
        val resultData = MutableLiveData<ApiResponse<DetailFilmResponse>>()
        apiService.getFilmById(id).enqueue(enqueueCallback(resultData))
        return resultData
    }

    fun getTvShowById(id: Int): LiveData<ApiResponse<DetailTvShowResponse>> {
        val resultData = MutableLiveData<ApiResponse<DetailTvShowResponse>>()
        apiService.getTvShowById(id).enqueue(enqueueCallback(resultData))
        return resultData
    }
}
