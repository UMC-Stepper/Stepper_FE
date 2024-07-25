package com.example.umc_stepper.ui.signup

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.FragmentRegister2Binding
import com.example.umc_stepper.domain.model.UserDto
import com.example.umc_stepper.utils.GlobalApplication
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

class SignUp2Activity : BaseActivity<FragmentRegister2Binding>(R.layout.fragment_register_2) {
    private lateinit var user: UserDto
    private var gson: Gson = Gson()
    private lateinit var galleryForResult: ActivityResultLauncher<Intent>

    override fun setLayout() {
        init()
    }

    private fun init(){
        initActivityResultLauncher()
        receiveUserData()
        setOnClickBtn()
    }

    //갤러리 런처 초기화
    private fun initActivityResultLauncher() {
        galleryForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val selectImageUrl = result.data?.data
                selectImageUrl?.let {
                    GlobalApplication.loadProfileImage(binding.fragmentRegister2ProfileFrameIv, it.toString())
                    binding.fragmentRegister2ProfileImgIv.visibility = View.GONE
                    user.profile_image = it.toString()
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
                user.weight = weight
                user.height = height
                //viewModel.provideUserDto...(서버로 userDto 담아서 보내기)

            }
            fragmentRegister2ProfileCardCv.setOnClickListener {
                openGallery()
            }
        }
    }

    //갤러리 열기
    private fun openGallery() {
        val galleryIntent = Intent()
            .setType("image/*")
            .setAction(Intent.ACTION_GET_CONTENT)
        galleryForResult.launch(Intent.createChooser(galleryIntent, "Select Picture"))
    }
}
