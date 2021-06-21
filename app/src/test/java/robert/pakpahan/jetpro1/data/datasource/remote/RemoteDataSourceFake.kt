package robert.pakpahan.jetpro1.data.datasource.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import robert.pakpahan.jetpro1.utils.EspressoIdlingResource

class RemoteDataSourceFake(private val apiService: ApiService) {
    fun getFilms(): LiveData<ApiResponse<FilmResponse>> {
        val data = MutableLiveData<ApiResponse<FilmResponse>>()
        apiService.getFilms()?.enqueue(enqueueCallback(data))
        return data
    }

    fun getTvShows(): LiveData<ApiResponse<TvShowResponse>> {
        val data = MutableLiveData<ApiResponse<TvShowResponse>>()
        apiService.getTvShows()?.enqueue(enqueueCallback(data))
        return data
    }

    fun getFilmById(id: Int): LiveData<ApiResponse<DetailFilmResponse>> {
        val data = MutableLiveData<ApiResponse<DetailFilmResponse>>()
        apiService.getFilmById(id).enqueue(enqueueCallback(data))
        return data
    }

    fun getTvShowById(id: Int): LiveData<ApiResponse<DetailTvShowResponse>> {
        val data = MutableLiveData<ApiResponse<DetailTvShowResponse>>()
        apiService.getTvShowById(id).enqueue(enqueueCallback(data))
        return data
    }

    private fun <T> enqueueCallback(mutableLiveData: MutableLiveData<ApiResponse<T>>): Callback<T?> {
        EspressoIdlingResource.increment()
        return object : Callback<T?> {
            override fun onResponse(call: Call<T?>, response: Response<T?>) {
                val data = response.body()
                mutableLiveData.postValue(
                    if (data != null) ApiResponse.Success(data) else ApiResponse.Empty(data)
                )
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<T?>, t: Throwable) {
                mutableLiveData.postValue(ApiResponse.Error(t.message.toString()))
                EspressoIdlingResource.decrement()
            }
        }
    }

}