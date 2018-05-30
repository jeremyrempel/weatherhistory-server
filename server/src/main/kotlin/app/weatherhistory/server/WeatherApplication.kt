package app.weatherhistory.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.rest.core.config.RepositoryRestConfiguration

@SpringBootApplication
@EnableCaching
class WeatherApplication

fun main(args: Array<String>) {
    val app = runApplication<WeatherApplication>(*args)

    app.getBean(RepositoryRestConfiguration::class.java).setExposeRepositoryMethodsByDefault(false)
}
