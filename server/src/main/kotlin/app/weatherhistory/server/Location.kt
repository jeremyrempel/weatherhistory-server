package app.weatherhistory.server

import javax.persistence.*

@Cacheable
@Table(name = "locations")
@Entity
data class Location(
        @Id
        val stationCode: String,
        val countryCode: String,
        val name: String,
        val state: String?
)