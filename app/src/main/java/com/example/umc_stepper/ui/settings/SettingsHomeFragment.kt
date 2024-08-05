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
        binding.settingsMenu1Ib.setOnClickListener {
            goSettingsShowProfile()
        }

        //스위치 텍스트 초기화
        updateToggleText(binding.settingsMenu2Switch.isChecked)

        // 스위치 리스너
        binding.settingsMenu2Switch.setOnCheckedChangeListener { _, isChecked ->
            updateToggleText(isChecked)
        }
    }

    private fun updateToggleText(isChecked: Boolean) {
        binding.settingsMenu2ToggleTv.text = if (isChecked) "On" else "off"
    }

    private fun goSettingsShowProfile() {
        findNavController().navigate(R.id.action_settingsFragment_to_settingsShowProfileFragment)
    }

    //추후 탈퇴하기 버튼 연결
    private fun goSettings() {
        // findNavController().navigate(R.id.action_additionalExerciseHomeFragment_to_fragmentAddExerciseDownload)
    }
}
