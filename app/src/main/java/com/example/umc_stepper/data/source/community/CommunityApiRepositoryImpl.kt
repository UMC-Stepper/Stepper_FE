package com.example.umc_stepper.data.source.community

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
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
import com.example.umc_stepper.domain.repository.CommunityApiRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import javax.inject.Inject

class CommunityApiRepositoryImpl @Inject constructor(
    private val dataSource: CommunityApiDataSource
) : CommunityApiRepository {
    override suspend fun postCommitScrap(postId : Int): Flow<BaseResponse<ScrapResponse>> =
        dataSource.postCommitScrap(postId)

    override suspend fun deleteCancelScrap(postId: Int): Flow<BaseResponse<String>> =
        dataSource.deleteCancelScrap(postId)

    override suspend fun getCommunityMyPosts(): Flow<BaseListResponse<CommunityMyPostsResponseItem>> =
        dataSource.getCommunityMyPosts()

    override suspend fun getCommunityMyComments(): Flow<BaseListResponse<CommunityMyCommentsResponseItem>> =
        dataSource.getCommunityMyComments()

    override suspend fun getCommunityMyScraps(): Flow<BaseListResponse<CommunityMyCommentsResponseItem>> =
        dataSource.getCommunityMyScraps()

    override suspend fun postCommentWrite(commentWriteDto: CommentWriteDto): Flow<BaseResponse<CommentResponseItem>> =
        dataSource.postCommentWrite(commentWriteDto)

    override suspend fun getComment(postId: Int): Flow<BaseListResponse<CommentResponseItem>> =
        dataSource.getComment(postId)

    override suspend fun postReply(replyRequestDto: ReplyRequestDto): Flow<BaseResponse<CommentResponseItem>> =
        dataSource.postReply(replyRequestDto)

    override suspend fun postLikeEdit(postId: Int): Flow<BaseResponse<LikeResponse>>
    = dataSource.postLikeEdit(postId)

    override suspend fun deleteCancelLike(postId: Int): Flow<BaseResponse<String>>
    = dataSource.deleteCancelLike(postId)

    override suspend fun postEditPost(data : RequestBody, image : List<MultipartBody.Part>): Flow<BaseResponse<ApiResponsePostResponse>>
    = dataSource.postEditPost(data,image)

    override suspend fun getDetailPost(postId: Int): Flow<BaseResponse<ApiResponsePostViewResponse>>
    = dataSource.getDetailPost(postId)

    override suspend fun getDetailPostList(categoryName: String): Flow<BaseListResponse<CommunityMyCommentsResponseItem>>
    = dataSource.getDetailPostList(categoryName)

    // 위클리 게시글 조회 API
    override suspend fun getWeeklyPostList(weeklyMissionId  : Int): Flow<BaseListResponse<CommunityMyCommentsResponseItem>>
    = dataSource.getWeeklyPostList(weeklyMissionId)

    // 주간 미션 조회 API
    override suspend fun getWeeklyMission(id : Int): Flow<BaseResponse<WeeklyMissionResponse>>
    = dataSource.getWeeklyMission(id)
}