package com.lbc.app.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lbc.app.R
import com.lbc.app.adapter.AlbumsListAdapter
import com.lbc.app.adapter.EndlessRecyclerViewScrollListener
import com.lbc.app.databinding.FragmentAlbumsListBinding
import com.lbc.presentation.viewmodel.AlbumsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsListFragment : Fragment() {

    private var _binding: FragmentAlbumsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AlbumsListViewModel by viewModels()
    private val albumsListAdapter = AlbumsListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAlbums()
        albumsListAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        handleUiState()
        handleLoadingState()
        setupUi()
    }

    private fun setupUi() {
        binding.recyclerViewAlbumsList.apply {
            val linearLayoutManager = LinearLayoutManager(binding.root.context)
            layoutManager = linearLayoutManager
            adapter = albumsListAdapter
            setHasFixedSize(true)
             addOnScrollListener(object: EndlessRecyclerViewScrollListener(linearLayoutManager) {
                 override fun onLoadMore(page: Int) {
                     viewModel.loadMoreAlbums()
                 }
             })
        }
    }

    private fun handleLoadingState() {
        viewModel.loadingState.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.dkProgressCircular.visibility = View.VISIBLE
            } else {
                binding.progressBar.dkProgressCircular.visibility = View.GONE
            }
        }
    }

    private fun handleUiState() {
        viewModel.albumsListState.observe(viewLifecycleOwner) {
            val isError = it.errorMessage != null
            if (isError) {
                Snackbar.make(
                    binding.root,
                    it.errorMessage!!,
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(getString(R.string.retry)) { viewModel.getAlbums(refresh = true) }
                    .setActionTextColor(Color.WHITE)
                    .show()

                if (it.albumsList.isEmpty()) {
                    binding.recyclerViewAlbumsList.visibility = View.GONE
                    binding.textViewErrorMessage.text = it.errorMessage
                    binding.textViewErrorMessage.visibility = View.VISIBLE
                } else {
                    albumsListAdapter.submitList(it.albumsList.toMutableList())
                    binding.recyclerViewAlbumsList.visibility = View.VISIBLE
                    binding.textViewErrorMessage.visibility = View.GONE
                }
            } else {
                albumsListAdapter.submitList(it.albumsList.toMutableList())
                binding.recyclerViewAlbumsList.visibility = View.VISIBLE
                binding.textViewErrorMessage.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}