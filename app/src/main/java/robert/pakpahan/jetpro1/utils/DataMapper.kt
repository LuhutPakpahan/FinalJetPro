package robert.pakpahan.jetpro1.utils

import robert.pakpahan.jetpro1.data.datalayout.detail.*
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.film.DetailFilmResponseEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.film.DetailFilmWithGenre
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.tvshow.DetailTvShowResponseEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.tvshow.DetailTvShowWithGenre
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.film.FilmEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.film.FilmResponseEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.film.FilmWithGenre
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.tvshow.TvShowEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.tvshow.TvShowResponseEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.tvshow.TvShowWithGenre
import robert.pakpahan.jetpro1.data.datasource.remote.DetailFilmResponse
import robert.pakpahan.jetpro1.data.datasource.remote.DetailTvShowResponse
import robert.pakpahan.jetpro1.data.datasource.remote.FilmResponse
import robert.pakpahan.jetpro1.data.datasource.remote.TvShowResponse

object DataMapper {

    fun listFilmWithGenreToFilms(list: MutableList<FilmWithGenre>?): List<Film> {
        val results = mutableListOf<Film>()
        list?.forEach {
            results.add(Film(
                vote_average = it.entity.vote_average,
                video = it.entity.video,
                popularity = it.entity.popularity,
                vote_count = it.entity.vote_count,
                poster_path = it.entity.poster_path,
                original_title = it.entity.original_title,
                overview = it.entity.overview,
                release_date = it.entity.release_date,
                title = it.entity.title,
                original_language = it.entity.original_language,
                backdrop_path = it.entity.backdrop_path,
                id = it.entity.pk,
                adult = it.entity.adult,
                genre_ids = it.genreIds.map { genreIds -> genreIds.genre }
            ))
        }
        return results
    }

    fun listTvShowWithGenreToTvShows(list: MutableList<TvShowWithGenre>?): List<TvShow> {
        val results = mutableListOf<TvShow>()
        list?.forEach {
            results.add(
                TvShow(
                    vote_average = it.entity.vote_average,
                    popularity = it.entity.popularity,
                    vote_count = it.entity.vote_count,
                    poster_path = it.entity.poster_path,
                    overview = it.entity.overview,
                    original_language = it.entity.original_language,
                    backdrop_path = it.entity.backdrop_path,
                    genre_ids = it.genreIds.map { genreIds -> genreIds.genre },
                    name = it.entity.name,
                    first_air_date = it.entity.first_air_date,
                    original_name = it.entity.original_name,
                    id = it.entity.pk,
                )
            )
        }
        return results
    }

    fun detailFilmWithGenreToDetailFilm(data: DetailFilmWithGenre?) =
        data?.run {
            DetailFilm(
                isFavorite = entity.isFavorite,
                id = entity.pk,
                backdrop_path = entity.backdrop_path,
                adult = entity.adult,
                original_language = entity.original_language,
                title = entity.title,
                release_date = entity.release_date,
                overview = entity.overview,
                original_title = entity.original_title,
                poster_path = entity.poster_path,
                vote_count = entity.vote_count,
                popularity = entity.popularity,
                video = entity.video,
                vote_average = entity.vote_average,
                tagline = entity.tagline,
                status = entity.status,
                runtime = entity.runtime,
                revenue = entity.revenue,
                imdb_id = entity.imdb_id,
                homepage = entity.homepage,
                budget = entity.budget,
                genres = genre.map { Genre(it.genre_code, it.name) }
            )
        }

    fun detailTvShowWithGenreToDetailTvShow(data: DetailTvShowWithGenre?) =
        data?.run {
            DetailTvShow(
                isFavorite = entity.isFavorite,
                id = entity.pk,
                backdrop_path = entity.backdrop_path,
                original_language = entity.original_language,
                overview = entity.overview,
                poster_path = entity.poster_path,
                vote_count = entity.vote_count,
                popularity = entity.popularity,
                vote_average = entity.vote_average,
                status = entity.status,
                homepage = entity.homepage,
                genres = genre.map { Genre(it.genre_code, it.name) },
                name = entity.name,
                first_air_date = entity.first_air_date,
                original_name = entity.original_name,
                in_production = entity.in_production,
                last_air_date = entity.last_air_date,
                number_of_episodes = entity.number_of_episodes,
                number_of_seasons = entity.number_of_seasons,
                type = entity.type
            )
        }

    fun filmResponseToEntity(response: FilmResponse): FilmResponseEntity {
        return FilmResponseEntity(
            page = response.page,
            total_results = response.total_results,
            total_pages = response.total_pages
        )
    }

    fun tvShowResponseToEntity(response: TvShowResponse): TvShowResponseEntity {
        return TvShowResponseEntity(
            page = response.page,
            total_results = response.total_results,
            total_pages = response.total_pages
        )
    }

    fun filmToEntity(
        idInsertResponse: Long,
        model: Film
    ): FilmEntity {
        return FilmEntity(
            fk = idInsertResponse,
            pk = model.id,
            popularity = model.popularity,
            vote_count = model.vote_count,
            poster_path = model.poster_path,
            backdrop_path = model.backdrop_path,
            original_language = model.original_language,
            original_title = model.original_title,
            title = model.title,
            vote_average = model.vote_average,
            overview = model.overview,
            release_date = model.release_date
        )
    }

    fun tvShowToEntity(
        idInsertResponse: Long,
        model: TvShow
    ): TvShowEntity {
        return TvShowEntity(
            pk = model.id,
            fk = idInsertResponse,
            popularity = model.popularity,
            vote_count = model.vote_count,
            poster_path = model.poster_path,
            backdrop_path = model.backdrop_path,
            original_language = model.original_language,
            vote_average = model.vote_average,
            overview = model.overview,
            original_name = model.name,
            first_air_date = model.first_air_date,
            name = model.name
        )
    }

    fun detailFilmResponseToEntity(response: DetailFilmResponse): DetailFilmResponseEntity {
        return DetailFilmResponseEntity(
            pk = response.id,
            adult = response.adult,
            backdrop_path = response.backdrop_path,
            budget = response.budget,
            homepage = response.homepage,
            imdb_id = response.imdb_id,
            original_language = response.original_language,
            title = response.title,
            release_date = response.release_date,
            overview = response.overview,
            vote_average = response.vote_average,
            original_title = response.original_title,
            poster_path = response.poster_path,
            vote_count = response.vote_count,
            popularity = response.popularity,
            revenue = response.revenue,
            runtime = response.runtime,
            status = response.status,
            tagline = response.tagline,
            video = response.video
        )
    }

    fun detailTvShowResponseToEntity(response: DetailTvShowResponse): DetailTvShowResponseEntity {
        return DetailTvShowResponseEntity(
            pk = response.id,
            backdrop_path = response.backdrop_path,
            homepage = response.homepage,
            original_language = response.original_language,
            overview = response.overview,
            vote_average = response.vote_average,
            poster_path = response.poster_path,
            vote_count = response.vote_count,
            popularity = response.popularity,
            status = response.status,
            type = response.type,
            number_of_seasons = response.number_of_seasons,
            number_of_episodes = response.number_of_episodes,
            last_air_date = response.last_air_date,
            in_production = response.in_production,
            original_name = response.original_name,
            first_air_date = response.first_air_date,
            name = response.name,
        )
    }
}