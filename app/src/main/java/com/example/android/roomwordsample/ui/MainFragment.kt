package com.example.android.roomwordsample.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import com.example.android.roomwordsample.R
import com.example.android.roomwordsample.WordViewModel
import com.example.android.roomwordsample.WordViewModelFactory
import com.example.android.roomwordsample.application.WordsApplication
import com.example.android.roomwordsample.databinding.FragmentMainBinding
import com.example.android.roomwordsample.ui.adapters.WordListAdapter
import com.example.android.roomwordsample.util.UtilMethods.hideKeyboard
import com.example.android.roomwordsample.util.themeColor
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialSharedAxis
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import timber.log.Timber
import kotlin.math.abs

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((activity?.application as WordsApplication).repository)
    }

    private var editTextBox: EditText? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        (context as AppCompatActivity).supportActionBar!!.title = getString(R.string.app_name)

        val myCallbackRight = simpleCallbackRightSwipe()
        val myHelperRight = ItemTouchHelper(myCallbackRight)
        myHelperRight.attachToRecyclerView(binding.recyclerview)

        val myCallbackLeft = simpleCallbackLeftSwipe()
        val myHelperLeft = ItemTouchHelper(myCallbackLeft)
        myHelperLeft.attachToRecyclerView(binding.recyclerview)

        val gesture = gestureDetectorMainFragment()
        binding.recyclerview.setOnTouchListener { _, event -> gesture.onTouchEvent(event) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WordListAdapter(
            wordViewModel
        )

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        wordViewModel.allWords.observe(viewLifecycleOwner, { words ->
            words?.let {
                adapter.submitList(it)
            }
        })

        binding.fab.setOnClickListener {

            findNavController().currentDestination?.id.apply {
                exitTransition = MaterialElevationScale(false).apply {
                    duration = resources.getInteger(R.integer.motion_duration_large).toLong()
                }
                reenterTransition = MaterialElevationScale(true).apply {
                    duration = resources.getInteger(R.integer.motion_duration_large).toLong()
                }
            }

            findNavController().navigate(R.id.action_mainFragment_to_newWordFragment)
            hideKeyboard(view)
        }

        binding.fabDelete.setOnClickListener {
            showDialog()
            hideKeyboard(view)
        }

        editTextBox = binding.editTextNews

        binding.fabNetwork.setOnClickListener {

            val searchWord: String =
                if (editTextBox!!.text.isNotBlank() && editTextBox!!.text.isNotEmpty()) {
                    editTextBox!!.text.toString()
                } else {
                    "international"
                }

            editTextBox!!.text.clear()

            findNavController().currentDestination?.id.apply {
                exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
                    duration = resources.getInteger(R.integer.motion_duration_large).toLong()
                }
                reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
                    duration = resources.getInteger(R.integer.motion_duration_large).toLong()
                }
            }

            val action = MainFragmentDirections.actionMainFragmentToNewsFragment(searchWord)
            Toast.makeText(context, "News: $searchWord", Toast.LENGTH_SHORT).show()
            findNavController().navigate(action)
            hideKeyboard(view)

        }

        enterTransition = MaterialContainerTransform().apply {
            startView = requireActivity().findViewById(R.id.word_card_view)
            endView = requireActivity().findViewById(R.id.fab)
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            containerColor = requireContext().themeColor(R.attr.colorSurface)
            startContainerColor = requireContext().themeColor(R.attr.colorSecondary)
            endContainerColor = requireContext().themeColor(R.attr.colorSurface)
        }
        returnTransition = Slide().apply {
            duration = resources.getInteger(R.integer.motion_duration_medium).toLong()
            addTarget(R.id.fab)
        }

    }

    private fun showDialog() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("The database will be cleared")
        builder.setMessage("Are you sure?")

        builder.setPositiveButton("OK") { dialog, _ ->
            wordViewModel.deleteAll()
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun gestureDetectorMainFragment() = GestureDetector(
        activity,
        object : SimpleOnGestureListener() {
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

                    } else if (e2.x - e1.x > swipeMinDistance
                        && abs(velocityX) > swipeThresholdVelocity
                    ) {
                        Timber.i("Left to Right")
                        //                            Toast.makeText(context, "right", Toast.LENGTH_SHORT).show()

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return super.onFling(e1, e2, velocityX, velocityY)
            }
        })

    private fun simpleCallbackRightSwipe() = object : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val versionName =
                wordViewModel.allWords.value!![viewHolder.absoluteAdapterPosition].word
            Toast.makeText(
                viewHolder.itemView.context,
                "$versionName was DELETED",
                Toast.LENGTH_SHORT
            ).show()
            wordViewModel.deleteItem(wordViewModel.allWords.value!![viewHolder.absoluteAdapterPosition])
            hideKeyboard(viewHolder.itemView)

        }

        //            val trashBinIcon = ResourcesCompat.getDrawable(
        //                resources,
        //                R.drawable.ic_baseline_delete_24,
        //                null
        //            )

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {

            RecyclerViewSwipeDecorator.Builder(
                context,
                c,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
                .addBackgroundColor(
                    ContextCompat.getColor(
                        context as AppCompatActivity,
                        android.R.color.holo_red_dark
                    )
                )
                .addActionIcon(R.drawable.ic_baseline_delete_24)
                .create()
                .decorate()

            super.onChildDraw(
                c, recyclerView, viewHolder,
                dX, dY, actionState, isCurrentlyActive
            )

            //                c.clipRect(
            //                    0f, viewHolder.itemView.top.toFloat(),
            //                    dX, viewHolder.itemView.bottom.toFloat()
            //                )
            //
            //                if (dX < c.width / 4)
            //                    c.drawColor(Color.GRAY)
            //                else c.drawColor(Color.RED)
            //
            //                val textMargin = resources.getDimension(R.dimen.text_margin)
            //                    .roundToInt()
            //
            //                if (trashBinIcon != null) {
            //                    trashBinIcon.bounds = Rect(
            //                        textMargin,
            //                        viewHolder.itemView.top + textMargin,
            //                        textMargin + trashBinIcon.intrinsicWidth,
            //                        viewHolder.itemView.top + trashBinIcon.intrinsicHeight
            //                                + textMargin
            //                    )
            //                }
            //                trashBinIcon?.draw(c)

        }
    }

    private fun simpleCallbackLeftSwipe() = object : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val searchWord: String =
                wordViewModel.allWords.value!![viewHolder.absoluteAdapterPosition].word
            val action = MainFragmentDirections.actionMainFragmentToNewsFragment(searchWord)
            Toast.makeText(
                viewHolder.itemView.context,
                "News: $searchWord",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(action)

        }

        //            val searchMagnifierIcon = ResourcesCompat.getDrawable(
        //                resources,
        //                R.drawable.ic_baseline_search_24,
        //                null
        //            )

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {

            RecyclerViewSwipeDecorator.Builder(
                context,
                c,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
                .addBackgroundColor(
                    ContextCompat.getColor(
                        context as AppCompatActivity,
                        android.R.color.holo_purple
                    )
                )
                .addActionIcon(R.drawable.ic_baseline_search_24)
                .create()
                .decorate()

            super.onChildDraw(
                c, recyclerView, viewHolder,
                dX, dY, actionState, isCurrentlyActive
            )

            //                c.clipRect(
            //                    dX, viewHolder.itemView.top.toFloat(),
            //                    0f, viewHolder.itemView.bottom.toFloat()
            //                )
            //
            //                if (dX > c.width / 4)
            //                    c.drawColor(Color.GRAY)
            //                else c.drawColor(Color.GREEN)
            //
            //                val textMargin = resources.getDimension(R.dimen.text_margin)
            //                    .roundToInt()
            //
            //                if (searchMagnifierIcon != null) {
            //                    searchMagnifierIcon.bounds = Rect(
            //
            //                        viewHolder.itemView.right - textMargin - searchMagnifierIcon.intrinsicWidth,
            //                        viewHolder.itemView.top + textMargin,
            //                        viewHolder.itemView.right - textMargin,
            //                        viewHolder.itemView.top + searchMagnifierIcon.intrinsicHeight
            //                                + textMargin
            //
            ////                        viewHolder.itemView.right - textMargin - searchMagnifierIcon.intrinsicWidth,
            ////                        viewHolder.itemView.top + textMargin,
            ////                        viewHolder.itemView.right - textMargin,
            ////                        viewHolder.itemView.top + searchMagnifierIcon.intrinsicHeight
            ////                                + textMargin
            //                    )
            //                }
            //                searchMagnifierIcon?.draw(c)

        }
    }

}
