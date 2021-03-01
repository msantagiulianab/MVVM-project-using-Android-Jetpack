package com.example.android.roomwordsample.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.roomwordsample.MainCoroutineRule
import com.example.android.roomwordsample.getOrAwaitValueTest
import com.example.android.roomwordsample.repositories.FakeWordRepository
import com.example.android.roomwordsample.util.Constants
import com.example.android.roomwordsample.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WordViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: WordViewModel

    @Before
    fun setup() {
        viewModel = WordViewModel(FakeWordRepository())
    }

//    @Test
//    fun


    @Test
    fun `insert Word item item with empty field, returns error`() {

        viewModel.insertWordItem("")

        val value = viewModel.insertWordItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert Word item that is too long, returns error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.insertWordItem(string)

        val value = viewModel.insertWordItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert Word item with valid input, returns success`() {
        viewModel.insertWordItem("name")

        val value = viewModel.insertWordItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}