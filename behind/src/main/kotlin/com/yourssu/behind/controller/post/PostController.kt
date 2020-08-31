package com.yourssu.behind.controller.post

import com.yourssu.behind.model.dto.post.request.CreateOrUpdateRequestPostDto
import com.yourssu.behind.model.dto.post.response.ResponseDetailPostsDto
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.post.PostType
import com.yourssu.behind.service.post.PostService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/article")
class PostController @Autowired constructor(val postService: PostService) {
    @ApiOperation("게시글 작성", consumes = (MediaType.MULTIPART_FORM_DATA_VALUE))
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createPost(@RequestPart(required = false) imgFile: Array<MultipartFile>?, @RequestPart createOrUpdateRequestPostDto: CreateOrUpdateRequestPostDto) {
        postService.createPost(createOrUpdateRequestPostDto, imgFile)
    }

    @GetMapping("/lecture/{lectureId}")
    @ApiOperation("강좌 별 게시글 가져오기")
    @ResponseStatus(HttpStatus.OK)
    fun getPost(@PathVariable lectureId: Long, @RequestParam(required = false) type: PostType?, @RequestParam page: Int): List<ResponsePostsDto> {
        return postService.getPosts(lectureId, type, page)
    }

    @ApiOperation("게시물 검색하기")
    @GetMapping("/search/{keyword}")
    @ResponseStatus(HttpStatus.OK)
    fun searchPosts(@PathVariable keyword: String, @RequestParam(required = false) type: PostType?, @RequestParam page: Int): List<ResponsePostsDto> {
        return postService.searchPosts(keyword, type, page)
    }

    @ApiOperation("스크랩 하기")
    @GetMapping("/scrap/{postId}")
    @ResponseStatus(HttpStatus.OK)
    fun scrapPost(@PathVariable postId: Long) {
        return postService.scrapPost(postId)
    }

    @ApiOperation("특정 게시글 가져오기")
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    fun getPostDetails(@PathVariable postId: Long): ResponseDetailPostsDto {
        return postService.getPostDetails(postId)
    }

    @ApiOperation("게시글 삭제하기")
    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable postId: Long) {
        return postService.deletePost(postId)
    }

    @ApiOperation("게시글 신고하기")
    @GetMapping("/report/{postId}")
    fun reportPost(@PathVariable postId: Long) {
        return postService.reportPost(postId)
    }
}
