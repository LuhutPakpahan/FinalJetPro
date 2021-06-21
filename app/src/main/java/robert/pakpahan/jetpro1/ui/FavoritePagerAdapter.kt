package robert.pakpahan.jetpro1.ui

import android.content.Context
import robert.pakpahan.jetpro1.ui.FavoriteTvShowFragment
import robert.pakpahan.jetpro1.ui.FavoriteFilmFragment
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import robert.pakpahan.jetpro1.R
import robert.pakpahan.jetpro1.ui.FavoriteFilmFragment.Companion.newInstance


class FavoritePagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> FavoriteFilmFragment.newInstance(position + 1)
            else -> FavoriteTvShowFragment.newInstance(position + 1)
        }

    override fun getPageTitle(position: Int): CharSequence =
        mContext.resources.getString(TAB_TITLES[position])

    override fun getCount(): Int = TAB_TITLES.size

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.film,
            R.string.tv_show
        )
    }
}
