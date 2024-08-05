package com.example.umc_stepper.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentSettingsHomeBinding

class SettingsHomeFragment : BaseFragment<FragmentSettingsHomeBinding>(R.layout.fragment_settings_home) {

    override fun setLayout() {
        setOnClick()
    }

    // 화면 테스트 용
    private fun setOnClick() {
        binding.fragmentSettingHomeTv.setOnClickListener {
            val action = SettingsHomeFragmentDirections.actionSettingsFragmentToSettingsShowProfileFragment()
            findNavController().navigate(action.actionId)
        }
    }

}