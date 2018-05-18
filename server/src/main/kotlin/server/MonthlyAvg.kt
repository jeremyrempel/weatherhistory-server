package server

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "monthlyavg")
@Entity
data class MonthlyAvg(
        @Id
        @JsonIgnore
        val id: Int,
        @JsonIgnore
        val station_code: String,
        val month: Int,
        val maxtemp: Double,
        val mintemp: Double,
        val precip: Double
)