package br.com.elton.api.application.impl

import br.com.elton.api.adapters.persistence.entities.EmailEntity
import br.com.elton.api.adapters.persistence.repository.EmailRepository
import br.com.elton.api.application.EmailService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Singleton

@Singleton
class EmailServiceImpl(private val emailRepository: EmailRepository)
    : EmailService {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(EmailServiceImpl::class.java.name)
    }

    override fun saveEmail(email: EmailEntity): EmailEntity {
        return emailRepository.save(email)
    }

    override fun findById(id: Long): Optional<EmailEntity> {
        return emailRepository.findById(id)
    }

    override fun findByContactIdAndActiveTrue(id: Long) : List<EmailEntity> {
        return emailRepository.findByContactIdAndActiveTrue(id)
    }

    override fun findByContactId(id: Long) : List<EmailEntity> {
        return emailRepository.findByContactId(id)
    }

    override fun deleteByContactId(idContact : Long) {
        emailRepository.deleteByContactId(idContact)
    }

    override fun updateEmail(email: EmailEntity): EmailEntity {
        return emailRepository.update(email)
    }

    override fun findAll(): List<EmailEntity> {
        TODO("Not yet implemented")
    }

    override fun deleteByIdEmail(id: Long): EmailEntity {
        TODO("Not yet implemented")
    }

}