package com.yourssu.behind.model.dto.post.request

import com.yourssu.behind.model.entity.post.PostType

data class CreateOrUpdateRequestPostDto(val schoolId: String="20160279", val title: String="", val type: PostType=PostType.FREE, val content: String="", val lectureId: Long=1)