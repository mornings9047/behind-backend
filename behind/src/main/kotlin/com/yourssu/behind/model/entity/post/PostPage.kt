package com.yourssu.behind.model.entity.post

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class PostPage(nowPage: Int) : PageRequest(nowPage, 10, Sort.by("id").descending()) {

}