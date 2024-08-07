package com.example.umc_stepper.ui.signup

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.FragmentRegister2Binding
import com.example.umc_stepper.domain.model.request.UserDto
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.login.LoginActivity
import com.example.umc_stepper.ui.login.LoginViewModel
import com.example.umc_stepper.utils.GlobalApplication
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUp2Activity : BaseActivity<FragmentRegister2Binding>(R.layout.fragment_register_2) {
    private lateinit var user: UserDto
    private var gson: Gson = Gson()
    private lateinit var galleryForResult: ActivityResultLauncher<Intent>
    private lateinit var loginViewModel: LoginViewModel

    override fun setLayout() {
        init()
    }

    private fun init() {
        initLoginViewModel()
        initActivityResultLauncher()
        observeLifeCycle()
        receiveUserData()
        setOnClickBtn()
        addTextWatcher()
    }

    private fun initLoginViewModel(){
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }
    //갤러리 런처 초기화
    private fun initActivityResultLauncher() {
        galleryForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val selectImageUrl = result.data?.data
                selectImageUrl?.let {
                    GlobalApplication.loadProfileImage(
                        binding.fragmentRegister2ProfileFrameIv,
                        it.toString()
                    )
                    binding.fragmentRegister2ProfileImgIv.visibility = View.GONE
                    user.profileImage = it.toString()
                }
            }
        }
    }

    private fun observeLifeCycle(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                loginViewModel.userData.collectLatest {
                    if(it.isSuccess){
                        startActivity(Intent(this@SignUp2Activity,LoginActivity::class.java))
                    }
                }
            }
        }

    }

    //이전 화면 에서 유저 데이터 받아 오기
    private fun receiveUserData() {
        try {
            intent.getStringExtra("user")?.let { userJson ->
                user = gson.fromJson(userJson, UserDto::class.java)
                Log.e("user", user.toString())
            }
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            Log.e("user", user.toString())
        }
    }

    //버튼 클릭
    private fun setOnClickBtn() {
        with(binding) {
            fragmentRegister2SuccessInputBt.setOnClickListener {
                // 성공 버튼 클릭 시 동작
                val height = fragmentRegister2HeightEt.text.toString()
                val weight = fragmentRegister2WeightEt.text.toString()
                confirmNumberText(height,fragmentRegister2HeightEt)
                confirmNumberText(weight,fragmentRegister2WeightEt)

                if(weight.isNotEmpty() && height.isNotEmpty()) {
                    user.weight = weight.toInt()
                    user.height = height.toInt()
                    loginViewModel.postSignUpInfo(user)
                }


            }
            fragmentRegister2ProfileCardCv.setOnClickListener {
                openGallery()
            }
        }
    }


    private fun confirmNumberText(confirmText: String, view: EditText) {
        try {
            confirmText.toInt()
        } catch (e: NumberFormatException) {
            view.setText("")
            Toast.makeText(this@SignUp2Activity,"숫자만 입력해주세요.",Toast.LENGTH_SHORT).show()
        }
    }
    //갤러리 열기
    private fun openGallery() {
        val galleryIntent = Intent()
            .setType("image/*")
            .setAction(Intent.ACTION_GET_CONTENT)
        galleryForResult.launch(Intent.createChooser(galleryIntent, "Select Picture"))
    }

    private fun addTextWatcher() {
        binding.fragmentRegister2WeightEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkWeightField()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.fragmentRegister2HeightEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkHeightField()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun checkWeightField() {
        val wTextField = binding.fragmentRegister2WeightEt.text.toString()
        val drawableOk = ContextCompat.getDrawable(this, R.drawable.ic_signup_pwd_check_ok)?.apply {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        }
        val drawableError =
            ContextCompat.getDrawable(this, R.drawable.ic_signup_pwd_check_error)?.apply {
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            }

        if (wTextField.isNotEmpty()) {
            binding.fragmentRegister2WeightEt.setCompoundDrawables(null, null, drawableOk, null)
        } else {
            binding.fragmentRegister2WeightEt.setCompoundDrawables(null, null, drawableError, null)
        }
    }

    private fun checkHeightField() {
        val hTextField = binding.fragmentRegister2HeightEt.text.toString()
        val drawableOk = ContextCompat.getDrawable(this, R.drawable.ic_signup_pwd_check_ok)?.apply {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        }
        val drawableError =
            ContextCompat.getDrawable(this, R.drawable.ic_signup_pwd_check_error)?.apply {
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            }

        if (hTextField.isNotEmpty()) {
            binding.fragmentRegister2HeightEt.setCompoundDrawables(null, null, drawableOk, null)
        } else {
            binding.fragmentRegister2HeightEt.setCompoundDrawables(null, null, drawableError, null)
        }
    }

}
