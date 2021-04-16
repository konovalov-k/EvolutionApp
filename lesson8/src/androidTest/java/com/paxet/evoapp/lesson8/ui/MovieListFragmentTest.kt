import android.view.View
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.paxet.evoapp.lesson8.R
import com.paxet.evoapp.lesson8.ui.fragments.movieslist.MovieViewHolder
import com.paxet.evoapp.lesson8.ui.fragments.movieslist.MoviesListFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.not
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.action.ViewActions.scrollTo
import com.paxet.evoapp.lesson8.data.network.tmdbapi.CastItem
import com.paxet.evoapp.lesson8.ui.activity.MainActivity
import com.paxet.evoapp.lesson8.ui.fragments.actors.ActorViewHolder
import com.paxet.evoapp.lesson8.ui.fragments.actors.ActorsAdapter

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class MovieListFragmentTest {
    private lateinit var navController: NavController
    private lateinit var activity: ActivityScenario<MainActivity>

    @Before
    fun launchActivity(){
        activity = launch(MainActivity::class.java)
        activity.onActivity {
            navController = it.findViewById<FragmentContainerView>(R.id.nav_host_fragment_container).findNavController()
        }
    }

   @Test
   fun checkWayToDetails() {
       runBlocking {
           Assert.assertEquals(navController.currentDestination?.id, R.id.moviesList)

           onView(withId(R.id.rv_movies_list))
               .check(matches(isDisplayed()))
               .check(matches(isEnabled()))

           delay(1_000)

           onView(withId(R.id.rv_movies_list))
               .perform(
                   RecyclerViewActions.actionOnItemAtPosition<MovieViewHolder>(
                       10,
                       scrollTo()
                   )
               )

           delay(3_000)

           onView(withId(R.id.rv_movies_list))
               .perform(
                   RecyclerViewActions.actionOnItemAtPosition<MovieViewHolder>(
                       0,
                       scrollTo()
                   )
               )

           delay(1_000)

           onView(withId(R.id.rv_movies_list))
               .perform(
                   RecyclerViewActions.actionOnItemAtPosition<MovieViewHolder>(
                       0,
                       click()
                   )
               )

           Assert.assertEquals(navController.currentDestination?.id, R.id.movieDetails)

           delay(1_000)

           onView(withId(R.id.rv_actors_list))
               .perform(scrollTo())

           delay(1_000)

           onView(withId(R.id.rv_actors_list))
               .perform(
                   RecyclerViewActions.actionOnItemAtPosition<MovieViewHolder>(
                       5,
                       scrollTo()
                   )
               )

           delay(3_000)

           activity.onActivity {
               it.finish()
           }
       }
   }
}