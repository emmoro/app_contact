package br.com.elton.api.adapters.persistence.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name="email")
data class EmailEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(name = "email")
    var email: String,

    @Column(name = "active")
    var active: Boolean,

    @Column(name = "creation_date")
    var creationDate: LocalDateTime,

    @Column(name = "update_date")
    var updateDate: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "contact_id")
    var contact: ContactEntity?)
{
    constructor() : this(null, "", true, LocalDateTime.now(),
        LocalDateTime.now(), null)
}