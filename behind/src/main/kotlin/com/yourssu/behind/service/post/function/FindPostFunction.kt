package com.yourssu.behind.service.post.function

import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.lecture.Lecture
import com.yourssu.behind.model.entity.post.PostPage
import com.yourssu.behind.model.entity.post.PostType
import com.yourssu.behind.repository.post.PostRepository

class FindPostFunction(private val postRepository: PostRepository) {
    fun getAllPosts(lecture: Lecture, page: Int): List<ResponsePostsDto> {
        return postRepository.findAllByLectureAndDeletePostIsFalse(lecture, PostPage(page)).map { ResponsePostsDto(it) }
    }

    fun getPostsByType(lecture: Lecture, type: PostType, page: Int): List<ResponsePostsDto> {
        return postRepository.findAllByLectureAndTypeEqualsAndDeletePostIsFalse(lecture, type, PostPage(page)).map { ResponsePostsDto(it) }
    }

    fun searchPostsByKeyword(keyword: String): List<ResponsePostsDto> {
        return postRepository.findByTitleContainingOrContentContaining(keyword, keyword).map { ResponsePostsDto(it) }
    }
}
