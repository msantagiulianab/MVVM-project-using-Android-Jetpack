package com.example.android.roomwordsample.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.roomwordsample.databinding.FragmentMarsOverviewBinding
import com.example.android.roomwordsample.ui.adapters.MarsPhotoGridAdapter
import com.example.android.roomwordsample.viewModels.MarsOverviewViewModel

class MarsOverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: MarsOverviewViewModel by lazy {
        ViewModelProvider(this).get(MarsOverviewViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMarsOverviewBinding.inflate(inflater)

//        val binding = GridViewItemBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.photosGrid.adapter = MarsPhotoGridAdapter(MarsPhotoGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    MarsOverviewFragmentDirections.actionMarsOverviewFragmentToMarsDetailFragment(it)
                )
                viewModel.displayPropertyDetailsComplete()
            }
        })
//
//        setHasOptionsMenu(true)
        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.overflow_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        viewModel.updateFilter(
//            when (item.itemId) {
//                R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
//                R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
//                else -> MarsApiFilter.SHOW_ALL
//            }
//        )
//        return true
//    }

}
