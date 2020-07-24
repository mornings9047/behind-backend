package com.yourssu.behind.service.post.function

import com.yourssu.behind.exception.post.AlreadyThumbsUpException
import com.yourssu.behind.exception.post.PostNotExistException
import com.yourssu.behind.exception.post.WriterThumbsUpException
import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.user.UserRepository

class ThumbsUpFunction(private val userRepository: UserRepository, private val postRepository: PostRepository) {

    fun thumbsUp(schoolId: String, postId: Long) {
        var targetPost: Post = postRepository.findById(postId).orElseThrow { PostNotExistException() }
        var targetUser: User = userRepository.findBySchoolId(schoolId).orElseThrow { UserNotExistsException() }

        if (targetPost.user == targetUser) //자신의 글에는 좋아요를 누를 수 없음
            throw WriterThumbsUpException()
        else if (targetUser.likePost.contains(targetPost))
            throw AlreadyThumbsUpException()
        targetUser.likePost.add(targetPost)
        userRepository.save(targetUser)
    }
}