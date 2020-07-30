package com.yourssu.behind.service.post.function

import com.yourssu.behind.exception.post.PostNotExistException
import com.yourssu.behind.exception.post.WriterScrapException
import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.Scrap
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.post.PostRepository
import com.yourssu.behind.repository.post.ScrapRepository
import com.yourssu.behind.repository.user.UserRepository
import javax.transaction.Transactional

class ScrapFunction(private val userRepository: UserRepository, private val postRepository: PostRepository, private val scrapRepository: ScrapRepository) {


    fun createScrapPost(schoolId: String, postId: Long) {

        val post: Post = postRepository.findById(postId).orElseThrow { PostNotExistException() }
        val user: User = userRepository.findBySchoolId(schoolId).orElseThrow { UserNotExistsException() }
        val scrap = scrapRepository.findByScrapUserAndScrapPost(user, post).orElse(null)

        if (scrap == null) {
            if (post.user == user)
                throw WriterScrapException()
            scrapRepository.save(Scrap(scrapUser = user, scrapPost = post))

        } else {
            deleteScrapPost(user, post)
        }
    }

    private fun deleteScrapPost(user: User, post: Post) {
        return scrapRepository.deleteByScrapUserAndScrapPost(user, post)
    }
}
