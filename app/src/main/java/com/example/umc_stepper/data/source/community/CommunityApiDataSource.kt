package com.example.umc_stepper.data.source.community

import android.util.Log
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.data.remote.CommunityApi
import com.example.umc_stepper.domain.model.request.comment_controller.CommentWriteDto
import com.example.umc_stepper.domain.model.response.comment_controller.CommentResponseItem
import com.example.umc_stepper.domain.model.response.comment_controller.CommentWriteResponse
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponseListPostViewResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponsePostResponse
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponsePostViewResponse
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyPostsResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.LikeResponse
import com.example.umc_stepper.domain.model.response.post_controller.ScrapResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommunityApiDataSource @Inject constructor(
    private val communityApi: CommunityApi
) {
    //POST 스크랩 등록
    fun postCommitScrap(postId: Int): Flow<BaseResponse<ScrapResponse>> = flow {
        val result = communityApi.postCommitScrap(postId)
        emit(result)
    }.catch {
        Log.e("Post Commit Scrap Failure", it.message.toString())
    }

    //DELETE 스크랩 취소
    fun deleteCancelScrap(postId: Int): Flow<BaseResponse<String>> = flow {
        val result = communityApi.deleteCancelScrap(postId)
        emit(result)
    }.catch {
        Log.e("Delete Commit Cancel Failure", it.message.toString())
    }

    //POST 좋아요 등록
    fun postLikeEdit(postId: Int): Flow<BaseResponse<LikeResponse>> = flow {
        val result = communityApi.postLikeEdit(postId)
        emit(result)
    }.catch {
        Log.e("Post Like Edit Failure", it.message.toString())
    }

    //DELETE 좋아요 취소
    fun deleteCancelLike(postId: Int): Flow<BaseResponse<String>> = flow<BaseResponse<String>> {
        val result = communityApi.deleteCancelLike(postId)
        emit(result)
    }.catch {
        Log.e("Delete Cancel Like Failure", it.message.toString())
    }

    //POST 게시글 작성
    fun postEditPost(): Flow<BaseResponse<ApiResponsePostResponse>> = flow {
        val result = communityApi.postEditPost()
        emit(result)
    }.catch {
        Log.e("Post Edit Post Failure", it.message.toString())
    }

    //GET 게시글 상세 조회
    fun getDetailPost(postId: Int): Flow<BaseResponse<ApiResponsePostViewResponse>> = flow {
        val result = communityApi.getDetailPost(postId)
        emit(result)
    }.catch {
        Log.e("Get Detail Post", it.message.toString())
    }

    //GET 게시글 목록 조회
    fun getDetailPostList(categoryName: String): Flow<BaseListResponse<ApiResponseListPostViewResponseItem>> =
        flow {
            val result = communityApi.getDetailPostList(categoryName)
            emit(result)
        }.catch {
            Log.e("Get Detail Post List Failure", it.message.toString())
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
