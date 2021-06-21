package robert.pakpahan.jetpro1.ui

import android.app.Activity
import android.content.Context
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.ViewPagerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import robert.pakpahan.jetpro1.MainActivity
import robert.pakpahan.jetpro1.R
import robert.pakpahan.jetpro1.R.id.*
import robert.pakpahan.jetpro1.data.datalayout.detail.Film
import robert.pakpahan.jetpro1.data.datalayout.detail.TvShow
import robert.pakpahan.jetpro1.utils.EspressoIdlingResource

class FavoriteFragmentTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val currentDes = { activity: Activity -> activity.findNavController(nav_host_main_fragment).currentDestination }

    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadFragmentFavoriteFilm() {
        val data = mutableListOf<Film>()
        Espresso.onView(withId(fragment_favorite)).perform(ViewActions.click())
        scenarioRule.scenario.onActivity {
            assertEquals(context.getString(R.string.favorite), currentDes(it)?.label)
        }
        Espresso.onView(withId(fragment_favorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(view_pager))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(rvFilm)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        scenarioRule.scenario.onActivity { activity ->
            val rvFilm: RecyclerView = activity.findViewById(R.id.rvFilm)
            (rvFilm.adapter as FilmAdapter).currentList?.map { data.add(it) }
        }
        if (data.size > 0) {
            assertNotNull(data)
            assertTrue(data.size > 0)
            Espresso.onView(withId(rvFilm))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(data.size))
            Espresso.onView(withId(rvFilm)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    ViewActions.click()
                ))
            Espresso.onView(withId(tvReadMore)).perform(ViewActions.click())
            Espresso.onView(withId(ivBack)).perform(ViewActions.click())
        }
    }

    @Test
    fun loadFragmentFavoriteTvShow() {
        val data = mutableListOf<TvShow>()
        Espresso.onView(withId(fragment_favorite)).perform(ViewActions.click())
        scenarioRule.scenario.onActivity {
            assertEquals(context.getString(R.string.favorite), currentDes(it)?.label)
        }
        Espresso.onView(withId(fragment_favorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(view_pager))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(view_pager)).perform(ViewPagerActions.scrollRight())
        Espresso.onView(withId(rvTvShow)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        scenarioRule.scenario.onActivity { activity ->
            val rvTvShow: RecyclerView = activity.findViewById(R.id.rvTvShow)
            (rvTvShow.adapter as TvShowAdapter).currentList?.map { data.add(it) }
        }
        if (data.size > 0) {
            assertNotNull(data)
            assertTrue(data.size > 0)
            Espresso.onView(withId(rvTvShow))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(data.size))
            Espresso.onView(withId(rvTvShow)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    ViewActions.click()
                ))
            Espresso.onView(withId(tvReadMore)).perform(ViewActions.click())
            Espresso.onView(withId(ivBack)).perform(ViewActions.click())
        }
    }
}