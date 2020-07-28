package com.yourssu.behind.controller.post

import com.yourssu.behind.model.dto.post.request.CreateOrUpdateRequestPostDto
import com.yourssu.behind.model.dto.post.response.ResponsePostDto
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.post.PostType
import com.yourssu.behind.service.post.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/posts")
class PostController @Autowired constructor(val postService: PostService) {

    @PostMapping("/", consumes = [MediaType.ALL_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createPost(@RequestPart imgFile: MultipartFile?,
                   @RequestPart createOrUpdateRequestPostDto: CreateOrUpdateRequestPostDto
    ) {
        postService.createPost(createOrUpdateRequestPostDto, imgFile)
    }

    @GetMapping("/{lectureId}")
    @ResponseStatus(HttpStatus.OK)
    fun getPost(@PathVariable lectureId: Long, @RequestParam type: PostType?, @RequestParam page: Int): List<ResponsePostsDto> {
        return postService.getPosts(lectureId, type, page)
    }

    @GetMapping("/search/{keyword}")
    @ResponseStatus(HttpStatus.OK)
    fun searchPosts(@PathVariable keyword: String, @RequestParam page: Int): List<ResponsePostsDto> {
        return postService.searchPosts(keyword, page)
    }

    @GetMapping("/{postId}/scrap")
    @ResponseStatus(HttpStatus.OK)
    fun scrapPost(@PathVariable postId: Long, @RequestParam schoolId: String) {
        return postService.scrapPost(schoolId, postId)
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    fun getPostDetails(@PathVariable postId: Long): ResponsePostDto {
        return postService.getPostDetails(postId)
    }
}
