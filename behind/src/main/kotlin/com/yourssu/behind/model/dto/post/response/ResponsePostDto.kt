package com.yourssu.behind.model.dto.post.response

import com.yourssu.behind.model.dto.lecture.ReturnLectureDto
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import java.time.LocalDateTime

class ResponsePostDto(post: Post) {
    val title: String = post.title
    val type: PostType = post.type
    val content: String = post.content
    val createdAt: LocalDateTime = post.createdAt
    val lecture = ReturnLectureDto(post.lecture)

}