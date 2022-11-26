package com.se.scramble.domain.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Issues(
    @Id @Column(name = "issues_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val issues_id: Long? = null,

    @Column(name = "content")
    var content: String,

    @Column(name = "deadline")
    var deadline: String,

    @Column(name = "storyPoint")
    var storyPoint: Int,

    @Column(name = "category")
    var category: String,

    @Column(name = "importance")
    var importance: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROJECTS_ID")
    var projects: Projects? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERS_ID")
    var users: Users? = null
) {
}