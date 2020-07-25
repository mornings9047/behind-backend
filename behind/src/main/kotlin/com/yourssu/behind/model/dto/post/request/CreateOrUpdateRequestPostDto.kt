package com.yourssu.behind.model.dto.post.request

import com.yourssu.behind.model.entity.post.PostType

data class CreateOrUpdateRequestPostDto(val schoolId: String, val title: String, val type: PostType, val content: String, val lectureId: Long)
