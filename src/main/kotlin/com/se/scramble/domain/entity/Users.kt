package com.se.scramble.domain.entity

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Users(
    @Id @Column(name = "users_id")
    val users_id: String? = null,

    @Column(name = "password")
    val password: String? = null,

    @Column(name = "nickname")
    val nickname: String

) {

}