package robert.pakpahan.jetpro1.data.datasource.local.entity.discover.film

import androidx.room.Embedded
import androidx.room.Relation

data class FilmWithGenre(
    @Embedded
    val entity: FilmEntity,

    @Relation(
        parentColumn = FilmEntity.PRIMARY_KEY,
        entityColumn = GenreFilmEntity.FOREIGN_KEY,
        entity = GenreFilmEntity::class
    )
    val genreIds: List<GenreFilmEntity>
)