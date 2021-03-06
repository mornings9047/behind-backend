package com.yourssu.behind.model.entity.post

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class PostPage(nowPage: Int) : PageRequest(nowPage, 5, Sort.by("id").descending())
