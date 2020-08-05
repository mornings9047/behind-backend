package com.yourssu.behind.service.postTest

/*
  schoolId가 2020인 유저가 DB에있다고 가정했습니다.
 */
/*
@SpringBootTest
class PostServiceTest @Autowired constructor(val postService: PostService, val postRepository: PostRepository, val scrapRepository: ScrapRepository, val professorRepository: ProfessorRepository, val lectureRepository: LectureRepository) {

    val testCreateOrUpdateRequestPostDto =
            CreateOrUpdateRequestPostDto(schoolId = "20202020",
                    title = "testPost3",
                    type = PostType.FREE,
                    content = "This is PostCreation Test",
                    lectureId = 1)


    val existId: Long = 1
    val fakeId: Long = -1

    @Test
    @Transactional
    fun createPostTest() {
        postService.createPost(testCreateOrUpdateRequestPostDto, null)
        Assertions.assertNotNull(postRepository.findByTitle(testCreateOrUpdateRequestPostDto.title))
    }

    @Test
    @Transactional
    fun getPostTest() {
        Assertions.assertNotNull(postService.getPosts(existId, null, 0))
        Assertions.assertThrows(LectureNotExistException::class.java
        ) { postService.getPosts(fakeId, null, 0) }
    }

    @Test
    @Transactional
    fun scrapTest() {
        postService.scrapPost("20202020", existId)
        val post = postRepository.findById(existId).orElseThrow { PostNotExistException() }

        Assertions.assertNotEquals(0, scrapRepository.countAllByScrapPost(post))
    }

    @Test
    @Transactional
    fun createPostsTest() {
        val professor = Professor(name = "JOC")
        professorRepository.save(professor)
        lectureRepository.save(Lecture(major = "computer", year = "20", semester = LectureSemester.SPRING, courseName = "OOP", professor = professor))

        for (i in 1..100)
            postService.createPost(CreateOrUpdateRequestPostDto(schoolId = "20202020", title = "title$i", type = PostType.FREE, content = "www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239https://www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239$i", lectureId = 422), imgFile = null)
    }

    @Test
    @Transactional
    fun searchPostsTest() {
        for (i in 1..20)
            postService.createPost(CreateOrUpdateRequestPostDto(schoolId = "20202020", title = "FREE$i", type = PostType.FREE, content = "www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239https://www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239$i", lectureId = 9), imgFile = null)
        for (i in 1..20)
            postService.createPost(CreateOrUpdateRequestPostDto(schoolId = "20202020", title = "QUES$i", type = PostType.QUESTION, content = "www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239https://www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239$i", lectureId = 9), imgFile = null)
        for (i in 1..20)
            postService.createPost(CreateOrUpdateRequestPostDto(schoolId = "20202020", title = "INFO$i", type = PostType.INFORMATION, content = "www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239https://www.figma.com/file/gEIPNEmE2KpymmVGFbHdNP/%EB%B9%84%ED%95%98%EC%9D%B8%EB%93%9C---Android?node-id=59%3A1239$i", lectureId = 9), imgFile = null)

        for (responsePostDto in postService.searchPosts(keyword = "FREE", type = null, page = 0))
            println("${responsePostDto.type}   ${responsePostDto.title}")
        for (responsePostDto in postService.searchPosts(keyword = "QUES", type = null, page = 0))
            println("${responsePostDto.type}   ${responsePostDto.title}")
        for (responsePostDto in postService.searchPosts(keyword = "INFO", type = null, page = 0))
            println("${responsePostDto.type}   ${responsePostDto.title}")
    }

    @Test
    fun getPostDetailsTest() {
        println(postService.getPostDetails(160).content)
    }

    @Test
    @Transactional
    fun deletePostTest() {
        var post = postRepository.findByTitle("testPost3").orElseThrow { PostNotExistException() }
        post[0].id?.let { postService.deletePost(it) }
        Assertions.assertEquals(true, post[0].deletePost)

    }
}

 */
