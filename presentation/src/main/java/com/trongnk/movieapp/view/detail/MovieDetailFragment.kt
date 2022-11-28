package com.trongnk.movieapp.view.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.trongnk.movieapp.R
import com.trongnk.movieapp.databinding.FragmentMovieDetailBinding
import com.trongnk.movieapp.model.SortType
import com.trongnk.movieapp.util.Event
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null

    private val args: MovieDetailFragmentArgs by navArgs()

    private val viewModel by viewModels<MovieDetailViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        _binding?.lifecycleOwner = this
        _binding?.viewModel = viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolBar()

        viewModel.watchTrailerEvent.observe(viewLifecycleOwner,
            Event.Observer(
                this
            ) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(browserIntent)
            })

        binding.addButton.setOnClickListener {
            viewModel.updateWatchList()
        }

        binding.watchButton.setOnClickListener {
            viewModel.watchTrailer()
        }

        viewModel.loadMovieDetail(args.title)
    }

    private fun setupToolBar() {
        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

        binding.toolbar.setNavigationOnClickListener { view ->
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}