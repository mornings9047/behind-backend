package com.yourssu.behind.controller.post

import com.yourssu.behind.model.dto.post.request.CreateOrUpdateRequestPostDto
import com.yourssu.behind.model.dto.post.response.ResponsePostDto
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
@RequestMapping("/posts")
class PostController @Autowired constructor(val postService: PostService) {

    @PostMapping("/", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ApiOperation("게시글 작성",consumes = (MediaType.MULTIPART_FORM_DATA_VALUE))
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun createPost(@RequestPart(required = false) imgFile: MultipartFile?,
                   @RequestPart createOrUpdateRequestPostDto: CreateOrUpdateRequestPostDto
    ) {
        postService.createPost(createOrUpdateRequestPostDto, imgFile)
    }

    @GetMapping("/{lectureId}")
    @ApiOperation("강좌 별 게시글 가져오기")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun getPost(@PathVariable lectureId: Long, @RequestParam(required = false) type: PostType?, @RequestParam page: Int): List<ResponsePostsDto> {
        return postService.getPosts(lectureId, type, page)
    }

    @GetMapping("/search/{keyword}")
    @ApiOperation("게시물 검색하기")
    fun searchPosts(@PathVariable keyword: String, @RequestParam type: PostType?, @RequestParam page: Int): List<ResponsePostsDto> {
        return postService.searchPosts(keyword, type, page)
    }


    @ApiOperation("스크랩 하기")
    @GetMapping("/{postId}/scrap")
    fun scrapPost(@PathVariable postId: Long, @RequestParam schoolId: String) {
        return postService.scrapPost(schoolId, postId)
    }

    @GetMapping("/{postId}")
    fun getPostDetails(@PathVariable postId: Long): ResponsePostDto {
        return postService.getPostDetails(postId)
    }
}
