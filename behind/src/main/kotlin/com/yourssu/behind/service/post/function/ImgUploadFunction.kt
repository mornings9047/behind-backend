package com.yourssu.behind.service.post.function

import com.yourssu.behind.exception.post.InvalidFileTypeException
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

class ImgUploadFunction {
    fun storeImg(file: MultipartFile): String {
        val directory = File("./uploads")
        var originalFileName = file.originalFilename
        val extPos = originalFileName!!.lastIndexOf(".")
        val ext = originalFileName?.substring(extPos + 1)
        originalFileName = originalFileName.substring(0, extPos)
        val fileName: String = originalFileName + UUID.randomUUID().toString()

        if (!directory.exists())
            directory.mkdir()

        if (ext != "jpeg" && ext != "jpg" && ext != "png")
            throw InvalidFileTypeException()

        val filePath = "${directory.absolutePath}/${fileName}.${ext}"
        val target = File(filePath)
        file.transferTo(target)

        return "img/${fileName}.jpeg"
    }
}
