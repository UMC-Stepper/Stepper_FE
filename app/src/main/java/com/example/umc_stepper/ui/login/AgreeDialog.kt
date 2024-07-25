package com.example.umc_stepper.ui.login

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.umc_stepper.databinding.DialogAppPermissionBinding

class AgreeDialog(
    confirmDialogInterface: ConfirmDialogInterface
) : DialogFragment(){

    private var _binding: DialogAppPermissionBinding? = null
    private val binding get() = _binding!!

    private var confirmDialogInterface: ConfirmDialogInterface? = null

    init {
        this.confirmDialogInterface = confirmDialogInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAppPermissionBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //배경 투명
        binding.dialogAppPermissionBtn.setOnClickListener{
            this.confirmDialogInterface?.onClickConfirmButton(true)
            dismiss()
        }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}