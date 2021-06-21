package robert.pakpahan.jetpro1

import android.app.Activity
import android.content.Context
import androidx.navigation.findNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.ViewPagerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import robert.pakpahan.jetpro1.R.id.*
import org.junit.*
import org.junit.runner.RunWith
import robert.pakpahan.jetpro1.utils.EspressoIdlingResource

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
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
    fun loadFilm() {
        scenarioRule.scenario.onActivity {
            Assert.assertEquals(context.getString(R.string.film), currentDes(it)?.label)
        }
        Espresso.onView(withId(bottom_navigation_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(fragment_film))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(rvFilm)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(menu_sort)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(menu_sort)).perform(ViewActions.click())
        Espresso.onView(withText(R.string.by_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withText(R.string.by_release))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withText(R.string.by_name)).perform(ViewActions.click())
    }

    @Test
    fun loadTvShow() {
        Espresso.onView(withId(fragment_tv)).perform(ViewActions.click())
        scenarioRule.scenario.onActivity {
            Assert.assertEquals(context.getString(R.string.tv_show), currentDes(it)?.label)
        }
        Espresso.onView(withId(bottom_navigation_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(fragment_tv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(rvTvShow)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(menu_sort)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(menu_sort)).perform(ViewActions.click())
        Espresso.onView(withText(R.string.by_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withText(R.string.by_release))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withText(R.string.by_release)).perform(ViewActions.click())
    }

    @Test
    fun loadFavoriteFilm() {
        Espresso.onView(withId(fragment_favorite)).perform(ViewActions.click())
        scenarioRule.scenario.onActivity {
            Assert.assertEquals(context.getString(R.string.favorite), currentDes(it)?.label)
        }
        Espresso.onView(withId(fragment_favorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(view_pager))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(rvFilm)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(menu_sort)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(menu_sort)).perform(ViewActions.click())
        Espresso.onView(withText(R.string.by_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withText(R.string.by_release))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withText(R.string.by_name)).perform(ViewActions.click())
    }

    @Test
    fun loadFavoriteTvShow() {
        Espresso.onView(withId(fragment_favorite)).perform(ViewActions.click())
        scenarioRule.scenario.onActivity {
            Assert.assertEquals(context.getString(R.string.favorite), currentDes(it)?.label)
        }
        Espresso.onView(withId(fragment_favorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(view_pager))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(view_pager)).perform(ViewPagerActions.scrollRight())
        Espresso.onView(withId(rvTvShow)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(menu_sort)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(menu_sort)).perform(ViewActions.click())
        Espresso.onView(withText(R.string.by_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withText(R.string.by_release))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withText(R.string.by_release)).perform(ViewActions.click())
    }
}