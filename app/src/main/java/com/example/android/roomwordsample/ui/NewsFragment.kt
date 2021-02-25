package com.example.android.roomwordsample.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.roomwordsample.R
import com.example.android.roomwordsample.databinding.FragmentNewsBinding
import com.example.android.roomwordsample.network.apiNews.models.NewsHeadlines
import com.example.android.roomwordsample.ui.adapters.HeadlinesRecyclerViewAdapter
import com.example.android.roomwordsample.util.UtilMethods.isInternetAvailable
import com.example.android.roomwordsample.viewModels.TopStoriesViewModel
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialSharedAxis

class NewsFragment : Fragment() {

    private lateinit var dialogNoInternet: View
    private lateinit var layoutNews: View
    private lateinit var gifImage: ImageView
    private lateinit var headlinesRecyclerView: RecyclerView

    private val args: NewsFragmentArgs by navArgs()

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val newsViewModel: TopStoriesViewModel by lazy {
        ViewModelProvider(this).get(TopStoriesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_news,
            container,
            false
        )

        (context as AppCompatActivity).supportActionBar!!.title = getString(R.string.app_name)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        layoutNews = binding.layoutTopHeadlines
        dialogNoInternet = binding.dialogNoInternet
        gifImage = binding.noInternetImage
        headlinesRecyclerView = binding.headlinesRecyclerView

        observeNews()
        safelyLoadNews(args.searchWord)

    }

    private fun safelyLoadNews(searchWord: String) {
        if (!context?.let { isInternetAvailable(it.applicationContext) }!!) {
            layoutNews.visibility = View.GONE
            dialogNoInternet.visibility = View.VISIBLE
            Glide.with(this)
                .load(R.drawable.no_internet_sat)
                .into(gifImage)
            Toast.makeText(context, "Something wrong!", Toast.LENGTH_SHORT).show()
        } else {
            layoutNews.visibility = View.VISIBLE
            dialogNoInternet.visibility = View.GONE

            newsViewModel.getInternational(searchWord)
        }
    }

    private fun observeNews() {
        newsViewModel.liveData.observe(viewLifecycleOwner, {

            val myNewsList = mutableListOf<NewsHeadlines>()
            for (i in it.articles)
                if (!i.urlToImage.isNullOrEmpty()) {

                    myNewsList.add(
                        NewsHeadlines(
                            i.author,
                            i.source.id,
                            i.source.name,
                            i.title,
                            i.description,
                            i.url,
                            i.urlToImage,
                            i.publishedAt,
                            i.content
                        )
                    )
                }

            headlinesRecyclerView.adapter =
                HeadlinesRecyclerViewAdapter(requireContext(), myNewsList)

            /*
    Pointing at the selected news article when returning to this page is not possible if, like in
    this case, I want to always have a refreshed list of articles straight from the viewModel
             */
//        headlinesRecyclerView.adapter?.stateRestorationPolicy =
//            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        })

        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }

    }

}