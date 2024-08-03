package com.example.umc_stepper.ui.community.savedcontents

import android.content.Context
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityMyScrapBinding
import com.example.umc_stepper.ui.MainActivity

class CommunityMyScrapFragment : BaseFragment<FragmentCommunityMyScrapBinding>(R.layout.fragment_community_my_scrap) {

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
