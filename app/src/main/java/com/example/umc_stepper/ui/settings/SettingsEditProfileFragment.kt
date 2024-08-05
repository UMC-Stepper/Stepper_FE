package com.example.umc_stepper.ui.settings

import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
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
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentSettingsEditProfileBinding
import com.example.umc_stepper.utils.EditPageOutDialog

class SettingsEditProfileFragment : BaseFragment<FragmentSettingsEditProfileBinding>(R.layout.fragment_settings_edit_profile) {

    private lateinit var callback: OnBackPressedCallback
    private lateinit var editPageOutDialog: EditPageOutDialog

    override fun setLayout() {
        initSetting()
    }

    private fun initSetting() {
        editPageOutDialog = EditPageOutDialog()
        //editPageOutDialog.show(parentFragmentManager, "SearchLoadingDialog")
        editEmail()
        editPhoneNumber()
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

}