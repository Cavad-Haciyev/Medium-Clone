package com.example.mediumclone.model

import com.example.mediumclone.core.auditable.Auditable
import java.util.*
import javax.persistence.*

@Entity
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    var id: UUID = UUID.randomUUID(),
    var description: String,
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    var post: Post,
    var userId: UUID,
    var activate: Boolean
) : Auditable() {
}

