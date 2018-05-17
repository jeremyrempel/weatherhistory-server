package server

import org.springframework.data.repository.CrudRepository

interface LocationRepository : CrudRepository<Location, Long> {
    fun findByNameIgnoreCaseContaining(query: String): List<Location>
}