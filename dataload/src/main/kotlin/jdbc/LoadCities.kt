package jdbc

import load.City

fun resetCities() {
    getConnection().use {
        it.createStatement().execute("truncate table cities cascade")
        it.commit()
    }
}

fun loadCities(cityData: List<City>) {

    val toleranceKm = 100

    getConnection().use { conn ->

        val s = "SELECT station_code FROM stations\n" +
                "WHERE ST_DWithin(stations.location, st_makepoint(?,?), ?)\n" +
                "ORDER BY stations.location <-> st_setsrid(st_makepoint(?,?), 4326)\n" +
                "LIMIT 1"

        conn.prepareStatement(s).use { ps ->

            cityData.forEach { city ->
                ps.setDouble(1, city.long)
                ps.setDouble(2, city.lat)
                // tolerance (m)
                ps.setInt(3, toleranceKm * 1000)
                ps.setDouble(4, city.long)
                ps.setDouble(5, city.lat)

                ps.executeQuery().use { rs ->
                    if (rs.next()) {
                        val stationCode = rs.getString(1)

                        val ps2 = conn.prepareStatement("INSERT INTO cities (geonameid,name,location,country_code,station_code) VALUES (?,?,ST_SetSRID(ST_MakePoint(?,?),4326),?,?) ").use { ps2 ->
                            ps2.setInt(1, city.geocodeid)
                            ps2.setString(2, city.name)
                            ps2.setDouble(3, city.long)
                            ps2.setDouble(4, city.lat)
                            ps2.setString(5, city.countryCode)
                            ps2.setString(6, stationCode)

                            ps2.executeUpdate()
                        }

                    } else {
                        println("no station found for city ${city.name}")
                    }

                }
            }
        }
        conn.commit()
    }
}