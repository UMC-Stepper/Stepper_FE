package com.example.umc_stepper.ui.signup

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    override fun setLayout() {
        setEditText()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    private fun setEditText() {

        // 닉네임
        binding.signupNicknameEt.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard(binding.signupNicknameEt, this)
            }
            false
        }

        // 이메일
        binding.signupEmailEt.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard(binding.signupEmailEt, this)
            }
            false
        }

        // 인증번호
        binding.signupCertNumberEt.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard(binding.signupCertNumberEt, this)
            }
            false
        }

        // 비밀번호
        binding.signupPwdEt.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard(binding.signupPwdEt, this)
            }
            false
        }

        //비밀번호 확인
        binding.signupPwdCheckEt.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard(binding.signupPwdCheckEt, this)
            }
            false
        }

        // 비밀번호 확인 EditText에 TextWatcher 추가
        binding.signupPwdCheckEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkPwd()
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })

    }

    private fun checkPwd() {
        val pwd = binding.signupPwdEt.text.toString()
        val pwdCheck = binding.signupPwdCheckEt.text.toString()
        val drawableOk = ContextCompat.getDrawable(this, R.drawable.ic_signup_pwd_check_ok)?.apply {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        }
        val drawableError = ContextCompat.getDrawable(this, R.drawable.ic_signup_pwd_check_error)?.apply {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        }

        if (pwd == pwdCheck) {
            binding.signupPwdErrorTv.visibility = View.GONE
            binding.signupPwdCheckEt.setCompoundDrawables(null, null, drawableOk, null)
        } else {
            binding.signupPwdErrorTv.visibility = View.VISIBLE
            binding.signupPwdCheckEt.setCompoundDrawables(null, null, drawableError, null)        }
    }

    private fun hideKeyboard(view: View, context: Context) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // 외부 터치시 키보드 숨기기, 포커스 제거
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        if(currentFocus is EditText) {
            currentFocus!!.clearFocus()
        }

        return super.dispatchTouchEvent(ev)
    }
}