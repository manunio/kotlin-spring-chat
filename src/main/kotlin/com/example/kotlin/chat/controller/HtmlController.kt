package com.example.kotlin.chat.controller

import com.example.kotlin.chat.service.MessageService
import com.example.kotlin.chat.service.MessageVM
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController(val messageService: MessageService) {

    @GetMapping("/")
    suspend fun index(model: Model): String {
        // implemented in src/main/resources/templates/chatrs.html
        return "chatrs"
    }
}