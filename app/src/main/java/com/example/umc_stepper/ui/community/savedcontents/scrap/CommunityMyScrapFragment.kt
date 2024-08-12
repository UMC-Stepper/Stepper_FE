package com.example.umc_stepper.ui.community.savedcontents.scrap

import android.content.Context
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityMyScrapBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.utils.listener.ItemClickListener
import kotlinx.coroutines.launch

class CommunityMyScrapFragment : BaseFragment<FragmentCommunityMyScrapBinding>(R.layout.fragment_community_my_scrap),
    ItemClickListener {

    private lateinit var mainActivity: MainActivity
    private lateinit var myScrapAdapter: MyScrapAdapter
    private val communityViewModel: CommunityViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        updateToolbar()
        initAdapter()
    }

    private fun initAdapter() {
        myScrapAdapter = MyScrapAdapter(this)
        binding.fragmentCommunityMyScrapRv.adapter = myScrapAdapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // 내가 스크랩 한 글 목록 불러오기
            }
        }
    }

    private fun updateToolbar() {
        mainActivity.updateToolbarTitle("목록")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_toolbar_community_home)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_community_search)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_community_menu)
    }

    override fun onClick(item: Any) {
        TODO("Not yet implemented")
    }

}
