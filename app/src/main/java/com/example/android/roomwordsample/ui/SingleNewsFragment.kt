package com.example.android.roomwordsample.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.android.roomwordsample.R
import com.example.android.roomwordsample.databinding.FragmentSingleNewsBinding
import com.example.android.roomwordsample.util.themeColor
import com.google.android.material.transition.MaterialContainerTransform
import timber.log.Timber
import kotlin.math.abs

class SingleNewsFragment : Fragment() {

    private val args: SingleNewsFragmentArgs by navArgs()

    private var _binding: FragmentSingleNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.my_nav_host_fragment
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_single_news,
            container,
            false
        )

        val gestureSingleNews = gestureDetectorSingleNewsFragment()

        binding.newsCardView.setOnTouchListener { _, event -> gestureSingleNews.onTouchEvent(event) }

        (context as AppCompatActivity).supportActionBar!!.title = getString(R.string.app_name)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(args.urlImage)
            .placeholder(R.drawable.index)
            .into(binding.imageNews)

        binding.contentNews.text = args.content

        binding.showFullArticle.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(args.urlArticle))
            startActivity(intent)
        }
    }

    private fun gestureDetectorSingleNewsFragment() = GestureDetector(
        activity,
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onFling(
                e1: MotionEvent, e2: MotionEvent, velocityX: Float,
                velocityY: Float
            ): Boolean {
                Timber.i("onFling has been called!")
                val swipeMinDistance = 120
                val swipeMaxOffPath = 250
                val swipeThresholdVelocity = 200

                try {
                    if (abs(e1.y - e2.y) > swipeMaxOffPath) return false
                    if (e1.x - e2.x > swipeMinDistance
                        && abs(velocityX) > swipeThresholdVelocity
                    ) {
                        Timber.i("Right to Left")
                        //                            Toast.makeText(context, "left", Toast.LENGTH_SHORT).show()
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(args.urlArticle))
                        startActivity(intent)
                    } else if (e2.x - e1.x > swipeMinDistance
                        && abs(velocityX) > swipeThresholdVelocity
                    ) {
                        Timber.i("Left to Right")
                        //                            Toast.makeText(context, "right", Toast.LENGTH_SHORT).show()
                        //                            activity?.supportFragmentManager?.popBackStack()
                    }
                } catch (e: Exception) {
                    // nothing
                }
                return super.onFling(e1, e2, velocityX, velocityY)
            }
        })

}