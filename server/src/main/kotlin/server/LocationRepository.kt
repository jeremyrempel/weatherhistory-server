package server

import org.springframework.data.repository.Repository

interface LocationRepository : Repository<Location, Long> {
    fun findAll(): Iterable<Location>
}