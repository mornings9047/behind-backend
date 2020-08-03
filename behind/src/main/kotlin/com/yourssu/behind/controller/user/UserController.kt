package com.yourssu.behind.controller.user

import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.post.PostSearch
import com.yourssu.behind.service.user.UserService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController @Autowired constructor(val userService: UserService) {

    @GetMapping("{userId}/post")
    @ApiOperation("유저와 연관된 게시물 가져오기(댓글 작성한 글, 작성한 글, 스크랩 한 글")
    fun getUserRelatedPost(@PathVariable userId: Long, @RequestParam type: PostSearch, page: Int): Collection<ResponsePostsDto> {
        return userService.findUserRelatedPost(userId, type, page)
    }

    @DeleteMapping("{userId}")
    @ApiOperation("회원 탈퇴")
    fun deleteUser(@PathVariable userId: Long) {
        return userService.deleteUser(userId)
    }

}
