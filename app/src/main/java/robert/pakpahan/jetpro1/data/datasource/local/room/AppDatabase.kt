package robert.pakpahan.jetpro1.data.datasource.local.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import robert.pakpahan.jetpro1.data.datasource.local.dao.FilmDao
import robert.pakpahan.jetpro1.data.datasource.local.dao.TvShowDao
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.film.DetailFilmResponseEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.film.DetailGenreFilmEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.tvshow.DetailGenreTvShowEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.detail.tvshow.DetailTvShowResponseEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.film.FilmEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.film.FilmResponseEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.film.GenreFilmEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.tvshow.GenreTvShowEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.tvshow.TvShowEntity
import robert.pakpahan.jetpro1.data.datasource.local.entity.discover.tvshow.TvShowResponseEntity
import robert.pakpahan.jetpro1.utils.Constants

@Database(
    entities = [
        FilmEntity::class,
        FilmResponseEntity::class,
        GenreFilmEntity::class,
        DetailFilmResponseEntity::class,
        DetailGenreFilmEntity::class,

        TvShowEntity::class,
        TvShowResponseEntity::class,
        GenreTvShowEntity::class,
        DetailTvShowResponseEntity::class,
        DetailGenreTvShowEntity::class,
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
    abstract fun tvShowDao(): TvShowDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(application: Application): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            application, AppDatabase::class.java, Constants.DATABASE_NAME
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}