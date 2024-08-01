package com.example.umc_stepper.ui.signup

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivityRegisterAgreeBinding

class RegisterAgreeActivity :
    BaseActivity<ActivityRegisterAgreeBinding>(R.layout.activity_register_agree) {
    private var isCheckedTermsOfUse = false
    private var isCheckedPrivacyPermission = false
    private var isConfirmBtnState = false

    override fun setLayout() {
        onClickBtn()
    }

    //버튼 클릭 시 호출
    private fun onClickBtn() {
        with(binding) {

            //두 가지 모두 선택 되었을 경우 버튼 활성화
            fragmentRegisterAgreeBtn.setOnClickListener {
                setCheckedState(fragmentRegisterAgree2Btn, true)
                setCheckedState(fragmentRegisterAgree4Btn, true)
                isCheckedTermsOfUse = true
                isCheckedPrivacyPermission = true
                confirmState()
            }

            //이용 약관 동의
            fragmentRegisterAgree2Btn.setOnClickListener {
                isCheckedTermsOfUse = toggleState(it, isCheckedTermsOfUse)
                confirmState()
            }

            //개인 정보 수집 및 동의
            fragmentRegisterAgree4Btn.setOnClickListener {
                isCheckedPrivacyPermission = toggleState(it, isCheckedPrivacyPermission)
                confirmState()
            }

            //확인 버튼 클릭 시 값을 가지고 돌아감
            registerConfirmBtn.setOnClickListener {
                resultIntent()
            }
        }
    }

    //상태 값 토글
    private fun toggleState(view: View, currentState: Boolean): Boolean {
        val newState = !currentState
        setCheckedState(view, newState)
        return newState
    }

    //버튼 상태 체크 후 이미지 토글
    private fun setCheckedState(view: View, state: Boolean) {
        val backgroundResource = if (state) {
            R.drawable.selector_checked_on
        } else {
            R.drawable.selector_checked_off
        }
        view.setBackgroundResource(backgroundResource)
    }

    //체크된 값들을 가지고 돌아감
    private fun resultIntent() {
        val intent = Intent(this, SignUpActivity::class.java).apply {
            putExtra("agree_success", true)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    //모두 선택 시 완료 버튼 활성화
    private fun confirmState() {
        if (isCheckedTermsOfUse && isCheckedPrivacyPermission) {
            isConfirmBtnState = true
            with(binding.registerConfirmBtn) {
                setBackgroundResource(R.drawable.shape_rounded_square_purple700_60dp)
                isEnabled = true
                setTextColor(ContextCompat.getColor(context, R.color.White))
            }
        } else {
            isConfirmBtnState = false
            with(binding.registerConfirmBtn) {
                setBackgroundResource(R.drawable.selector_confirm_off)
                isEnabled = false
                setTextColor(ContextCompat.getColor(context, R.color.Gray_Purple))
            }
        }
    }


}
