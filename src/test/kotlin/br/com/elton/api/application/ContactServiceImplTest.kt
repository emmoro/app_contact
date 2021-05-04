package br.com.elton.api.application

import br.com.elton.api.adapters.persistence.repository.ContactRepository
import br.com.elton.api.adapters.persistence.repository.EmailRepository
import br.com.elton.api.application.exception.ContactNotFoundException
import br.com.elton.api.application.impl.ContactServiceImpl
import br.com.elton.api.application.impl.EmailServiceImpl
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class ContactServiceImplTest {

    private val contactRepository = mockk<ContactRepository>()

    private val emailRepository = mockk<EmailRepository>()

    var emailService = EmailServiceImpl(emailRepository)

    var contactService = ContactServiceImpl(contactRepository, emailService)

    private val contactId = 5L;

    @Test
    fun `when contact does not exist` () {
        every { contactRepository.findById(any()) } returns Optional.empty()

        val contactNotFoundException = Assertions.assertThrows(ContactNotFoundException::class.java) {
            contactService.findById(contactId)
        }

        val exception = contactNotFoundException.message
        Assertions.assertEquals("Contact Id $contactId not found!", exception.toString())
    }

}