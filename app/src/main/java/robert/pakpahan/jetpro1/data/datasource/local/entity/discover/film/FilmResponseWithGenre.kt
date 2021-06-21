package robert.pakpahan.jetpro1.data.datasource.local.entity.discover.film

import androidx.room.Embedded
import androidx.room.Relation

data class FilmResponseWithGenre(
    @Embedded
    val entity: FilmResponseEntity,

    @Relation(
        parentColumn = FilmResponseEntity.PRIMARY_KEY,
        entityColumn = FilmEntity.FOREIGN_KEY,
        entity = FilmEntity::class
    )
    val resultWithWithGenre: List<FilmWithGenre>
)