package com.yourssu.behind.service.post.function

import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

class ImgUploadFunction {
    fun storeImg(file: MultipartFile): String {
        val directory: File = File("./uploads")
        val fileName: String = file.originalFilename+UUID.randomUUID().toString()

        if (!directory.exists())
            directory.mkdir()

        var filePath: String = "${directory.absolutePath}/${fileName}.jpeg"
        var target: File = File(filePath)
        file.transferTo(target)

        return "img/${fileName}.jpeg"
    }
}