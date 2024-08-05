package com.example.umc_stepper.ui.community.savedcontents.comments

import android.content.Context
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityMyCommentsBinding
import com.example.umc_stepper.ui.MainActivity

class CommunityMyCommentsFragment : BaseFragment<FragmentCommunityMyCommentsBinding>(R.layout.fragment_community_my_comments) {

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        updateToolbar()
    }

    private fun updateToolbar() {
        mainActivity.updateToolbarTitle("목록")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_toolbar_community_home)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_community_search)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_community_menu)
    }

}