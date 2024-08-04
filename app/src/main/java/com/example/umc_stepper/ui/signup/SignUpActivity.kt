package com.example.umc_stepper.ui.signup

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivitySignUpBinding
import com.example.umc_stepper.domain.model.request.UserDto
import com.google.gson.Gson

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private var isCheckedCertificationNumber = true // 인증 번호
    private var isAgreementChecked = false // 약관 동의
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun setLayout() {
        setEditText()
        onClickBtn()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    private fun onClickBtn() {
        nextAgreePage()
        confirmUserDto()
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

            override fun afterTextChanged(s: Editable?) {}
        })

    }

    private fun checkPwd() {
        val pwd = binding.signupPwdEt.text.toString()
        val pwdCheck = binding.signupPwdCheckEt.text.toString()
        val drawableOk = ContextCompat.getDrawable(this, R.drawable.ic_signup_pwd_check_ok)?.apply {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        }
        val drawableError =
            ContextCompat.getDrawable(this, R.drawable.ic_signup_pwd_check_error)?.apply {
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            }

        if (pwdCheck.isEmpty()) {
            binding.signupPwdErrorTv.visibility = View.GONE
            binding.signupPwdCheckEt.setCompoundDrawables(null, null, null, null)
        } else {
            if (pwd == pwdCheck) {
                binding.signupPwdErrorTv.visibility = View.GONE
                binding.signupPwdCheckEt.setCompoundDrawables(null, null, drawableOk, null)
            } else {
                binding.signupPwdErrorTv.visibility = View.VISIBLE
                binding.signupPwdCheckEt.setCompoundDrawables(null, null, drawableError, null)
            }
        }
    }

    private fun hideKeyboard(view: View, context: Context) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // 외부 터치시 키보드 숨기기, 포커스 제거
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        if (currentFocus is EditText) {
            currentFocus!!.clearFocus()
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun confirmUserDto() { //모아서 두번째 페이지로
        with(binding) {
            signupCompleteBtn.setOnClickListener {
                val nickname = signupNicknameEt.text.toString()
                val email = signupEmailEt.text.toString()
                val password = signupPwdEt.text.toString()
                val passwordCheck = signupPwdCheckEt.text.toString()

                if (isCheckedCertificationNumber
                    && nickname.isNotEmpty()
                    && email.isNotEmpty()
                    && password == passwordCheck
                    && isAgreementChecked
                ) {
                    val intent = Intent(this@SignUpActivity, SignUp2Activity::class.java)
                    val gson = Gson()
                    val userDto = gson.toJson(
                        UserDto(
                            nick_name = nickname,
                            email = email,
                            password = password,
                            community_alarm = "true",
                            email_agree = "true",
                            per_agree = "true",
                            use_agree = "true"
                        )
                    )
                    intent.putExtra("user", userDto)
                    startActivity(intent)
                } else {
                    if (!isCheckedCertificationNumber) {
                        makeToastMessage("인증 번호 확인 미완료")
                    } else if (nickname.isEmpty()) {
                        makeToastMessage("닉네임 확인 미완료")
                    } else if (email.isEmpty()) {
                        makeToastMessage("이메일 확인 미완료")
                    } else if (passwordCheck.isEmpty()) {
                        makeToastMessage("비밀번호 확인 미완료")
                    } else if (!isAgreementChecked) {
                        makeToastMessage("동의 미완료")
                    }
                }
            }
        }
    }

    private fun makeToastMessage(message: String) {
        Toast.makeText(this@SignUpActivity, message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun nextAgreePage() {
        startForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val result = result.data?.getBooleanExtra("agree_success", false)!!
                if (result) {
                    binding.signupTermsCheckIv.setBackgroundResource(R.drawable.selector_checked_on)
                    isAgreementChecked = true
                }
            }
        }
        binding.signupAgreeTv.setOnClickListener {
            val intent = Intent(this@SignUpActivity, RegisterAgreeActivity::class.java)
            startForResult.launch(intent)
        }
    }
}
