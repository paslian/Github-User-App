package com.example.navigationapi_pasliansahatrafael.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationapi_pasliansahatrafael.ui.viewmodel.DetailViewModel
import com.example.navigationapi_pasliansahatrafael.ui.adapter.FollowAdapter
import com.example.navigationapi_pasliansahatrafael.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View binding is not available yet")


    private var position: Int = 0
    private var username: String? = null

    private lateinit var adapter: FollowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
            username = arguments?.getString(ARG_USERNAME)
            arguments?.let {
                position = it.getInt(ARG_POSITION)
                username = it.getString(ARG_USERNAME)
            }

            adapter = FollowAdapter()
            binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())
            binding.rvFollow.adapter = adapter

            if (position == 1){
                viewModel.isLoading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }

                viewModel.followersList.observe(viewLifecycleOwner) { followers ->
                    adapter.submitList(followers)
                }
            } else {
                viewModel.isLoading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }

                viewModel.followingList.observe(viewLifecycleOwner) { following ->
                        adapter.submitList(following)
                }
            }

        }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "username"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}