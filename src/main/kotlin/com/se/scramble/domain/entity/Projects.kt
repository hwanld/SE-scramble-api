package com.se.scramble.domain.entity

import java.util.*
import javax.persistence.*

@Entity
data class Projects(
    @Id @Column(name = "projects_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val projects_id: Long? = null,

    @Column(name = "name")
    val name: String = "제목 없음",

    @Column(name = "storyPoint")
    val storyPoint: Int = 0,

    @Column
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROJECTS_USERS")
    var users: MutableSet<Users> = TreeSet(),

    @OneToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    var issues: MutableSet<Issues> = TreeSet()
) {
    override fun hashCode(): Int {
        return super.hashCode()
    }
}