package com.example.umc_stepper.ui.community.savedcontents.scrap

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityMyScrapBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.ui.community.savedcontents.comments.CommunityMyCommentsFragmentDirections
import com.example.umc_stepper.utils.listener.ItemClickListener
import kotlinx.coroutines.launch

class CommunityMyScrapFragment : BaseFragment<FragmentCommunityMyScrapBinding>(R.layout.fragment_community_my_scrap),
    ItemClickListener {

    private lateinit var mainActivity: MainActivity
    private lateinit var myScrapAdapter: MyScrapAdapter
    private val communityViewModel: CommunityViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        initSettings()
    }

    private fun initSettings() {
        updateToolbar()
        initAdapter()
        observeViewModel()
    }

    private fun initAdapter() {
        myScrapAdapter = MyScrapAdapter(this)
        binding.fragmentCommunityMyScrapRv.adapter = myScrapAdapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                communityViewModel.getCommunityMyScraps()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                communityViewModel.communityMyScrapResponseItem.collect {
                    if (it.isSuccess) {
                        it.result?.forEach { _ ->
                            updateVisibility(true)
                        }
                        myScrapAdapter.submitList(it.result)
                    } else {
                        updateVisibility(false)
                    }
                }
            }
        }
    }

    private fun updateVisibility(show : Boolean) {
        if (show) {
            binding.fragmentCommunityMyScrapNoContentsTv.visibility = View.INVISIBLE
            binding.fragmentCommunityMyScrapRv.visibility = View.VISIBLE
        } else {
            binding.fragmentCommunityMyScrapNoContentsTv.visibility = View.VISIBLE
            binding.fragmentCommunityMyScrapRv.visibility = View.INVISIBLE
        }
    }

    private fun updateToolbar() {
        mainActivity.updateToolbarTitle("목록")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_toolbar_community_home)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_community_search)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_community_menu)
    }

    override fun onClick(item: Any) {
        val args = Bundle().apply {
            putString("postId", "$item")
        }
        val action = CommunityMyScrapFragmentDirections.actionCommunityMyScrapFragmentToCommunityWeeklyShowPostFragment()
        findNavController().navigateSafe(action.actionId, args)
    }

}
