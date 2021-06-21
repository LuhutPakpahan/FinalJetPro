package robert.pakpahan.jetpro1.data.datasource.local.entity.detail.film

import androidx.room.Embedded
import androidx.room.Relation

data class DetailFilmWithGenre (
    @Embedded
    val entity: DetailFilmResponseEntity,

    @Relation(
        parentColumn = DetailFilmResponseEntity.PRIMARY_KEY,
        entityColumn = DetailGenreFilmEntity.FOREIGN_KEY,
        entity = DetailGenreFilmEntity::class
    )
    val genre: List<DetailGenreFilmEntity>
)