package com.yourssu.behind.service.post.function

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.yourssu.behind.exception.post.InvalidFileTypeException
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.util.*


@Component
class ImgUploadFunction(@Value("\${cloud.aws.credentials.accessKey}") private val accessKey: String,
                        @Value("\${cloud.aws.credentials.secretKey") private val secretKey: String,
                        @Value("\${cloud.aws.s3.bucket}") private val bucket: String,
                        @Value("\${cloud.aws.region.static}") private val region: String) {

    private val credentials: AWSCredentials = BasicAWSCredentials(this.accessKey, this.secretKey)
    private val s3Client: AmazonS3 = AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withRegion(this.region)
            .build()

    fun s3StoreImg(file: MultipartFile): String {
        var fileName: String? = file.originalFilename
        val ext = FilenameUtils.getExtension(fileName)
        val bytes: ByteArray = com.amazonaws.util.IOUtils.toByteArray(file.inputStream)
        var metaData = ObjectMetadata()
        var byteArrayInputStream = ByteArrayInputStream(bytes)
        metaData.contentLength = bytes.size.toLong()
        metaData.contentType = "image/jpeg"
        fileName = UUID.randomUUID().toString()+fileName

        if (ext != "jpeg" && ext != "jpg" && ext != "png")
            throw InvalidFileTypeException()

        s3Client.putObject(PutObjectRequest(bucket, fileName, byteArrayInputStream, metaData))
        return s3Client.getUrl(bucket, fileName).toString()
    }
}
