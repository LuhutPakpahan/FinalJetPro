package robert.pakpahan.jetpro1.binding

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import robert.pakpahan.jetpro1.R
import robert.pakpahan.jetpro1.data.datalayout.detail.Genre

object ItemBindingAdapter {
    val genres: MutableMap<Int, String> = mutableMapOf()

    init {
        genres[28] = "Action"
        genres[12] = "Adventure"
        genres[16] = "Animation"
        genres[35] = "Comedy"
        genres[80] = "Crime"
        genres[99] = "Documentary"
        genres[18] = "Drama"
        genres[10751] = "Family"
        genres[14] = "Fantasy"
        genres[36] = "History"
        genres[27] = "Horror"
        genres[10402] = "Music"
        genres[9648] = "Mystery"
        genres[10749] = "Romance"
        genres[878] = "Science Fiction"
        genres[10770] = "TV Movie"
        genres[53] = "Thriller"
        genres[10752] = "War"
        genres[37] = "Western"
        genres[10759] = "Action & Adventure"
        genres[10762] = "Kids"
        genres[10763] = "News"
        genres[10764] = "Reality"
        genres[10765] = "Sci-Fi & Fantasy"
        genres[10766] = "Soap"
        genres[10767] = "Talk"
        genres[10768] = "War & Politics"
    }

    @JvmStatic
    @BindingAdapter("android:listGenre")
    fun setListGenre(view: AppCompatTextView, idListGenre: List<Int>?) {
        val genre = StringBuilder()
        idListGenre?.forEachIndexed { index, key ->
            genre.append(if (index != idListGenre.size - 1) "${genres[key]}, " else genres[key])
        }
        view.text = genre.toString()
    }

    @JvmStatic
    @BindingAdapter("android:genres")
    fun setGenre(view: AppCompatTextView, it: List<Genre>?) {
        val genre = StringBuilder()
        it?.forEachIndexed { i, v ->
            genre.append(if (i != it.size - 1) "${v.name}, " else v.name)
        }
        view.text = genre.toString()
    }

    @JvmStatic
    @BindingAdapter("android:favoriteImage")
    fun setFavoriteImage(view: View, isFavorite: Boolean?) {
        isFavorite?.apply {
            val imageView: ImageView = view.findViewById(R.id.ivFavorite)
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    view.context,
                    if (isFavorite) R.drawable.ic_delete else R.drawable.ic_favorite
                )
            )
        }
    }
}