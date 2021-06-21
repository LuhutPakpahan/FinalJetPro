package robert.pakpahan.jetpro1.data.datalayout.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Genre(
    val genre_code: Int,
    val name: String
) : Parcelable