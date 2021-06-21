package robert.pakpahan.jetpro1.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.navGraphViewModels
import robert.pakpahan.jetpro1.MyApp
import robert.pakpahan.jetpro1.R
import robert.pakpahan.jetpro1.base.BaseFragment
import robert.pakpahan.jetpro1.databinding.FragmentFavoriteBinding
import robert.pakpahan.jetpro1.viewmodel.ViewModelFactory
import robert.pakpahan.jetpro1.ui.FavoritePagerAdapter
import robert.pakpahan.jetpro1.ui.MainViewModel
import javax.inject.Inject

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    @Inject
    internal lateinit var factory: ViewModelFactory

    private val viewModel by navGraphViewModels<MainViewModel>(R.id.nav_graph_main) { factory }

    override val layoutFragment = R.layout.fragment_favorite

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApp).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewPager.adapter = FavoritePagerAdapter(requireContext(), childFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.by_name -> {
                item.isChecked = true
                viewModel.sorting(MainViewModel.Type.NAME);true
            }
            R.id.by_release -> {
                item.isChecked = true
                viewModel.sorting(MainViewModel.Type.RELEASE_DATA);true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}