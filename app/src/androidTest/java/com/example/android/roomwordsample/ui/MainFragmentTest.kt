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
class MainFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

//    @Test
//    fun clickFabAddWord_navigateToWordFragment() {
//        val navController = mock(NavController::class.java)
//
//        launchFragmentInHiltContainer<MainFragment> {
//            Navigation.setViewNavController(requireView(), navController)
//        }
//
//        onView(withId(R.id.fab)).perform(click())
//
//        verify(navController).navigate(
//            MainFragmentDirections.actionMainFragmentToNewWordFragment()
//        )
//    }


}