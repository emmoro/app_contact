package br.com.elton.api.adapters.grpc

import br.com.elton.api.adapters.mapper.ContactMapperGrpc
import br.com.elton.api.application.ContactService
import com.example.grpc.adapters.grpc.*
import io.grpc.Status
import io.grpc.stub.StreamObserver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class ContactEndpoint(private val contactMapperGrpc: ContactMapperGrpc,
                      private val contactService: ContactService)
    : ContactServiceGrpc.ContactServiceImplBase() {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ContactEndpoint::class.java.name)
    }

    override fun findByIdContact(request: FindByIdContactRequest?,
                                 responseObserver: StreamObserver<FindByIdContactResponse>) {
        logger.info("Find Contact by Id : {${request!!.id}}")
        try {
            var contact = contactService.findById(request!!.id)
            var findByIdContactResponse = contactMapperGrpc.convertContactEntityToFindByIdContactResponseGrpc(contact.get())
            responseObserver.onNext(findByIdContactResponse)
            responseObserver.onCompleted()
        } catch (e: Exception) {
            logger.error("Contact Not Found to ID : {${request.toString()}}")
            throw Status.NOT_FOUND
                .withDescription("Contact Not Found!")
                .asException()
        }
    }

    override fun saveContact(request: SaveContactRequest?,
                             responseObserver: StreamObserver<SaveContactResponse>?) {
        logger.info("Save Contact : {${request.toString()}}")
        var contact = contactMapperGrpc.convertSaveContactRequestGrpcToContactEnitty(request!!)
        contact = contactService.saveContact(contact)

        responseObserver!!.onNext(contactMapperGrpc.convertContactEntityToSaveContactResponseGrpc(contact))
        responseObserver.onCompleted()
    }

    override fun findAllContacts(request: FindAllContactsRequest?,
        responseObserver: StreamObserver<FindAllContactsResponse>?) {
        logger.info("Find All Contact.")
        var listContacts = contactService.findAll(request!!.page, request!!.size)

        responseObserver!!.onNext(
            contactMapperGrpc.convertListContactEntityToFindAllContactsResponseGrpc(listContacts))
        responseObserver.onCompleted()
    }

    override fun updateContact(request: UpdateContactRequest?,
        responseObserver: StreamObserver<UpdateContactResponse>?) {
        logger.info("Update the Contact : {${request.toString()}}")

        try {
            var contact = contactService.findById(request!!.id)
            var contactEntity = contactMapperGrpc.convertUpdateContactRequestGrpcToContactEntity(request!!)
            contactEntity.creationDate = contact.get().creationDate
            contactEntity = contactService.updateContact(contactEntity)

            responseObserver!!.onNext(
                contactMapperGrpc.convertContactEntityToUpdateContactResponseGrpc(contactEntity))
            responseObserver.onCompleted()
        } catch (e: Exception) {
            logger.error("Contact Not Found to ID : {${request.toString()}}")
            throw Status.NOT_FOUND
                .withDescription("Contact Not Found!")
                .asException()

        }
    }

    override fun deleteContact(request: DeleteContactRequest?,
        responseObserver: StreamObserver<DeleteContactResponse>?) {
        logger.info("Delete Contact by Id : {${request!!.id}}")

        try {
            var contact = contactService.findById(request!!.id)
            contactService.deleteByIdContact(request.id)
            responseObserver!!.onNext(contactMapperGrpc.convertToDeleteContactResponseGrpc())
            responseObserver.onCompleted()
        } catch (e: Exception) {
            logger.error("Contact Not Found to ID : {${request.toString()}}")
            throw Status.NOT_FOUND
                .withDescription("Contact Not Found!")
                .asException()
        }
    }

}