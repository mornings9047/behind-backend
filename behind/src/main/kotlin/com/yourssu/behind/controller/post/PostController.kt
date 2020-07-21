package com.yourssu.behind.controller.post

import com.yourssu.behind.model.dto.post.request.CreateOrUpdateRequestPostDto
import com.yourssu.behind.model.dto.post.response.ResponsePostDto
import com.yourssu.behind.service.post.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/posts")
class PostController @Autowired constructor(val postService: PostService) {

    @PostMapping("/", consumes = [MediaType.ALL_VALUE])
    fun createPost(@RequestPart imgFile: MultipartFile,
                   @RequestPart createOrUpdateRequestPostDto: CreateOrUpdateRequestPostDto
    ): Unit {
        postService.createPost(createOrUpdateRequestPostDto, imgFile)
    }

    @PostMapping("/img")
    fun createPostImg(@RequestPart("img") imgFile: MultipartFile): String {
        return postService.createPostImg(imgFile)
    }

    @GetMapping("/{postId}")
    fun getPost(@PathVariable postId: Long): ResponsePostDto {
        return postService.findPostById(postId)
    }

    @GetMapping("/")
    fun getAllPostList(@RequestParam lecture: String) {

    }
}