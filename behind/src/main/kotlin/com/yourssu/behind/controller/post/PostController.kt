package com.yourssu.behind.controller.post

import com.yourssu.behind.model.dto.post.request.CreateOrUpdateRequestPostDto
import com.yourssu.behind.model.dto.post.response.ResponsePostsDto
import com.yourssu.behind.model.entity.post.Post
import com.yourssu.behind.model.entity.post.PostType
import com.yourssu.behind.service.post.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/posts")
class PostController @Autowired constructor(val postService: PostService) {

    @PostMapping("/", consumes = [MediaType.ALL_VALUE])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun createPost(@RequestPart imgFile: MultipartFile?,
                   @RequestPart createOrUpdateRequestPostDto: CreateOrUpdateRequestPostDto
    ) {
        postService.createPost(createOrUpdateRequestPostDto, imgFile)
    }


    @GetMapping("/{lectureId}")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun getPost(@PathVariable lectureId: Long, @RequestParam type: PostType?, @RequestParam page: Int): List<ResponsePostsDto> {
        return postService.getPosts(lectureId, type, page)
    }

    @GetMapping("/search/{keyword}")
    fun searchPosts(@PathVariable keyword: String): List<ResponsePostsDto> {
        return postService.searchPosts(keyword)
    }
}
