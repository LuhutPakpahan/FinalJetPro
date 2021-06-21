package robert.pakpahan.jetpro1.data.datasource.local.entity.discover.film

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    indices = [Index(value = [FilmEntity.FOREIGN_KEY])],
    foreignKeys = [ForeignKey(
        entity = FilmResponseEntity::class,
        parentColumns = [FilmResponseEntity.PRIMARY_KEY],
        childColumns = [FilmEntity.FOREIGN_KEY],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )],
)

data class FilmEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = PRIMARY_KEY)
    val pk: Int,

    @NonNull
    @ColumnInfo(name = FOREIGN_KEY)
    val fk: Long,

    var isFavorite: Boolean = false,
    val popularity: Double = 0.0,
    val vote_count: Int = 0,
    val video: Boolean = false,
    val poster_path: String = "",
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val title: String = "",
    val vote_average: Float = 0.0f,
    val overview: String = "",
    val release_date: String = ""
) : Parcelable {
    companion object {
        const val PRIMARY_KEY = "id_film"
        const val FOREIGN_KEY = "id_film_foreign"
    }
}