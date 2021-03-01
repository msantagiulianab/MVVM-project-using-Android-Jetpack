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
class WordFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

//    @Test
//    fun pressBackButton_popBackStack() {
//        val navController = mock(NavController::class.java)
//        launchFragmentInHiltContainer<WordFragment> {
//            Navigation.setViewNavController(requireView(), navController)
//        }
//
//        pressBack()
//
//        verify(navController).popBackStack()
//    }
//
//    @Test
//    fun clickSaveButton_navigateToMainFragment() {
//        val navController = mock(NavController::class.java)
//
//        launchFragmentInHiltContainer<WordFragment> {
//            Navigation.setViewNavController(requireView(), navController)
//        }
//
//        Espresso.onView(ViewMatchers.withId(R.id.button_save)).perform(ViewActions.click())
//
//        verify(navController).navigate(
//            R.id.action_newWordFragment_to_mainFragment, null
//        )
//    }

}