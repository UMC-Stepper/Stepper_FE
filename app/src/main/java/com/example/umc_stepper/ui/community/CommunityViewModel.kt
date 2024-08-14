package com.example.umc_stepper.ui.community

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.comment_controller.CommentWriteDto
import com.example.umc_stepper.domain.model.request.comment_controller.ReplyRequestDto
import com.example.umc_stepper.domain.model.response.comment_controller.CommentResponse
import com.example.umc_stepper.domain.model.response.comment_controller.CommentWriteResponse
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponsePostViewResponse
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.LikeResponse
import com.example.umc_stepper.domain.model.response.post_controller.ScrapResponse
import com.example.umc_stepper.domain.repository.CommunityApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val communityApiRepository: CommunityApiRepository
) : ViewModel() {

    // 내가 작성한 댓글의 글 목록 조회
    private val _communityMyCommentsResponseItem = MutableStateFlow<BaseListResponse<CommunityMyCommentsResponseItem>>(BaseListResponse())
    val communityMyCommentsResponseItem : StateFlow<BaseListResponse<CommunityMyCommentsResponseItem>> = _communityMyCommentsResponseItem

    // 내가 스크랩한 글 목록 조회
    private val _communityMyScrapResponseItem = MutableStateFlow<BaseListResponse<CommunityMyCommentsResponseItem>>(BaseListResponse())
    val communityMyScrapResponseItem : StateFlow<BaseListResponse<CommunityMyCommentsResponseItem>> = _communityMyScrapResponseItem

    // 게시글 상세 조회
    private val _apiResponsePostViewResponse = MutableStateFlow<BaseResponse<ApiResponsePostViewResponse>>(BaseResponse())
    val apiResponsePostViewResponse : StateFlow<BaseResponse<ApiResponsePostViewResponse>> = _apiResponsePostViewResponse

    // 댓글 작성
    private val _commentWriteResponse = MutableStateFlow<BaseResponse<CommentWriteResponse>>(BaseResponse())
    val commentWriteResponse : StateFlow<BaseResponse<CommentWriteResponse>> = _commentWriteResponse

    // 대댓글 작성
    private val _commentResponse = MutableStateFlow<BaseResponse<CommentResponse>>(BaseResponse())
    val commentResponse : StateFlow<BaseResponse<CommentResponse>> = _commentResponse

    // 좋아요 등록
    private val _likeResponse = MutableStateFlow<BaseResponse<LikeResponse>>(BaseResponse())
    val likeResponse : StateFlow<BaseResponse<LikeResponse>> = _likeResponse

    // 스크랩 등록
    private val _scrapResponse = MutableStateFlow<BaseResponse<ScrapResponse>>(BaseResponse())
    val scrapResponse : StateFlow<BaseResponse<ScrapResponse>> = _scrapResponse

    // 스크랩 취소
    private val _scrapCancelResponse = MutableStateFlow<BaseResponse<String>>(BaseResponse())
    val scrapCancelResponse : StateFlow<BaseResponse<String>> = _scrapCancelResponse

    // 내가 작성한 댓글의 글 목록 조회
    fun getCommunityMyComments() {
        viewModelScope.launch {
            try{
                communityApiRepository.getCommunityMyComments().collect {
                    _communityMyCommentsResponseItem.value = it
                    Log.d("CommunityViewModel", "CommunityMyCommentsResponseItem : $it")
                }
            } catch (e:Exception) {
                Log.e("getCommunityMyComments is Error", e.message.toString())
            }
        }
    }

    // 내가 스크랩한 글의 글 목록 조회
    fun getCommunityMyScraps() {
        viewModelScope.launch {
            try{
                communityApiRepository.getCommunityMyScraps().collect {
                    _communityMyScrapResponseItem.value = it
                    Log.d("CommunityViewModel", "_communityMyScrapResponseItem : $it")
                }
            } catch (e:Exception) {
                Log.e("getCommunityMyScraps is Error", e.message.toString())
            }
        }
    }


    // 게시글 상세 조회
    fun getDetailPost(postId : Int) {
        viewModelScope.launch {
            try {
                communityApiRepository.getDetailPost(postId).collect {
                    _apiResponsePostViewResponse.value = it
                    Log.d("CommunityViewModel", "_apiResponsePostViewResponse : $it")
                }
            } catch (e:Exception) {
                Log.e("getDetailPost is Error", e.message.toString())
            }
        }
    }

    // 댓글 작성
    fun postCommentWrite(commentWriteDto: CommentWriteDto) {
        viewModelScope.launch {
            try {
                communityApiRepository.postCommentWrite(commentWriteDto).collect {
                    _commentWriteResponse.value = it
                }
            } catch (e: Exception) {
                Log.e("postCommentWrite is Error", e.message.toString())
            }
        }
    }

    // 대댓글 작성
    fun postReply (replyRequestDto: ReplyRequestDto) {
        viewModelScope.launch {
            try {
                communityApiRepository.postReply(replyRequestDto).collect {
                    _commentResponse.value = it
                }
            } catch (e: Exception) {
                Log.e("postCommentWrite is Error", e.message.toString())
            }
        }
    }

    // 좋아요 등록
    fun postLikeEdit(postId : Int) {
        viewModelScope.launch {
            try {
                communityApiRepository.postLikeEdit(postId).collect {
                    if(it.isSuccess) {
                        _likeResponse.value = it
                        getDetailPost(postId)
                        Log.d("CommunityViewModel", "_likeResponse : $it")
                    }
                }
            } catch (e:Exception) {
                Log.e("getDetailPost is Error", e.message.toString())
            }
        }
    }

    // 스크랩 등록
    fun postCommitScrap(postId: Int) {
        viewModelScope.launch {
            try {
                communityApiRepository.postCommitScrap(postId).collect {
                    if (it.isSuccess) {
                        _scrapResponse.value = it
                        getDetailPost(postId)
                        Log.d("CommunityViewModel", "_scrapResponse : $it")
                    }
                }
            } catch (e:Exception) {
                Log.e("getDetailPost is Error", e.message.toString())
            }
        }
    }


    // 스크랩 취소
    fun deleteCancelScrap(postId : Int) {
        viewModelScope.launch {
            try {
                communityApiRepository.deleteCancelScrap(postId).collect {
                    if (it.isSuccess) {
                        _scrapCancelResponse.value = it
                        getDetailPost(postId)
                        Log.d("CommunityViewModel", "_scrapResponse : $it")
                    }
                }
            } catch (e:Exception) {
                Log.e("getDetailPost is Error", e.message.toString())
            }
        }
    }

}