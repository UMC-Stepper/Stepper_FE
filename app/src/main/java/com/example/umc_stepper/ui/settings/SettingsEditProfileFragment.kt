package com.example.umc_stepper.ui.settings

import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentSettingsEditProfileBinding
import com.example.umc_stepper.ui.community.CommunityDialog
import com.example.umc_stepper.ui.community.CommunityDialogInterface
import com.example.umc_stepper.ui.community.CommunityRemoveInterface
import com.example.umc_stepper.utils.EditPageOutDialog

class SettingsEditProfileFragment : BaseFragment<FragmentSettingsEditProfileBinding>(R.layout.fragment_settings_edit_profile),
    CommunityDialogInterface,
    CommunityRemoveInterface {

    private lateinit var callback: OnBackPressedCallback
    private lateinit var editPageOutDialog: CommunityDialog

    override fun setLayout() {
        initSetting()
    }

    // 뒤로가기 콜백 설정 함수
    private fun addOnBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로 가기 버튼이 눌렸을 때 처리 동작
                showDialog("페이지에서 나가시면 작성하신 정보는 저장되지 않습니다.","나가기","계속 작성하기")
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun initSetting() {
        editEmail()
        editPhoneNumber()
        addOnBackPressedCallback()
    }

    private fun showDialog(title: String, btn1: String, btn2: String) {
        activity?.let {
            editPageOutDialog = CommunityDialog(title, btn1, btn2, this)
            editPageOutDialog.isCancelable = false
            editPageOutDialog.show(it.supportFragmentManager, "CommunityDialog")
        } ?: run {
            // activity가 null인 경우 예외 처리
            Log.e("SettingsEditProfileFragment", "Activity is null")
        }
    }

    private fun editEmail() {
        binding.fragmentSettingsEditEmailEt .addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString()
                if (email.isEmpty()) {
                    binding.fragmentSettingsEditEmailErrorTv.visibility = View.GONE
                }
                else if (isValidEmail(email)) {
                    binding.fragmentSettingsEditEmailErrorTv.visibility = View.GONE
                } else {
                    binding.fragmentSettingsEditEmailErrorTv.visibility = View.VISIBLE
                }
                activateButton()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // 이메일 유효성 체크 함수
    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && (email.endsWith(".com") || email.endsWith(".net"))
    }

    // 전화 번호 입력시 자동 하이픈(-) 추가 및 버튼 활성화 함수 텍스트 입력에 따라 계혹 호출
    private fun editPhoneNumber() {
        binding.fragmentSettingsEditPhoneNumberEt.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        binding.fragmentSettingsEditPhoneNumberEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                activateButton()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // 버튼 활성화
    private fun activateButton() {
        val email = binding.fragmentSettingsEditEmailEt.text.toString()
        val phoneNumber = binding.fragmentSettingsEditPhoneNumberEt.text.toString()

        if ((isValidEmail(email) || phoneNumber.length == 13) || (isValidEmail(email) && phoneNumber.length == 13) || (phoneNumber.length == 13)) {
            binding.fragmentSettingsEditCompleteBtn.setBackgroundResource(R.drawable.shape_rounded_square_purple700_60dp)
            binding.fragmentSettingsEditCompleteBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.White))
        } else {
            binding.fragmentSettingsEditCompleteBtn.setBackgroundResource(R.drawable.radius_corners_61dp_stroke_1)
            binding.fragmentSettingsEditCompleteBtn.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.Purple_Black_BG_2)
            binding.fragmentSettingsEditCompleteBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.Purple_Gray_300))
        }
    }

    override fun onRemove(pos: Int) {}

    override fun OnClickBtn1(btn1: String) {
        when(btn1) {
            "나가기" -> {
                editPageOutDialog.dismiss()
                val action = SettingsEditProfileFragmentDirections.actionSettingsEditProfileFragmentToSettingsShowProfileFragment()
                findNavController().navigateSafe(action.actionId)
            }
        }
    }

}