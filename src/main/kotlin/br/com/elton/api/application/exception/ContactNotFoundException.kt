package br.com.elton.api.application.exception

data class ContactNotFoundException(
    val id: Long,
    override val message: String = "Contact Id $id not found!"
) : Exception(message)