package robert.pakpahan.jetpro1.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import robert.pakpahan.jetpro1.R
import robert.pakpahan.jetpro1.base.BaseAdapter
import robert.pakpahan.jetpro1.data.datalayout.detail.TvShow
import robert.pakpahan.jetpro1.databinding.ItemContainerTvShowBinding

 class TvShowAdapter : BaseAdapter<TvShow, ItemContainerTvShowBinding>(R.layout.item_container_tv_show, diffUtil) {
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow) =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow) =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: Holder<ItemContainerTvShowBinding>, position: Int) {
        holder.binding?.let { bind ->
            getItem(position)?.apply {
                bind.model = this
                bind.root.setOnClickListener { onItemListener?.onItemClick(this) }
            }
        }
    }
}