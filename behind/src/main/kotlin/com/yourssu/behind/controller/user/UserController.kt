package com.yourssu.behind.controller.user

import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.post.PostSearch
import com.yourssu.behind.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController @Autowired constructor(val userService: UserService) {
    @GetMapping("{userId}/post")
    fun getUserRelatedPost(@PathVariable userId: Long, @RequestParam type: PostSearch): Collection<ResponsePostsDto> {
        return userService.findUserRelatedPost(userId, type)
    }
}
