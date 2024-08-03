package com.example.umc_stepper.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding>(@LayoutRes private val layoutRes: Int) : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun NavController.navigateSafe(
        @IdRes resId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null,
        navExtras: Navigator.Extras? = null
    ) {
        // 현재 목적지와 이동할 목적지 비교 (목적지 중복 이동 방지)
        val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
        if (action != null && currentDestination?.id != action.destinationId) {
            val newNavOptions = navOptions?.let { NavOptions.Builder() } ?: NavOptions.Builder()
            newNavOptions.setPopUpTo(action.destinationId, true)    // 현재 프래그먼트 ~ 목적지 프래그먼트 사이의 백스택 제거
            navigate(resId, args, newNavOptions.build(), navExtras)
        }
    }

    abstract fun setLayout()
}