package robert.pakpahan.jetpro1.data.datasource.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import robert.pakpahan.jetpro1.data.datalayout.detail.Film

@Parcelize
data class FilmResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<Film>
) : Parcelable