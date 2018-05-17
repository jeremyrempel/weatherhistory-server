package server

import javax.persistence.*

@Table(name = "locations")
@Entity
data class Location(
        @Id
        val stationCode: String,
        val countryCode: String,
        val name: String,
        val state: String?
)