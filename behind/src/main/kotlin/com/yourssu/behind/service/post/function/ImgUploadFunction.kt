package com.yourssu.behind.service.post.function

import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

class ImgUploadFunction {
    fun storeImg(file: MultipartFile): String {
        val directory = File("../uploads")
        val fileName: String = file.originalFilename + UUID.randomUUID().toString()

        if (!directory.exists())
            directory.mkdir()

        val filePath = "${directory.absolutePath}/${fileName}.jpeg"
        val target = File(filePath)
        file.transferTo(target)

        return "img/${fileName}.jpeg"
    }
}
