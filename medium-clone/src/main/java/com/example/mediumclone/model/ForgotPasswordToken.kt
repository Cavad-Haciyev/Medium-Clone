package com.example.mediumclone.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
data class ForgotPasswordToken @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @Column(columnDefinition = "BINARY(16)")
    var token: UUID = UUID.randomUUID(),
    var createdDate: LocalDateTime,
    var expireDate: LocalDateTime,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user", referencedColumnName = "id")
    var user: User

) {}
