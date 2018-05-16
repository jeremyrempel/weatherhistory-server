package com.example.demo

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestMapping
import java.util.concurrent.atomic.AtomicLong


@RestController
class GreetingController {

    val counter = AtomicLong()
    private val template = "Hello, %s!"

    @RequestMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String): Greeting {
        return Greeting(counter.incrementAndGet(),
                String.format(template, name))
    }
}