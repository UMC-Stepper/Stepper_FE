package com.example.umc_stepper.ui.settings


import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentSettingsShowProfileBinding
import com.example.umc_stepper.ui.login.LoginViewModel
import kotlinx.coroutines.launch

class SettingsShowProfileFragment : BaseFragment<FragmentSettingsShowProfileBinding>(R.layout.fragment_settings_show_profile) {

    private val loginViewModel : LoginViewModel by activityViewModels()

    override fun setLayout() {
        setButton()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.getUserInfo()
                loginViewModel.userInfo.collect {
                    privateGetUserInfo()
                }
            }
        }
    }

    private suspend fun privateGetUserInfo() {
        binding.userInfo = loginViewModel.userInfo.value.result
    }

    private fun setButton() {
        binding.fragmentSettingsShowEditBtn.setOnClickListener {
            val action = SettingsShowProfileFragmentDirections.actionSettingsShowProfileFragmentToSettingsEditProfileFragment()
            findNavController().navigate(action.actionId)
        }
    }

}