package com.artique.folders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.artique.R
import com.artique.common.launchAndRepeatWithViewLifecycle
import com.artique.databinding.FragmentFoldersBinding
import com.artique.folders.adapters.FoldersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FoldersFragment : Fragment(R.layout.fragment_folders) {

    private val viewModel: FoldersViewModel by viewModels()

    private var _binding: FragmentFoldersBinding? = null
    private val binding: FragmentFoldersBinding
        get() = _binding as FragmentFoldersBinding

    lateinit var adapter: FoldersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = FoldersAdapter {
            findNavController().navigate(
                FoldersFragmentDirections.actionFolderFragmentToFolderDetailsFragment(
                    it
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding =
            FragmentFoldersBinding.inflate(LayoutInflater.from(context), container, false).also {
                it.foldersRecyclerview.adapter = adapter
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchAndRepeatWithViewLifecycle {
            viewModel.folders.collectLatest { state ->
                adapter.list = state.listing
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}