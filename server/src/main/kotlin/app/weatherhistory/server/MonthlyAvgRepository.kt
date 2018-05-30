package app.weatherhistory.server

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository

interface MonthlyAvgRepository : Repository<MonthlyAvg, Long> {
    @Query("SELECT a FROM MonthlyAvg a WHERE a.station_code = ?1")
    fun findByStationCode(stationcode: String): List<MonthlyAvg>
}