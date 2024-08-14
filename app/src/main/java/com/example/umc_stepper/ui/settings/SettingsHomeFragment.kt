package com.example.umc_stepper.ui.settings

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentSettingsHomeBinding
import com.example.umc_stepper.ui.community.CommunityDialog
import com.example.umc_stepper.ui.community.CommunityDialogInterface
import com.example.umc_stepper.ui.login.LoginActivity
import com.example.umc_stepper.utils.enums.DialogType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsHomeFragment : BaseFragment<FragmentSettingsHomeBinding>(R.layout.fragment_settings_home),
    CommunityDialogInterface {

    private lateinit var communityDialog: CommunityDialog
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

        binding.settingsMenu3LogoutIb.setOnClickListener {
            showDialog("로그아웃\n정말 하실 건가요?","로그아웃","취소")
        }

        binding.settingsMenu3DeleteAccountIb.setOnClickListener {
            findNavController().navigateSafe(R.id.action_settingsFragment_to_settingExitFragment)
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

    private fun showDialog(title:String, btn1: String, btn2:String){
        activity?.let{
            communityDialog= CommunityDialog(title,btn1,btn2,this)
            communityDialog.isCancelable=false
            communityDialog.show(it.supportFragmentManager,"communityDialog")
        }?: run{

        }
    }

    override fun OnClickBtn1(btn1: String, dialogType: DialogType?) {
        val intent = Intent(requireContext(),LoginActivity::class.java)
        startActivity(intent)
        // 회원탈퇴기능(서버에전송)
    }


}
