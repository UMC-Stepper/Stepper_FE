package com.example.umc_stepper.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentSettingsShowProfileBinding

class SettingsShowProfileFragment : BaseFragment<FragmentSettingsShowProfileBinding>(R.layout.fragment_settings_show_profile) {

    override fun setLayout() {
        setButton()
    }

    private fun setButton() {
        binding.fragmentSettingsShowEditBtn.setOnClickListener {
            val action = SettingsHomeFragmentDirections.actionSettingsFragmentToSettingsShowProfileFragment()
            findNavController().navigate(action.actionId)
        }
    }

}