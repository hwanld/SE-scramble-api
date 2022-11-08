package com.se.scramble

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ScrambleApplication

fun main(args: Array<String>) {
	runApplication<ScrambleApplication>(*args)
}
