package br.com.elton.api.adapters.persistence.repository

import br.com.elton.api.adapters.persistence.entities.EmailEntity
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import javax.transaction.Transactional

@Repository
@Transactional
interface EmailRepository : PageableRepository<EmailEntity, Long> {

    fun findByContactIdAndActiveTrue(id : Long) : List<EmailEntity>

    fun findByContactId(id : Long) : List<EmailEntity>

    @Query("Delete from EmailEntity e where e.contact.id = :id")
    fun deleteByContactId(id : Long)

}