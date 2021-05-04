package br.com.elton.api.adapters.mapper

import br.com.elton.api.adapters.persistence.entities.ContactEntity
import br.com.elton.api.adapters.persistence.entities.EmailEntity
import br.com.elton.api.utils.Utils
import com.example.grpc.adapters.grpc.*
import io.micronaut.core.util.StringUtils
import io.micronaut.data.model.Page
import java.time.LocalDateTime
import javax.inject.Singleton

@Singleton
class ContactMapperGrpc(private val utils: Utils) {

    fun convertContactEntityToFindByIdContactResponseGrpc(contact: ContactEntity) : FindByIdContactResponse {
        var listEmail = this.createListEmailString(contact.emails)
        return FindByIdContactResponse.newBuilder().apply {
            id = contact.id!!
            name = contact.name
            phone = contact.phone
            emails = listEmail
            creationDate = utils.convertDateToString(contact.creationDate)
            updateDate = utils.convertDateToString(contact.updateDate)
        }.build()
    }

    fun convertSaveContactRequestGrpcToContactEnitty(saveContactRequest: SaveContactRequest) : ContactEntity {
        var listEmail = ArrayList<EmailEntity>()

        for (email in saveContactRequest.emails.split(";")) {
            if (!StringUtils.isEmpty(email)) {
                var emailEntity = EmailEntity(null, email, true, LocalDateTime.now(),
                    LocalDateTime.now(), null)
                listEmail.add(emailEntity)
            }
        }

        return ContactEntity(null, saveContactRequest.name, saveContactRequest.phone, listEmail,
            LocalDateTime.now(), LocalDateTime.now())
    }

    fun convertListContactEntityToFindAllContactsResponseGrpc(contacts: Page<ContactEntity>)
        : FindAllContactsResponse {

        var findAll = FindAllContactsResponse.newBuilder()
        contacts.forEach {
            var listEmail = this.createListEmailString(it.emails)
            findAll.addContacts(Contact.newBuilder().apply {
                id = it.id!!
                name = it.name
                phone = it.phone
                emails = listEmail
                creationDate = utils.convertDateToString(it.creationDate)
                updateDate = utils.convertDateToString(it.updateDate)
            }.build())
        }

        findAll.pageInfo = findAll.pageInfoBuilder.apply {
            totalPages = contacts.totalSize.toInt()
            currentPage = contacts.pageNumber.toInt()
            totalSize = contacts.totalSize.toInt()
        }.build()

        return findAll.build()
    }

    fun convertUpdateContactRequestGrpcToContactEntity(updateContact: UpdateContactRequest) : ContactEntity {
        var listEmail = ArrayList<EmailEntity>()

        for (email in updateContact.emails.split(";")) {
            if (!StringUtils.isEmpty(email)) {
                var emailEntity = EmailEntity(null, email, true, LocalDateTime.now(),
                    LocalDateTime.now(), null)
                listEmail.add(emailEntity)
            }
        }

        return ContactEntity(updateContact.id, updateContact.name, updateContact.phone, listEmail,
            LocalDateTime.now(), LocalDateTime.now())
    }

    fun convertContactEntityToSaveContactResponseGrpc(contact: ContactEntity) : SaveContactResponse {
        var listEmail = this.createListEmailString(contact.emails)
        return SaveContactResponse.newBuilder().apply {
            id = contact.id!!
            name = contact.name
            phone = contact.phone
            emails = listEmail
            creationDate = utils.convertDateToString(contact.creationDate)
            updateDate = utils.convertDateToString(contact.updateDate)
        }.build()
    }

    fun convertContactEntityToUpdateContactResponseGrpc(contact: ContactEntity) : UpdateContactResponse {
        var listEmail = this.createListEmailString(contact.emails)
        return UpdateContactResponse.newBuilder().apply {
            id = contact.id!!
            name = contact.name
            phone = contact.phone
            emails = listEmail
            creationDate = utils.convertDateToString(contact.creationDate)
            updateDate = utils.convertDateToString(contact.updateDate)
        }.build()
    }

    fun convertToDeleteContactResponseGrpc() : DeleteContactResponse {
        return DeleteContactResponse.newBuilder().apply {
            message = "Deleted with Success!"
        }.build()
    }

    private fun createListEmailString(emails : List<EmailEntity>) : String {
        var listString: String = ""
        for (email in emails) {
            if (listString.isEmpty()) {
                listString = email.email
            } else {
                listString += ";" + email.email
            }
        }

        return listString
    }

}