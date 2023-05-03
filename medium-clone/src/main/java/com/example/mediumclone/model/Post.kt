package com.example.mediumclone.model

import com.example.mediumclone.core.auditable.Auditable
import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.ToString
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "post")
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    var id: UUID = UUID.randomUUID(),
    var imageName: String,
    var imageType: String,
    @Column(name = "image", unique = false, nullable = false, length = 100000)
    var image: ByteArray,
    var tittle: String,
    @Lob
    @Column(columnDefinition = "TEXT")
    var description: String,
    var activate: Boolean,
    @OneToMany(mappedBy = "post")
    @ToString.Exclude
    @JsonIgnore
    var comments: List<Comment>,
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,
    @ElementCollection
    var postLikes:List<UUID>
) : Auditable() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (id != other.id) return false
        if (imageName != other.imageName) return false
        if (imageType != other.imageType) return false
        if (!image.contentEquals(other.image)) return false
        if (tittle != other.tittle) return false
        if (description != other.description) return false
        if (activate != other.activate) return false
        if (comments != other.comments) return false
        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + imageName.hashCode()
        result = 31 * result + imageType.hashCode()
        result = 31 * result + image.contentHashCode()
        result = 31 * result + tittle.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + activate.hashCode()
        result = 31 * result + comments.hashCode()
        result = 31 * result + user.hashCode()
        return result
    }

}


//private Long id;
//private String title;
//private String description;
//@Column(name = "name")
//private String name;
//
//@Column(name = "type")
//private String type;
//
//@Column(name = "image", unique = false, nullable = false, length = 100000)
//private byte[] image;