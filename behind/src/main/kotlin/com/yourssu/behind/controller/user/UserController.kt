package com.yourssu.behind.controller.user

import com.yourssu.behind.model.dto.lecture.ReturnLectureDto
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.post.PostSearch
import com.yourssu.behind.service.user.UserService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController @Autowired constructor(val userService: UserService) {
    @GetMapping("/post")
    @ApiOperation("유저와 연관된 게시물 가져오기(댓글 작성한 글, 작성한 글, 스크랩 한 글")
    @ResponseStatus(HttpStatus.OK)
    fun getUserRelatedPost(@RequestParam type: PostSearch, page: Int): Collection<ResponsePostsDto> {
        return userService.findUserRelatedPost(type, page)
    }

    @DeleteMapping
    @ApiOperation("회원 탈퇴")
    @ResponseStatus(HttpStatus.OK)
    fun deleteUser() {
        return userService.deleteUser()
    }

    @GetMapping("/lectures")
    @ApiOperation("저장한 강의들 보기")
    @ResponseStatus(HttpStatus.OK)
    fun getAllLecture(): Collection<ReturnLectureDto> {
        return userService.getAllUserLecture()
    }

    @GetMapping("/feed")
    @ApiOperation("새 글 피드")
    @ResponseStatus(HttpStatus.OK)
    fun newPostFeed(@RequestParam page: Int): Collection<ResponsePostsDto> {
        return userService.newPostFeed(page)
    }
}
