package robert.pakpahan.jetpro1.data.datasource.local.entity.detail.film

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    indices = [Index(value = [DetailGenreFilmEntity.FOREIGN_KEY])],
    foreignKeys = [ForeignKey(
        entity = DetailFilmResponseEntity::class,
        parentColumns = [DetailFilmResponseEntity.PRIMARY_KEY],
        childColumns = [DetailGenreFilmEntity.FOREIGN_KEY],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )]
)

data class DetailGenreFilmEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = PRIMARY_KEY)
    val pk: Long? = null,

    @NonNull
    @ColumnInfo(name = FOREIGN_KEY)
    val fk: Long,

    val genre_code: Int,
    val name: String
) : Parcelable {
    companion object {
        const val PRIMARY_KEY = "id_detail_genre_film"
        const val FOREIGN_KEY = "id_detail_genre_film_foreign"
    }
}