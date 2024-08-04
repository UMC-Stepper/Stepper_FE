package com.example.umc_stepper.ui.community

import android.content.Context
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityIndexBinding
import com.example.umc_stepper.ui.MainActivity

class CommunityIndexFragment :BaseFragment<FragmentCommunityIndexBinding>(R.layout.fragment_community_index) {
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        updateToolbar()
        binding.commnunityIndexPostBtn.setOnClickListener {
            goCommunityIndexPost()
        }

        binding.commnunityIndexCommentBtn.setOnClickListener {
            goCommunityIndexComment()
        }

        binding.communityIndexScapBtn.setOnClickListener {
            goCommunityIndexScrap()
        }
    }
    private fun updateToolbar() {
        mainActivity.updateToolbarTitle("목록")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_toolbar_community_home)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_community_search)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_community_menu)
    }

    private fun goCommunityIndexPost(){
        findNavController().navigate(R.id.action_communityIndexFragment_to_communityIndexPostFragment)
    }

    private fun goCommunityIndexComment(){
        findNavController().navigate(R.id.action_communityIndexFragment_to_communityMyCommentsFragment)
    }

    private fun goCommunityIndexScrap(){
        findNavController().navigate(R.id.action_communityIndexFragment_to_communityMyScrapFragment)
    }
}