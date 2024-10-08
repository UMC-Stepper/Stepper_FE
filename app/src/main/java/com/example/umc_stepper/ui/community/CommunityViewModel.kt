package com.example.umc_stepper.ui.community

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.comment_controller.CommentWriteDto
import com.example.umc_stepper.domain.model.request.comment_controller.ReplyRequestDto
import com.example.umc_stepper.domain.model.response.WeeklyMissionResponse
import com.example.umc_stepper.domain.model.response.comment_controller.CommentResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponseListPostViewResponse
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponseListPostViewResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponsePostResponse
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponsePostViewResponse
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyPostsResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.LikeResponse
import com.example.umc_stepper.domain.model.response.post_controller.ScrapResponse
import com.example.umc_stepper.domain.repository.CommunityApiRepository
import com.example.umc_stepper.utils.enums.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val communityApiRepository: CommunityApiRepository
) : ViewModel() {

    // 내가 작성한 글의 글 목록 조회
    private val _communityMyPostsResponseItem = MutableStateFlow<BaseListResponse<CommunityMyPostsResponseItem>>(BaseListResponse())
    val communityMyPostsResponseItem : StateFlow<BaseListResponse<CommunityMyPostsResponseItem>> = _communityMyPostsResponseItem

    // 내가 작성한 댓글의 글 목록 조회
    private val _communityMyCommentsResponseItem = MutableStateFlow<BaseListResponse<CommunityMyCommentsResponseItem>>(BaseListResponse())
    val communityMyCommentsResponseItem : StateFlow<BaseListResponse<CommunityMyCommentsResponseItem>> = _communityMyCommentsResponseItem

    // 내가 스크랩한 글 목록 조회
    private val _communityMyScrapResponseItem = MutableStateFlow<BaseListResponse<CommunityMyCommentsResponseItem>>(BaseListResponse())
    val communityMyScrapResponseItem : StateFlow<BaseListResponse<CommunityMyCommentsResponseItem>> = _communityMyScrapResponseItem

    // 위클리 게시글 조회
    private val _weeklyPostViewListResponse = MutableStateFlow<BaseListResponse<CommunityMyCommentsResponseItem>>(BaseListResponse())
    val weeklyPostViewListResponse : StateFlow<BaseListResponse<CommunityMyCommentsResponseItem>> = _weeklyPostViewListResponse

    // 위클리 게시글 검색 조회
    private val _weeklySearchViewListResponse = MutableStateFlow<BaseListResponse<CommunityMyCommentsResponseItem>>(BaseListResponse())
    val weeklySearchViewListResponse : StateFlow<BaseListResponse<CommunityMyCommentsResponseItem>> = _weeklySearchViewListResponse


    //주간 미션 조회
    private val _weeklyMissionResponse = MutableStateFlow<BaseResponse<WeeklyMissionResponse>>(BaseResponse())
    val weeklyMissionResponse : StateFlow<BaseResponse<WeeklyMissionResponse>> = _weeklyMissionResponse

    // 게시글 상세 조회
    private val _apiResponsePostViewResponse = MutableStateFlow<BaseResponse<ApiResponsePostViewResponse>>(BaseResponse())
    val apiResponsePostViewResponse : StateFlow<BaseResponse<ApiResponsePostViewResponse>> = _apiResponsePostViewResponse

    // 게시글 목록 조회
    private val _apiResponseListPostViewResponse = MutableStateFlow<BaseListResponse<ApiResponseListPostViewResponseItem>>(BaseListResponse())
    val apiResponseListPostViewResponse : StateFlow<BaseListResponse<ApiResponseListPostViewResponseItem>> = _apiResponseListPostViewResponse

    // 댓글 작성
    private val _commentWriteResponse = MutableStateFlow<BaseResponse<CommentResponseItem>>(BaseResponse())
    val commentWriteResponse : StateFlow<BaseResponse<CommentResponseItem>> = _commentWriteResponse

    // 대댓글 작성
    private val _replyResponse = MutableStateFlow<BaseResponse<CommentResponseItem>>(BaseResponse())
    val replyResponse : StateFlow<BaseResponse<CommentResponseItem>> = _replyResponse

    // 댓글 조회
    private val _getCommentResponse = MutableStateFlow<BaseListResponse<CommentResponseItem>>(BaseListResponse())
    val getCommentResponse : StateFlow<BaseListResponse<CommentResponseItem>> = _getCommentResponse

    // 좋아요 등록
    private val _likeResponse = MutableStateFlow<BaseResponse<LikeResponse>>(BaseResponse())
    val likeResponse : StateFlow<BaseResponse<LikeResponse>> = _likeResponse

    // 스크랩 등록
    private val _scrapResponse = MutableStateFlow<BaseResponse<ScrapResponse>>(BaseResponse())
    val scrapResponse : StateFlow<BaseResponse<ScrapResponse>> = _scrapResponse

    // 스크랩 취소
    private val _scrapCancelResponse = MutableStateFlow<BaseResponse<String>>(BaseResponse())
    val scrapCancelResponse : StateFlow<BaseResponse<String>> = _scrapCancelResponse

    //글 작성
    private val _postEditResponse = MutableStateFlow<BaseResponse<ApiResponsePostResponse>>(
        BaseResponse()
    )
    val postEditResponse : StateFlow<BaseResponse<ApiResponsePostResponse>> = _postEditResponse

    //글 작성
    fun postEditResponse(data : RequestBody, image : List<MultipartBody.Part>){
        viewModelScope.launch {
            try {
                communityApiRepository.postEditPost(data,image).collect{
                    _postEditResponse.value = it
                }
            }catch (e : Exception){
                Log.e("에러","글 작성 에러")
            }
        }
    }

    // 내가 작성한 글의 글 목록 조회
    fun getCommunityMyPosts() {
        viewModelScope.launch {
            try{
                communityApiRepository.getCommunityMyPosts().collect {
                    _communityMyPostsResponseItem.value = it
                    Log.d("CommunityViewModel", "CommunityMyPostsResponseItem : $it")
                }
            } catch (e:Exception) {
                Log.e("getCommunityMyPosts is Error", e.message.toString())
            }
        }
    }


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

    // 위클리 게시글 조회
    fun getWeeklyPostList(weeklyMissionId : Int) {
        viewModelScope.launch {
            try{
                communityApiRepository.getWeeklyPostList(weeklyMissionId).collect {
                    _weeklyPostViewListResponse.value = it
                    Log.d("CommunityViewModel", "_weeklyPostViewListResponse : $it")
                }
            } catch (e:Exception) {
                Log.e("getCommunityMyScraps is Error", e.message.toString())
            }
        }
    }

    // 위클리 게시글 조회 search
    fun getWeeklySearchList(weeklyMissionId : Int, query : String) {
        viewModelScope.launch {
            try{
                communityApiRepository.getWeeklyPostList(weeklyMissionId).collect { response ->
                    if (response.isSuccess && !response.result.isNullOrEmpty()) {
                        val filterList = response.result.filter{
                            it.title.contains(query) || it.body.contains(query)
                        }
                        val copyResponse = response.copy(loadState = LoadState.SUCCESS).copy(result = filterList)
                        _weeklySearchViewListResponse.value = copyResponse
                        Log.d("TodayViewModel", "_checkExerciseResponseDTO : $response")
                    } else {
                        _weeklySearchViewListResponse.value = BaseListResponse(LoadState.EMPTY)
                    }
                }
            } catch (e:Exception) {
                Log.e("getCommunityMyScraps is Error", e.message.toString())
            }
        }
    }


    // 주간 미션 조회
    fun getWeeklyMission(id : Int) {
        viewModelScope.launch {
            try{
                communityApiRepository.getWeeklyMission(id).collect {
                    _weeklyMissionResponse.value = it
                    Log.d("CommunityViewModel", "_weeklyMissionResponse : $it")
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

    // 게시글 목록 조회
    fun getDetailPostList(categoryName : String) {
        viewModelScope.launch {
            try {
                communityApiRepository.getDetailPostList(categoryName).collect {
                    _apiResponseListPostViewResponse.value = it
                    Log.d("게시글 목록 조회", "_apiResponseListPostViewResponse : $it")
                }
            } catch (e:Exception) {
                Log.e("getDetailPostList is Error", e.message.toString())
            }
        }
    }

    // 댓글 작성
    fun postCommentWrite(commentWriteDto: CommentWriteDto) {
        viewModelScope.launch {
            try {
                communityApiRepository.postCommentWrite(commentWriteDto).collect {
                    _commentWriteResponse.value = it
                    Log.d("댓글 작성", "_commentWriteResponse : $it")
                }
            } catch (e: Exception) {
                Log.e("postCommentWrite is Error", e.message.toString())
            }
        }
    }

    // 대댓글 작성
    fun postReply(replyRequestDto: ReplyRequestDto) {
        viewModelScope.launch {
            try {
                communityApiRepository.postReply(replyRequestDto).collect {
                    _replyResponse.value = it
                    Log.d("대댓글 작성", "_replyResponse : $it")
                }
            } catch (e: Exception) {
                Log.e("postCommentWrite is Error", e.message.toString())
            }
        }
    }

    // 댓글 조회
    fun getComment(postId: Int) {
        viewModelScope.launch {
            try {
                communityApiRepository.getComment(postId).collect {
                    _getCommentResponse.value = it
                    Log.d("댓글 조회", "_getCommentResponse : $it")
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