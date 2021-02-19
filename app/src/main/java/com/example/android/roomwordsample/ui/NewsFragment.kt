package com.example.android.roomwordsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.roomwordsample.R
import com.example.android.roomwordsample.databinding.FragmentTopStoriesBinding
import com.example.android.roomwordsample.network.apiNews.NewsHeadlines
import com.example.android.roomwordsample.ui.adapters.HeadlinesRecyclerViewAdapter
import com.example.android.roomwordsample.util.UtilMethods.isInternetAvailable
import com.example.android.roomwordsample.viewModels.TopStoriesViewModel
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var dialogNoInternet: View
    private lateinit var layoutNews: View
    private lateinit var gifImage: ImageView
    private lateinit var headlinesRecyclerView: RecyclerView
    private lateinit var skeleton: Skeleton

    private val args: NewsFragmentArgs by navArgs()

    private var _binding: FragmentTopStoriesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TopStoriesViewModel by lazy {
        ViewModelProvider(this).get(TopStoriesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_top_stories,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutNews = binding.layoutTopHeadlines
        dialogNoInternet = binding.dialogNoInternet
        gifImage = binding.noInternetImage
        headlinesRecyclerView = binding.headlinesRecyclerView

        skeleton = headlinesRecyclerView.applySkeleton(
            R.layout.shimmer_top_headlines,
            10
        )
        skeleton.maskCornerRadius = 40F
        skeleton.shimmerDurationInMillis = 1500
        skeleton.showSkeleton()

        observeNews()
        checkInternet(args.searchWord)
//        getNews(args.searchWord)

    }

    private fun checkInternet(searchWord: String) {
        if (!context?.let { isInternetAvailable(it.applicationContext) }!!) {
            layoutNews.visibility = View.GONE
            dialogNoInternet.visibility = View.VISIBLE
            Glide.with(this)
                .load(R.drawable.no_internet_gif_2)
                .into(gifImage)
            Toast.makeText(context, "Something wrong!", Toast.LENGTH_SHORT).show()


        } else {
            layoutNews.visibility = View.VISIBLE
            dialogNoInternet.visibility = View.GONE

            viewModel.getInternational(searchWord)

        }
    }

//    private fun getNews(searchWord: String) {
//        viewModel.getInternational(searchWord)
//    }

    private fun observeNews() {
        viewModel.liveData.observe(viewLifecycleOwner, {

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
        })
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}