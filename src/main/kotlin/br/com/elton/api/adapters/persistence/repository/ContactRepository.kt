package br.com.elton.api.adapters.persistence.repository

import br.com.elton.api.adapters.persistence.entities.ContactEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import javax.transaction.Transactional

@Repository
@Transactional
interface ContactRepository : PageableRepository<ContactEntity, Long> {
}