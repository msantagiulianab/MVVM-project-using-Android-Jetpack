package com.example.android.roomwordsample.ui

import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class NewsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

//    @Test
//    fun clickArticle_navigateToSingleNewsFragment() {
//        val navController = mock(NavController::class.java)
//
//        launchFragmentInHiltContainer<NewsFragment> {
//            Navigation.setViewNavController(requireView(), navController)
//        }
//
//        onView(ViewMatchers.withId(R.id.headlines_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()))
//
//        Mockito.verify(navController).navigate(
//            MainFragmentDirections.actionMainFragmentToNewWordFragment()
//        )
//    }
}