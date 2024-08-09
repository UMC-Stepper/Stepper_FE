package com.example.umc_stepper.data.source.community

import android.util.Log
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.data.remote.CommunityApi
import com.example.umc_stepper.data.remote.FastApi
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.request.comment_controller.CommentWriteDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.comment_controller.CommentResponseItem
import com.example.umc_stepper.domain.model.response.comment_controller.CommentWriteResponse
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyPostsResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.ScrapResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.http.Body
import retrofit2.http.Path
import javax.inject.Inject

class CommunityApiDataSource @Inject constructor(
    private val communityApi: CommunityApi
){
    fun postCommitScrap(postId : Int) : Flow<BaseResponse<ScrapResponse>> = flow{
        val result = communityApi.postCommitScrap(postId)
        emit(result)
    }.catch {
        Log.e("Post Commit Scrap Failure", it.message.toString())

    }

    //내가 작성한 글 조회
    fun getCommunityMyPosts() : Flow<BaseListResponse<CommunityMyPostsResponseItem>> = flow{
        val result = communityApi.getCommunityMyPosts()
        emit(result)
    }.catch {
        Log.e("Get Community MyPosts Failure", it.message.toString())

    }

    //내가 작성한 댓글 조회
    fun getCommunityMyComments():Flow<BaseListResponse<CommunityMyCommentsResponseItem>> = flow{
        val result = communityApi.getCommunityMyComments()
        emit(result)
    }.catch {
        Log.e("Get Community MyComments Failure", it.message.toString())

    }

    //댓글 작성
    suspend fun postCommentWrite(commentWriteDto: CommentWriteDto):Flow<BaseResponse<CommentWriteResponse>> = flow{
        val result = communityApi.postCommentWrite(commentWriteDto)
        emit(result)
    }.catch {
        Log.e("Post Comment Write Failure", it.message.toString())

    }

    //댓글 조회
    suspend fun getComment(postId : Int):Flow<BaseListResponse<CommentResponseItem>> = flow{
        val result = communityApi.getComment(postId)
        emit(result)
    }.catch {
        Log.e("Get Comment Failure", it.message.toString())

    }


}
