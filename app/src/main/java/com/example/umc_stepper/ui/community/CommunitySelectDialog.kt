package com.example.umc_stepper.ui.community

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.umc_stepper.databinding.DialogCommunityBinding
import com.example.umc_stepper.databinding.DialogProfileSelectBinding
import com.example.umc_stepper.ui.login.ConfirmDialogInterface
import com.example.umc_stepper.utils.enums.DialogType
import com.example.umc_stepper.utils.listener.CommunitySelectClick

class CommunitySelectDialog(
    private val communitySelectClick: CommunitySelectClick
) : DialogFragment() {

    private var _binding: DialogProfileSelectBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.5).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogProfileSelectBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        with(binding) {

            dialogProfileSelectBt1.setOnClickListener {
                communitySelectClick.onBtnClick1()
                dismiss()
            }
            dialogProfileSelectBt2.setOnClickListener {
                communitySelectClick.onBtnClick2()
                dismiss()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
