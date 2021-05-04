package br.com.elton.api.application

import br.com.elton.api.adapters.persistence.entities.ContactEntity
import io.micronaut.data.model.Page
import java.util.*

interface ContactService {

    fun saveContact(contact: ContactEntity) : ContactEntity

    fun findById(id: Long) : Optional<ContactEntity>

    fun findAll(page: Int, size: Int) : Page<ContactEntity>

    fun deleteByIdContact(id: Long)

    fun updateContact(contact: ContactEntity) : ContactEntity

}