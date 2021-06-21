package robert.pakpahan.jetpro1.ui

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import robert.pakpahan.jetpro1.R
import robert.pakpahan.jetpro1.base.BaseFragment
import robert.pakpahan.jetpro1.data.datalayout.detail.DetailFilm
import robert.pakpahan.jetpro1.data.datarepository.Utils
import robert.pakpahan.jetpro1.databinding.FragmentDetailFilmBinding
import robert.pakpahan.jetpro1.vo.Resource

class DetailFilmFragment : BaseFragment<FragmentDetailFilmBinding>() {

    private val viewModel by activityViewModels<DetailViewModel>()

    override val layoutFragment: Int = R.layout.fragment_detail_film

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.ivBack.setOnClickListener { activity?.onBackPressed() }
        viewModel.film.observe(viewLifecycleOwner, { handleStat(it) })
    }

    private fun handleStat(resource: Resource<DetailFilm>) {
        with(binding) {
            when (resource) {
                is Resource.Loading -> isLoading = true
                is Resource.Empty -> isLoading = false
                is Resource.Success -> {
                    isLoading = false
                    resource.data.let { data ->
                        visibleContent()
                        model = data
                        tvReadMore.setOnClickListener {
                            if (tvReadMore.text.toString() == "Read More") {
                                tvOverview.maxLines = Int.MAX_VALUE
                                tvOverview.ellipsize = null
                                tvReadMore.setText(R.string.read_less)
                            } else {
                                tvOverview.maxLines = 4
                                tvOverview.ellipsize = TextUtils.TruncateAt.END
                                tvReadMore.setText(R.string.read_more)
                            }
                        }
                        ivFavorite.setOnClickListener {
                            Utils.confirmDialog(requireContext(), data.title, data.isFavorite) {
                                viewModel.setFavoriteFilm(data.id, !data.isFavorite)
                                activity?.toast("Success ${if (data.isFavorite) "delete" else "add"} ${data.title} ${if (data.isFavorite) "from" else "to"} favorite")
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    isLoading = false
                    activity?.toast(resource.message)
                }
            }
        }
    }

    private fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun visibleContent() {
        with(binding) {
            ivBackground.visibility = View.VISIBLE
            ivPoster.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE
            tvOverview.visibility = View.VISIBLE
            tvGenre.visibility = View.VISIBLE
            tvReleaseDate.visibility = View.VISIBLE
            tvScore.visibility = View.VISIBLE
            tvReadMore.visibility = View.VISIBLE
            ivFavorite.visibility = View.VISIBLE
        }
    }

}

