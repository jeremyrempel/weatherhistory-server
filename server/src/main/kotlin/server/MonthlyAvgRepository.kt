package server

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface MonthlyAvgRepository : CrudRepository<MonthlyAvg, Long> {
    @Query("SELECT a FROM MonthlyAvg a WHERE a.station_code = ?1")
    fun findByStationCode(stationcode: String): List<MonthlyAvg>
}