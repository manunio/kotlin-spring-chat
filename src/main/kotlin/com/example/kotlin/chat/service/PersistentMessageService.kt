package com.example.kotlin.chat.service

import com.example.kotlin.chat.asDomainObject
import com.example.kotlin.chat.asViewModel
import com.example.kotlin.chat.mapToViewModel
import com.example.kotlin.chat.repository.ContentType
import com.example.kotlin.chat.repository.Message
import com.example.kotlin.chat.repository.MessageRepository
import org.springframework.context.annotation.Primary
import org.springframework.data.annotation.Persistent
import org.springframework.stereotype.Service
import java.net.URL

@Service
@Primary
class PersistentMessageService(val messageRepository: MessageRepository) : MessageService {
    override fun latest(): List<MessageVM> =
        messageRepository.findLatest().mapToViewModel()

    override fun after(messageId: String): List<MessageVM> {
        return messageRepository.findLatest(messageId)
            .mapToViewModel()
    }

    override fun post(message: MessageVM) {
        messageRepository.save(message.asDomainObject())
    }
}