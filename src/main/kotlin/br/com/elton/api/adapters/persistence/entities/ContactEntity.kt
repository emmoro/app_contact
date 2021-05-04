package br.com.elton.api.adapters.persistence.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name="contact")
data class ContactEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(name = "name")
    var name: String,

    @Column(name = "phone")
    var phone: String,

    @OneToMany(mappedBy = "contact")
    var emails: List<EmailEntity>,

    @Column(name = "creation_date")
    var creationDate: LocalDateTime,

    @Column(name = "update_date")
    var updateDate: LocalDateTime)
{
    constructor(): this(null, "", "", listOf(),
        LocalDateTime.now(), LocalDateTime.now())
}