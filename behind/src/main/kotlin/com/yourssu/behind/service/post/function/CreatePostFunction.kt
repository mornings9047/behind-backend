package com.yourssu.behind.service.post.function

import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.post.PostType
import com.yourssu.behind.repository.post.PostRepository

class CreatePostFunction(val postRepository: PostRepository) {
    fun getAllPosts(lecture: Lecture): List<ResponsePostsDto> {
        return postRepository.findAllByLecture(lecture).map { ResponsePostsDto(it) }
    }

    fun getPostsByType(lecture: Lecture, type: PostType): List<ResponsePostsDto> {
        return postRepository.findAllByLectureAndTypeEquals(lecture, type).map { ResponsePostsDto(it) }
    }
}