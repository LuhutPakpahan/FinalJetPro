package robert.pakpahan.jetpro1.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.navGraphViewModels
import androidx.paging.PagedList
import androidx.navigation.fragment.findNavController
import robert.pakpahan.jetpro1.MyApp
import robert.pakpahan.jetpro1.R
import robert.pakpahan.jetpro1.base.BaseFragment
import robert.pakpahan.jetpro1.base.ItemListener
import robert.pakpahan.jetpro1.data.datalayout.detail.Film
import robert.pakpahan.jetpro1.databinding.FragmentFilmBinding
import robert.pakpahan.jetpro1.viewmodel.ViewModelFactory
import robert.pakpahan.jetpro1.vo.Resource
import timber.log.Timber
import javax.inject.Inject

class FavoriteFilmFragment : BaseFragment<FragmentFilmBinding>(), ItemListener<Film> {

    companion object {
        private const val ARG_SECTION_NUMBER = "FAVORITE_Film_FRAGMENT"

        fun newInstance(index: Int) = FavoriteFilmFragment().apply {
            arguments = Bundle().apply { putInt(ARG_SECTION_NUMBER, index) }
        }
    }

    @Inject
    internal lateinit var factory: ViewModelFactory

    private val viewModel by navGraphViewModels<MainViewModel>(R.id.nav_graph_main) { factory }

    private lateinit var adapter: FilmAdapter

    override val layoutFragment: Int = R.layout.fragment_film

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApp).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FilmAdapter().apply {
            onItemListener = this@FavoriteFilmFragment
            binding.rvFilm.setHasFixedSize(true)
            binding.rvFilm.adapter = this
        }
        viewModel.filmFavorite.observe(viewLifecycleOwner, { handleStat(it) })
    }

    private fun handleStat(resource: Resource<PagedList<Film>>) {
        with(binding) {
            when (resource) {
                is Resource.Loading -> isLoading = true
                is Resource.Empty -> {
                    isLoading = false
                    activity?.toast(getString(R.string.empty_message))
                }
                is Resource.Success -> {
                    isLoading = false
                    resource.data.let { data -> adapter.submitList(data) }
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

    override fun onItemClick(model: Film) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.DATA_EXTRA, arrayListOf(R.id.detail_film, model.id))
        }
        requireActivity().startActivity(intent)
    }

}