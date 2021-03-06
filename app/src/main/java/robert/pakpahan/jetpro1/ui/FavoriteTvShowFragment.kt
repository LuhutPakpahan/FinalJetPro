package robert.pakpahan.jetpro1.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.navGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import robert.pakpahan.jetpro1.MyApp
import robert.pakpahan.jetpro1.R
import robert.pakpahan.jetpro1.base.BaseFragment
import robert.pakpahan.jetpro1.base.ItemListener
import robert.pakpahan.jetpro1.data.datalayout.detail.TvShow
import robert.pakpahan.jetpro1.databinding.FragmentTvShowBinding
import robert.pakpahan.jetpro1.viewmodel.ViewModelFactory
import robert.pakpahan.jetpro1.vo.Resource
import timber.log.Timber
import javax.inject.Inject

class FavoriteTvShowFragment : BaseFragment<FragmentTvShowBinding>(), ItemListener<TvShow> {

    companion object {
        private const val ARG_SECTION_NUMBER = "FAVORITE_TV_FRAGMENT"

        fun newInstance(index: Int) = FavoriteTvShowFragment().apply {
            arguments = Bundle().apply { putInt(ARG_SECTION_NUMBER, index) }
        }
    }

    @Inject
    internal lateinit var factory: ViewModelFactory

    private lateinit var adapter: TvShowAdapter

    private val viewModel by navGraphViewModels<MainViewModel>(R.id.nav_graph_main) { factory }

    override val layoutFragment: Int = R.layout.fragment_tv_show

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApp).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TvShowAdapter().apply {
            onItemListener = this@FavoriteTvShowFragment
            binding.rvTvShow.setHasFixedSize(true)
            binding.rvTvShow.adapter = this
        }
        viewModel.tvShowFavorite.observe(viewLifecycleOwner, { handleStat(it) })
    }

    private fun handleStat(resource: Resource<PagedList<TvShow>>) {
        with(binding) {
            when (resource) {
                is Resource.Loading -> isLoading = true
                is Resource.Empty -> {
                    isLoading = false
                    activity?.toast(getString(R.string.empty_message))
                }
                is Resource.Success -> {
                    isLoading = false
                    resource.data.let { adapter.submitList(it) }
                    Timber.d("Data size : %d", resource.data.size)
                }
                is Resource.Error -> {
                    isLoading = false
                    findNavController().getViewModelStoreOwner(R.id.nav_graph_main).viewModelStore.clear()
                    activity?.toast(resource.message)
                }
            }
        }
    }

    private fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(model: TvShow) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.DATA_EXTRA, arrayListOf(R.id.detail_tv_show, model.id))
        }
        requireActivity().startActivity(intent)
    }
}