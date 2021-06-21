package robert.pakpahan.jetpro1.data.datasource.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import robert.pakpahan.jetpro1.data.datalayout.detail.TvShow

@Parcelize
data class TvShowResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<TvShow>
) : Parcelable