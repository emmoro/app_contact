package br.com.elton.api.application.impl

import br.com.elton.api.adapters.persistence.entities.ContactEntity
import br.com.elton.api.adapters.persistence.repository.ContactRepository
import br.com.elton.api.application.ContactService
import br.com.elton.api.application.EmailService
import br.com.elton.api.application.exception.ContactNotFoundException
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.util.*
import javax.inject.Singleton

@Singleton
class ContactServiceImpl (
    private val contactRepository: ContactRepository,
    private val emailService: EmailService)
    : ContactService  {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ContactServiceImpl::class.java.name)
    }

    override fun saveContact(contact: ContactEntity): ContactEntity {
        var contactoEntity = contactRepository.save(contact)
        for (email in contactoEntity.emails) {
            email.contact = ContactEntity(
                contactoEntity.id, contactoEntity.name, contactoEntity.phone,
                listOf(), contactoEntity.creationDate, contactoEntity.updateDate
            )
            emailService.saveEmail(email)
        }

        return contactoEntity
    }

    override fun findById(id: Long): Optional<ContactEntity> {
        var contact = contactRepository.findById(id)
        if (contact.isEmpty) {
            throw ContactNotFoundException(id, "Contact Id $id not found!")
        }

        var listEmail = emailService.findByContactIdAndActiveTrue(id)
        contact.get().emails = listEmail

        return contact
    }

    override fun deleteByIdContact(id: Long) {
        emailService.deleteByContactId(id)
        contactRepository.deleteById(id)
    }

    override fun findAll(page: Int, size: Int): Page<ContactEntity> {
        var listContacts = contactRepository.findAll(Pageable.from(page, size))
        for (contact in listContacts) {
            var listEmail = emailService.findByContactIdAndActiveTrue(contact.id!!)
            contact.emails = listEmail
        }

        return listContacts
    }

    override fun updateContact(contact: ContactEntity): ContactEntity {
        var updateContact = contactRepository.update(contact)
        this.findEmailAndChangeEmail(contact)

        var listEmail = emailService.findByContactIdAndActiveTrue(contact.id!!)
        updateContact.emails = listEmail

        return updateContact
    }

    private fun findEmailAndChangeEmail(contact: ContactEntity) {
        var listEmail = emailService.findByContactId(contact.id!!)

        for (email in contact.emails) {
            var findEmail = listEmail.stream().filter { em -> em.email == email.email }.findFirst()
            if (findEmail.isPresent && !findEmail.get().active) {
                findEmail.get().active = true
                findEmail.get().updateDate = LocalDateTime.now()
                emailService.updateEmail(findEmail.get())
            } else if (findEmail.isEmpty) {
                email.contact = ContactEntity(
                    contact.id, contact.name, contact.phone,
                    listOf(), contact.creationDate, contact.updateDate
                )
                emailService.saveEmail(email)
            }
        }

        for (email in listEmail) {
            var findEmail = contact.emails.stream().filter { em -> em.email == email.email }.findFirst()
            if ((findEmail.isEmpty || !findEmail.isPresent) && email.active) {
                email.active = false
                email.updateDate = LocalDateTime.now()
                emailService.updateEmail(email)
            }
        }
    }

}