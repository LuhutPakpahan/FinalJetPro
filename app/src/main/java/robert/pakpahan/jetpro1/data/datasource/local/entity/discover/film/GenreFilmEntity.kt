package robert.pakpahan.jetpro1.data.datasource.local.entity.discover.film

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    indices = [Index(value = [GenreFilmEntity.FOREIGN_KEY])],
    foreignKeys = [ForeignKey(
        entity = FilmEntity::class,
        parentColumns = [FilmEntity.PRIMARY_KEY],
        childColumns = [GenreFilmEntity.FOREIGN_KEY],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )]
)

class GenreFilmEntity (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = PRIMARY_KEY)
    val pk: Int? = null,

    @NonNull
    @ColumnInfo(name = FOREIGN_KEY)
    val fk: Long,

    @NonNull
    @ColumnInfo(name = "genre_code")
    val genre: Int
): Parcelable {
    companion object {
        const val PRIMARY_KEY = "id_genre_film"
        const val FOREIGN_KEY = "id_genre_film_foreign"
    }
}