package com.example.android.roomwordsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.roomwordsample.R
import com.example.android.roomwordsample.databinding.FragmentMarsDetailBinding
import com.example.android.roomwordsample.viewModels.MarsDetailViewModel
import com.example.android.roomwordsample.viewModels.MarsDetailViewModelFactory

class MarsDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        val binding = FragmentMarsDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val marsProperty = MarsDetailFragmentArgs.fromBundle(requireArguments()).selectedProperty
        val viewModelFactory = MarsDetailViewModelFactory(marsProperty, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(MarsDetailViewModel::class.java)

        (context as AppCompatActivity).supportActionBar!!.title = getString(R.string.app_name)

        return binding.root
    }
}