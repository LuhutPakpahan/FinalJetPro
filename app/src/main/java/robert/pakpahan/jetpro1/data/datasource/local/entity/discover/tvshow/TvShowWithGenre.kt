package robert.pakpahan.jetpro1.data.datasource.local.entity.discover.tvshow

import androidx.room.Embedded
import androidx.room.Relation

data class TvShowWithGenre(
    @Embedded
    val entity: TvShowEntity,

    @Relation(
        parentColumn = TvShowEntity.PRIMARY_KEY,
        entityColumn = GenreTvShowEntity.FOREIGN_KEY,
        entity = GenreTvShowEntity::class
    )
    val genreIds: List<GenreTvShowEntity>
)