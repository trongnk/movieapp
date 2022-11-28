package com.trongnk.movieapp.view.list

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.trongnk.movieapp.R
import com.trongnk.movieapp.databinding.FragmentMovieListBinding
import com.trongnk.movieapp.model.SortType
import com.trongnk.movieapp.model.ViewState
import com.trongnk.movieapp.util.DividerItemDecorator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null

    private val viewModel by viewModels<MovieListViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        _binding?.lifecycleOwner = this
        _binding?.viewModel = viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolBar()
        setupRecyclerView()
        setupObservers()
        viewModel.loadMovieList()
    }

    private fun setupToolBar() {
        binding.toolbar.menu.clear()
        binding.toolbar.inflateMenu(R.menu.menu_movie_list)
        binding.toolbar.title = getString(R.string.movie_list_fragment_label)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sort -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setItems(R.array.sort_types) { dialog, which ->
                            viewModel.setSortType(if (which == 0) SortType.TITLE else SortType.RELEASED_DATE)
                        }
                        .show()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupObservers() {
        viewModel.moviesState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    binding.refreshLayout.isEnabled = false
                }
                is ViewState.Data -> {
                    (binding.recyclerView.adapter as MovieAdapter).submitList(it.data.toMutableList())
                    binding.refreshLayout.isRefreshing = false
                    binding.refreshLayout.isEnabled = true
                }
                else -> {
                    binding.refreshLayout.isRefreshing = false
                    binding.refreshLayout.isEnabled = true
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.loadMovieList()
        }
        val itemDivider = ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!
        val itemDecorator = DividerItemDecorator(itemDivider)
        binding.recyclerView.apply {
            addItemDecoration(itemDecorator)
            adapter = MovieAdapter(::onMovieClicked)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onMovieClicked(movie: MovieListViewModel.Movie) {
        findNavController().navigate(MovieListFragmentDirections.actionToMovieDetailFragment(movie.originalTitle))
    }
}