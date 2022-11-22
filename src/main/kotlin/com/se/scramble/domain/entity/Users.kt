package com.se.scramble.domain.entity

import java.util.*
import javax.persistence.*

@Entity
data class Users(
    @Id @Column(name = "users_id")
    val users_id: String? = null,

    @Column(name = "password")
    val password: String? = null,

    @Column(name = "nickname")
    val nickname: String,

    @Column
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERS_PROJECTS")
    var projects: MutableSet<Projects> = TreeSet(),

    @Column
    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    var issues: MutableSet<Issues> = TreeSet()

) : Comparable<Users> {
    override fun compareTo(other: Users): Int {
        if (this.users_id!! > other.users_id!!) return 1
        return -1
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}