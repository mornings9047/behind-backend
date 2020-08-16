package com.yourssu.behind.service.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class MailService @Autowired constructor(private val mailSender: JavaMailSender, private val authenticationMailTokenService: AuthenticationMailTokenService, private val jwtService: JwtService) {


    @Async
    fun sendMail(userEmail: String) {
        var simpleMessage: SimpleMailMessage = SimpleMailMessage()
        simpleMessage.setTo("${userEmail}@soongsil.ac.kr")
        simpleMessage.setFrom("jamma.urssu@gmail.com")
        simpleMessage.setSubject("Behind 회원가입 인증 메일입니다.")
        simpleMessage.setText("https://localhost:8080/auth/user?token=${authenticationMailTokenService.createAuthenticationToken(jwtService.getUser().schoolId)}")
        mailSender.send(simpleMessage)
    }
}
