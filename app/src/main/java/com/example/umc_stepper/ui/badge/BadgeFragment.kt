package com.example.umc_stepper.ui.badge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentBadgeBinding
import com.example.umc_stepper.domain.model.response.Badge
import com.example.umc_stepper.ui.login.MainViewModel
import com.example.umc_stepper.ui.stepper.StepperViewModel
import kotlinx.coroutines.launch

class BadgeFragment : BaseFragment<FragmentBadgeBinding>(R.layout.fragment_badge) {
    private val mainViewModel: MainViewModel by activityViewModels()
    override fun setLayout() {
        getBadge()
    }

    private fun getBadge() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.getBadge()
            }
            }
    }

    private fun setBadge(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    // response -> BaseListResponse
                    // response.result -> badgeResponseItem
                    // badge -> badgeResponseItem.list
                    mainViewModel.getBadge.collect { response ->
                        val badgeResponseItem = response.result
                        val badge = Badge()
                        badgeResponseItem?.forEach { i ->
                            binding.fragmentBadgeYellow1Tv.text = i.categoryName
                            Log.e("mainViewModel ","it : ${i.categoryName}")
                            i.list.forEach { j ->
                                j.id
                            }

                        }
                    }
                } catch (e:Exception) {

                }
            }
        }
    }
}