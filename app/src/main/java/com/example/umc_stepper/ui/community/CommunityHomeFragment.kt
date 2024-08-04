package com.example.umc_stepper.ui.community

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityHomeBinding
import com.example.umc_stepper.ui.MainActivity

class CommunityHomeFragment : BaseFragment<FragmentCommunityHomeBinding>(R.layout.fragment_community_home) {

    override fun setLayout() {
        setButton()
    }

    private fun setButton() {
        binding.fragmentCommunityHomeGoWeeklyIv.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToCommunityWeeklyHomeFragment()
            findNavController().navigate(action.actionId)
        }

        binding.fragmentCommunityHomePartTitleTv.setOnClickListener {
            val action = CommunityHomeFragmentDirections.actionCommunityHomeFragmentToWeeklyEditFragment()
            findNavController().navigate(action.actionId)
        }
    }

}