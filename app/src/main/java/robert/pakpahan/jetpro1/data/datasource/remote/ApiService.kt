package robert.pakpahan.jetpro1.data.datasource.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import robert.pakpahan.jetpro1.utils.Constants

interface ApiService {
    @GET("discover/movie?api_key=${Constants.TMDB_API_KEY}")
    fun getFilms(): Call<FilmResponse>?

    @GET("discover/tv?api_key=${Constants.TMDB_API_KEY}")
    fun getTvShows(): Call<TvShowResponse>?

    @GET("movie/{id}?api_key=${Constants.TMDB_API_KEY}")
    fun getFilmById(
        @Path("id") id: Int,
    ): Call<DetailFilmResponse>

    @GET("tv/{id}?api_key=${Constants.TMDB_API_KEY}")
    fun getTvShowById(
        @Path("id") id: Int,
    ): Call<DetailTvShowResponse>
}