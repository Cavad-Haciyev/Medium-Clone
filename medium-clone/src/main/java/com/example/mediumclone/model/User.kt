package com.example.mediumclone.model

import java.util.*
import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    var id: UUID = UUID.randomUUID(),
    @Column(unique = true)
    var userName: String,
    @Column(unique = true)
    var email: String,
    var biography: String,
    var password: String,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role", joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: List<Role>,
    @OneToMany(mappedBy = "user")
    var userPosts: List<Post>,
    @ManyToMany
    @JoinTable(
        name = "savedPost",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "post_id")]
    )
    var savedPost: List<Post>,
    @ManyToMany
    @JoinTable(
        name = "following",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "following_user")]
    )
    var followings: List<User>,

    var activate: Boolean = false,
    @OneToOne(mappedBy = "user")
    var forgotPasswordToken: ForgotPasswordToken

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (userName != other.userName) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (roles != other.roles) return false
        if (userPosts != other.userPosts) return false
        if (savedPost != other.savedPost) return false
        if (followings != other.followings) return false
        if (activate != other.activate) return false
        if (forgotPasswordToken != other.forgotPasswordToken) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + userName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + roles.hashCode()
        result = 31 * result + userPosts.hashCode()
        result = 31 * result + savedPost.hashCode()
        result = 31 * result + followings.hashCode()
        result = 31 * result + activate.hashCode()
        result = 31 * result + forgotPasswordToken.hashCode()
        return result
    }
}
