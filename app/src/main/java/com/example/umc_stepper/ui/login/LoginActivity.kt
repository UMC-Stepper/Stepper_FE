package com.example.umc_stepper.ui.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivityLoginBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.signup.SignUpActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login),
    ConfirmDialogInterface {
    lateinit var loginViewModel: LoginViewModel
    lateinit var agreeDialog: AgreeDialog
    private lateinit var requestMultiplePermissionsLauncher: ActivityResultLauncher<Array<String>>

    override fun setLayout() {
        initPermissionLaunchers()
        checkPermissionsAndProceed()
        setting()
    }

    private fun setting() {
        setViewModel()
        barTransparent()
        onClicked()
        goHome()
    }

    private fun initPermissionLaunchers() {
        requestMultiplePermissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                handlePermissionsResult(permissions)
            } else {
                handleLegacyPermissionsResult(permissions)
            }
        }
    }

    private fun checkPermissionsAndProceed() {
        if (!areAllPermissionsGranted()) {
            showDialog()
        }
    }

    //권한 확인
    private fun areAllPermissionsGranted(): Boolean {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
    }


    private fun setViewModel() {
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private fun goHome() {
        binding.activityLoginStepperIv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun barTransparent() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun onClicked() {
        with(binding) {
            activityLoginBtn.setOnClickListener {
                val email = activityLoginEmailEt.text.toString()
                val password = activityLoginPasswordEt.text.toString()
                //전송 api 호출 loginViewModel.getUser(email,password)
            }

            activityRegisterBtn.setOnClickListener {
                goSignUp()
            }
        }
    }

    private fun requestForUserData() { //응답
        with(binding) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    loginViewModel.userData.collectLatest { response ->
                        loginViewModel.updateUser(response) //유저 업데이트
                        goHome()
                        finish()
                    }
                }
            }
        }
    }

    //다이얼 로그 표시
    private fun showDialog() {
        agreeDialog = AgreeDialog(this@LoginActivity)

        with(agreeDialog) {
            isCancelable = false
            show(this@LoginActivity.supportFragmentManager, "AgreeDialog")
        }
    }

    //다이얼로그 확인 버튼
    override fun onClickConfirmButton(ok: Boolean) {
        if (ok) {
            askAllPermissions()
        }
    }

    //권한 요청 생성
    private fun askAllPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

        requestMultiplePermissionsLauncher.launch(permissions)
    }

    //android 13 버전 이상
    private fun handlePermissionsResult(permissions: Map<String, Boolean>) {
        if (permissions[Manifest.permission.POST_NOTIFICATIONS] == true) {
            Snackbar.make(binding.root, "알림 권한을 허용하였습니다.", Snackbar.LENGTH_SHORT).show()
        }
        if (permissions[Manifest.permission.CAMERA] == true) {
            Snackbar.make(binding.root, "카메라 권한을 허용하였습니다.", Snackbar.LENGTH_SHORT).show()
        }
        if (permissions[Manifest.permission.READ_MEDIA_IMAGES] == true || permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
            Snackbar.make(binding.root, "갤러리 권한을 허용하였습니다.", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(binding.root, "필수 권한이 거부되었습니다. 설정에서 권한을 허용해 주세요.", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    //android 13 버전 이하
    private fun handleLegacyPermissionsResult(permissions: Map<String, Boolean>) {
        if (permissions[Manifest.permission.CAMERA] == true) {
            Snackbar.make(binding.root, "카메라 권한을 허용하였습니다.", Snackbar.LENGTH_SHORT).show()
        }
        if (permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
            Snackbar.make(binding.root, "갤러리 권한을 허용하였습니다.", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(binding.root, "필수 권한이 거부되었습니다. 설정에서 권한을 허용해 주세요.", Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}
