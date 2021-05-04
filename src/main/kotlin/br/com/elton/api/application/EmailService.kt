package br.com.elton.api.application

import br.com.elton.api.adapters.persistence.entities.EmailEntity
import java.util.*

interface EmailService {

    fun saveEmail(email: EmailEntity) : EmailEntity

    fun findById(id: Long) : Optional<EmailEntity>

    fun findAll() : List<EmailEntity>

    fun deleteByIdEmail(id: Long) : EmailEntity

    fun updateEmail(email: EmailEntity) : EmailEntity

    fun findByContactIdAndActiveTrue(id: Long) : List<EmailEntity>

    fun findByContactId(id: Long) : List<EmailEntity>

    fun deleteByContactId(idContact : Long)

}