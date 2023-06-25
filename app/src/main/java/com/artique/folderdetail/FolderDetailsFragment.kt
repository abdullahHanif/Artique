package com.artique.folderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.artique.HomeActivity
import com.artique.R
import com.artique.common.launchAndRepeatWithViewLifecycle
import com.artique.databinding.FragmentFoldersDetailsBinding
import com.artique.folderdetail.adapters.FolderDetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FolderDetailsFragment : Fragment(R.layout.fragment_folders_details) {

    private val viewModel: FolderDetailsViewModel by viewModels()

    private var _binding: FragmentFoldersDetailsBinding? = null
    private val binding: FragmentFoldersDetailsBinding
        get() = _binding as FragmentFoldersDetailsBinding

    private val adapter = FolderDetailsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding =
            FragmentFoldersDetailsBinding.inflate(LayoutInflater.from(context), container, false)
                .also {
                    it.folderDetailsRecyclerView.adapter = adapter
                }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val args = FolderDetailsFragmentArgs.fromBundle(it)
            viewModel.handleArguments(args.albumContent)
            (requireActivity() as HomeActivity).updateToolbarTitle(args.albumContent.displayName)
        }

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