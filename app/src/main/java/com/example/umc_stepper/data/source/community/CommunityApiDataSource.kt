package com.example.umc_stepper.data.source.community

import android.util.Log
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.data.remote.CommunityApi
import com.example.umc_stepper.domain.model.request.comment_controller.CommentWriteDto
import com.example.umc_stepper.domain.model.request.comment_controller.ReplyRequestDto
import com.example.umc_stepper.domain.model.response.WeeklyMissionResponse
import com.example.umc_stepper.domain.model.response.comment_controller.CommentResponseItem
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
import retrofit2.HttpException
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
       try {
           val result = communityApi.postLikeEdit(postId)
           emit(result)
       }  catch (e: HttpException) {
           val errorResponse = e.response()?.let { it }
           Log.e("post Community LikeEdit Failure", "HTTP Error: ${errorResponse?.errorBody()?.string()}")

           emit(BaseResponse(
               isSuccess = errorResponse!!.isSuccessful,
               code = errorResponse.code().toString(),
               message = errorResponse.message().toString(),
               result = null)
           )
       }
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

    // 위클리 게시글 조회 API
    fun getWeeklyPostList(id : Int): Flow<BaseListResponse<ApiResponseListPostViewResponseItem>> = flow {
        try{
            val result = communityApi.getWeeklyPostList(id)
            emit(result)
        }catch (e: HttpException) {
            val errorResponse = e.response()?.let { it }
            Log.e("Get WeeklyPostList Failure", "HTTP Error: ${errorResponse?.errorBody()?.string()}")

            emit(BaseListResponse(
                isSuccess = errorResponse!!.isSuccessful,
                code = errorResponse.code().toString(),
                message = errorResponse.message().toString(),
                result = emptyList())
            )
        }
    }

    // 주간 미션 조회 API
    fun getWeeklyMission(id : Int): Flow<BaseResponse<WeeklyMissionResponse>> =  flow {
        try{
            val result = communityApi.getWeeklyMission(id)
            emit(result)
        }catch (e: HttpException) {
            val errorResponse = e.response()?.let { it }
            Log.e("Get WeeklyMission Failure", "HTTP Error: ${errorResponse?.errorBody()?.string()}")

            emit(BaseResponse(
                isSuccess = errorResponse!!.isSuccessful,
                code = errorResponse.code().toString(),
                message = errorResponse.message().toString(),
                result = null)
            )
        }
    }

    //내가 작성한 글 목록 조회
    fun getCommunityMyPosts() : Flow<BaseListResponse<CommunityMyPostsResponseItem>> = flow{
        val result = communityApi.getCommunityMyPosts()
        emit(result)
    }.catch {
        Log.e("Get Community MyPosts Failure", it.message.toString())

    }

    //내가 작성한 댓글 목록 조회 getCommunityMyScraps
    fun getCommunityMyComments():Flow<BaseListResponse<CommunityMyCommentsResponseItem>> = flow{
        try {
        val result = communityApi.getCommunityMyComments()
        emit(result)
        } catch (e: HttpException) {
            val errorResponse = e.response()?.let { it }
            Log.e("Get Community MyScraps Failure", "HTTP Error: ${errorResponse?.errorBody()?.string()}")

            emit(BaseListResponse(
                isSuccess = errorResponse!!.isSuccessful,
                code = errorResponse.code().toString(),
                message = errorResponse.message().toString(),
                result = emptyList())
            )
        }
    }

    //내가 스크랩한 글 목록 조회
    fun getCommunityMyScraps():Flow<BaseListResponse<CommunityMyCommentsResponseItem>> = flow{
        try {
            val result = communityApi.getCommunityMyScraps()
            emit(result)
        } catch (e: HttpException) {
            val errorResponse = e.response()?.let { it }
            Log.e("Get Community MyScraps Failure", "HTTP Error: ${errorResponse?.errorBody()?.string()}")

            emit(BaseListResponse(
                isSuccess = errorResponse!!.isSuccessful,
                code = errorResponse.code().toString(),
                message = errorResponse.message().toString(),
                result = emptyList())
            )
        }
    }

    //댓글 작성
    suspend fun postReply(replyRequestDto: ReplyRequestDto):Flow<BaseResponse<CommentResponseItem>> = flow{
        try {
            val result = communityApi.postReply(replyRequestDto)
            emit(result)
        } catch (e: HttpException) {
            Log.e("Post Comment Write Failure", e.message.toString())
            emit(
                BaseResponse(
                    isSuccess = false,
                    code = e.code().toString(),
                    message = e.message(),
                    result = null
                )
            )
        }
    }

    //대댓글 작성
    suspend fun postCommentWrite(commentWriteDto: CommentWriteDto):Flow<BaseResponse<CommentResponseItem>> = flow{
        try {
            val result = communityApi.postCommentWrite(commentWriteDto)
            emit(result)
        } catch (e: HttpException) {
            Log.e("Post Comment Write Failure", e.message.toString())
            emit(
                BaseResponse(
                    isSuccess = false,
                    code = e.code().toString(),
                    message = e.message(),
                    result = null
                )
            )
        }
    }

    //댓글 조회
    suspend fun getComment(postId : Int):Flow<BaseListResponse<CommentResponseItem>> = flow{
        val result = communityApi.getComment(postId)
        emit(result)
    }.catch {
        Log.e("Get Comment Failure", it.message.toString())
    }


}
