package jdbc

import data.Station

fun resetStations() {
    getConnection().use {
        it.createStatement().execute("truncate table stations cascade")
        it.commit()
    }
}

fun loadStations(data: List<Station>) {
    getConnection().use { conn ->

        conn.prepareStatement("insert into stations (station_code, country_code, name, location, elevation, state) values (?, ?, ?, ST_SetSRID(ST_MakePoint(?,?),4326), ?, ?)").use { ps ->
            data.forEach { row ->
                ps.setString(1, row.stationCode)
                ps.setString(2, row.countryCode)
                ps.setString(3, row.name)
                ps.setDouble(4, row.latitude)
                ps.setDouble(5, row.longitude)
                ps.setDouble(6, row.elevation)
                ps.setString(7, row.state)

                ps.executeUpdate()
            }
        }
        conn.commit()
    }
}