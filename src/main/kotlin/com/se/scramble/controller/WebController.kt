package com.se.scramble.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WebController {

    @RequestMapping("/index")
    fun index() : String = "index"
}