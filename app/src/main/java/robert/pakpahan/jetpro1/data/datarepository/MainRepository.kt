package robert.pakpahan.jetpro1.data.datarepository

import robert.pakpahan.jetpro1.data.datasource.local.LocalDataSource
import robert.pakpahan.jetpro1.data.datasource.remote.RemoteDataSource
import robert.pakpahan.jetpro1.utils.Executors

class MainRepository private constructor(
    remote: RemoteDataSource, local: LocalDataSource, executors: Executors
){
    val film = FilmRepository.getInstance(
        executors = executors, remoteData = remote, localData = local.film
    )
    val tvShow = TvShowRepository.getInstance(
        executors = executors, remoteData = remote, localData = local.tvShow
    )

    companion object {
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance(
            remote: RemoteDataSource, local: LocalDataSource, executors: Executors
        ): MainRepository =
            instance ?: synchronized(this) {
                instance ?: MainRepository(remote, local, executors)
            }
    }
}