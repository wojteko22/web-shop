package com.example.webshop.service.mail

import com.example.webshop.entity.User
import com.example.webshop.entity.UserStatus
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(private val mailSender: JavaMailSender,
                  private val messageFactory: MessageFactory) {

    fun sendSignUpMessage(user: User, userStatus: UserStatus) {
        val smm = SimpleMailMessage()
        smm.setTo(user.email)
        smm.subject = "Web-Shop rejestracja"
        smm.from = "web-shop@kamo.ovh"
        smm.text = messageFactory.getSignUpMessage(getSignUpMessageType(user), userStatus)
        mailSender.send(smm)
    }

    fun sendReminderToken(email: String, token: String) {
        val smm = SimpleMailMessage()
        smm.setTo(email)
        smm.subject = "Web-Shop przypomnienie hasła"
        smm.from = "web-shop@kamo.ovh"
        smm.text = "Witaj! Twój kod : "+token
        mailSender.send(smm)
    }

    private fun getSignUpMessageType(user: User): SignUpMessageType {
        when(user.role.role) {
            "SHOP_OWNER" -> return SignUpMessageType.SHOP_SIGN_UP
            "SELLER" -> return SignUpMessageType.SELLER_SIGN_UP
            "CUSTOMER" -> return SignUpMessageType.USER_SIGN_UP
            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}