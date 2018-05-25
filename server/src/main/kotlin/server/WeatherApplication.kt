package server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.rest.core.config.RepositoryRestConfiguration

@SpringBootApplication
class WeatherApplication

fun main(args: Array<String>) {
    val app = runApplication<WeatherApplication>(*args)

    app.getBean(RepositoryRestConfiguration::class.java).setExposeRepositoryMethodsByDefault(false)
}
