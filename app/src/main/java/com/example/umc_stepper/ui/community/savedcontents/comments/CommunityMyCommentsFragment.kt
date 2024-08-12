package com.example.umc_stepper.ui.community.savedcontents.comments

import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityMyCommentsBinding
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponseItem
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.utils.listener.ItemClickListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CommunityMyCommentsFragment : BaseFragment<FragmentCommunityMyCommentsBinding>(R.layout.fragment_community_my_comments),
    ItemClickListener {

    private lateinit var mainActivity: MainActivity
    private lateinit var myCommentsAdapter: MyCommentsAdapter
    private val communityViewModel : CommunityViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        updateToolbar()
        setAdapter()
        observeViewModel()

    }

    private fun setAdapter() {
        myCommentsAdapter = MyCommentsAdapter(this)
        binding.fragmentCommunityMyCommentsRv.adapter = myCommentsAdapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                communityViewModel.getCommunityMyComments()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                communityViewModel.communityMyCommentsResponseItem.collect {
                    it.result?.forEach { res ->
                        updateVisibility(res.title.isNotBlank())
                    }
                    myCommentsAdapter.submitList(it.result)
                }
            }
        }
    }

    private fun updateVisibility(show : Boolean) {
        if (show) {
            binding.fragmentCommunityMyCommentsNoContentsTv.visibility = View.INVISIBLE
            binding.fragmentCommunityMyCommentsRv.visibility = View.VISIBLE
        } else {
            binding.fragmentCommunityMyCommentsNoContentsTv.visibility = View.VISIBLE
            binding.fragmentCommunityMyCommentsRv.visibility = View.INVISIBLE
        }
    }

    private fun updateToolbar() {
        mainActivity.updateToolbarTitle("목록")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_toolbar_community_home)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_community_search)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_community_menu)
    }

    override fun onClick(item: Any) {
        // 글 클릭하면 해당 글로 이동 (API로 해당글 정보 받아서 넘겨줘야 함)
    }

}