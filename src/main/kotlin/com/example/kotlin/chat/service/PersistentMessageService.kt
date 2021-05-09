package com.example.kotlin.chat.service

import com.example.kotlin.chat.asDomainObject
import com.example.kotlin.chat.asRendered
import com.example.kotlin.chat.asViewModel
import com.example.kotlin.chat.mapToViewModel
import com.example.kotlin.chat.repository.ContentType
import com.example.kotlin.chat.repository.Message
import com.example.kotlin.chat.repository.MessageRepository
import kotlinx.coroutines.flow.*
import org.springframework.context.annotation.Primary
import org.springframework.data.annotation.Persistent
import org.springframework.stereotype.Service
import java.net.URL

@Service
@Primary
class PersistentMessageService(val messageRepository: MessageRepository) : MessageService {

    val sender: MutableSharedFlow<MessageVM> = MutableSharedFlow()

    override fun latest(): Flow<MessageVM> =
        messageRepository.findLatest().mapToViewModel()

    override fun after(messageId: String): Flow<MessageVM> {
        return messageRepository.findLatest(messageId)
            .mapToViewModel()
    }

    override fun stream(): Flow<MessageVM> = sender

    override suspend fun post(messages: Flow<MessageVM>) =
        messages
            .onEach { sender.emit(it.asRendered()) }
            .map { it.asDomainObject() }
            .let { messageRepository.saveAll(it) }
            .collect()
}