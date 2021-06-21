package robert.pakpahan.jetpro1.ui

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.After
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import robert.pakpahan.jetpro1.R
import robert.pakpahan.jetpro1.ui.DetailActivity.Companion.DATA_EXTRA
import robert.pakpahan.jetpro1.utils.DummyData
import robert.pakpahan.jetpro1.utils.EspressoIdlingResource

@RunWith(AndroidJUnit4ClassRunner::class)
class DetailActivityTest {
    private lateinit var scenarioRule: ActivityScenario<DetailActivity>
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadDetailFilm() {
        val data = DummyData.getDetailFilm()
        scenarioRule = ActivityScenario.launch(
            Intent(context, DetailActivity::class.java).apply {
                putExtra(DATA_EXTRA, arrayListOf(R.id.detail_film, data.id))
            }
        )
        Espresso.onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvTitle)).check(ViewAssertions.matches(withText(data.title)))
        Espresso.onView(withId(R.id.tvScore))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvReleaseDate))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvReleaseDate))
            .check(ViewAssertions.matches(ViewMatchers.withText("Release on : ${data.release_date}")))
        Espresso.onView(withId(R.id.tvOverview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvOverview))
            .check(ViewAssertions.matches(withText(data.overview)))
        Espresso.onView(withId(R.id.ivPoster))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.ivBackground))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvReadMore)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.ivFavorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.ivFavorite)).perform(ViewActions.click())
        Espresso.onView(withText(R.string.ok))
            .check(ViewAssertions.matches(ViewMatchers.withText("OK")))
        Espresso.onView(withText(R.string.cancel))
            .check(ViewAssertions.matches(ViewMatchers.withText("Cancel")))
        Espresso.onView(ViewMatchers.withText("Cancel")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.ivBack)).perform(ViewActions.click())
    }

    @Test
    fun loadDetailTvShow() {
        val data = DummyData.getDetailTvShow()
        scenarioRule = ActivityScenario.launch(
            Intent(context, DetailActivity::class.java).apply {
                putExtra(DATA_EXTRA, arrayListOf(R.id.detail_tv_show, data.id))
            }
        )
        Espresso.onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(withText(data.original_name)))
        Espresso.onView(withId(R.id.tvScore))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvReleaseDate))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvReleaseDate))
            .check(ViewAssertions.matches(ViewMatchers.withText("Release on : ${data.first_air_date}")))
        Espresso.onView(withId(R.id.tvOverview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvOverview))
            .check(ViewAssertions.matches(withText(data.overview)))
        Espresso.onView(withId(R.id.ivPoster))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.ivBackground))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvReadMore)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.ivFavorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.ivFavorite)).perform(ViewActions.click())
        Espresso.onView(withText(R.string.ok))
            .check(ViewAssertions.matches(ViewMatchers.withText("OK")))
        Espresso.onView(withText(R.string.cancel))
            .check(ViewAssertions.matches(ViewMatchers.withText("Cancel")))
        Espresso.onView(ViewMatchers.withText("Cancel")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.ivBack)).perform(ViewActions.click())
    }
}