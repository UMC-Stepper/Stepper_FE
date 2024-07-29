package com.example.umc_stepper.domain.model.response

import com.example.umc_stepper.data.remote.YoutubeApi

data class YouTubeVideo(
    val kind: String = "",
    val etag: String = "",
    val items: List<VideoItem> = listOf(),
    val pageInfo: PageInfo = PageInfo()
)

data class VideoItem(
    val kind: String = "",
    val etag: String = "",
    val id: String = "",
    val snippet: VideoSnippet = VideoSnippet()
)

data class VideoSnippet(
    val publishedAt: String = "",
    val channelId: String = "",
    val title: String = "",
    val description: String = "",
    val thumbnails: Thumbnails = Thumbnails(),
    val channelTitle: String = "",
    val tags: List<String> = listOf(),
    val categoryId: String = "",
    val liveBroadcastContent: String = "",
    val defaultLanguage: String = "",
    val localized: Localized = Localized(),
    val defaultAudioLanguage: String = ""
)

data class Thumbnails(
    val default: Thumbnail = Thumbnail(),
    val medium: Thumbnail = Thumbnail(),
    val high: Thumbnail = Thumbnail(),
    val standard: Thumbnail = Thumbnail(),
    val maxres: Thumbnail = Thumbnail()
)

data class Thumbnail(
    val url: String = "",
    val width: Int = 0,
    val height: Int = 0
)

data class Localized(
    val title: String = "",
    val description: String = ""
)

data class PageInfo(
    val totalResults: Int = 0,
    val resultsPerPage: Int = 0
)

data class Ylist(
    val ylist: MutableList<YouTubeVideo> = mutableListOf()
)
