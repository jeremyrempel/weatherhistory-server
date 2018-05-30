package app.weatherhistory.server

import org.springframework.data.repository.Repository

interface LocationRepository : Repository<Location, Long> {
    fun findTop20ByNameContainsIgnoreCase(name: String): Iterable<Location>
}