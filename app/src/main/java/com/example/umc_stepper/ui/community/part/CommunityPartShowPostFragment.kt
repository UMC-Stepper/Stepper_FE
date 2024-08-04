package com.example.umc_stepper.ui.community.part

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityPartShowPostBinding
import com.example.umc_stepper.ui.MainActivity

class CommunityPartShowPostFragment : BaseFragment<FragmentCommunityPartShowPostBinding>(R.layout.fragment_community_part_show_post) {

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        updateMainToolbar()
    }

    private fun updateMainToolbar() {
        mainActivity.updateToolbarTitle("Community")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_toolbar_community_home)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_community_search)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_community_menu)
    }

}