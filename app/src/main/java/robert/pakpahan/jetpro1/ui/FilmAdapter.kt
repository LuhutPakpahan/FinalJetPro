package robert.pakpahan.jetpro1.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import robert.pakpahan.jetpro1.R
import robert.pakpahan.jetpro1.base.BaseAdapter
import robert.pakpahan.jetpro1.data.datalayout.detail.Film
import robert.pakpahan.jetpro1.databinding.ItemContainerFilmBinding

class FilmAdapter : BaseAdapter<Film, ItemContainerFilmBinding>(R.layout.item_container_film, diffUtil) {
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Film>() {
            override fun areItemsTheSame(oldItem: Film, newItem: Film) =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Film, newItem: Film) =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: Holder<ItemContainerFilmBinding>, position: Int) {
        holder.binding?.let { bind ->
            getItem(position)?.apply {
                bind.model = this
                bind.root.setOnClickListener { onItemListener?.onItemClick(this) }
            }
        }
    }
}