import jdbc.getConnection
import jdbc.loadCities
import jdbc.loadMonthlyAvg
import jdbc.resetReadings
import load.*
import parse.pullAndParseCity
import java.io.InputStreamReader
import java.net.URL


// 1. monthly average (temp high, temp low, rainfall, wind, sun)
// 2. yearly average (temp high, temp low, rainfall, wind, sun)

// ftp://ftp.ncdc.noaa.gov/pub/data/normals/1981-2010/readme.txt

fun main(args: Array<String>) {

    loadCountries()

    println("Loading station data (NOAA)")
    val stationData = pullAndParseStationData()
    loadStations(stationData)
    println("${stationData.size} stations loaded")

    println("Loading cities (GEOCODE)")
    val cityData = pullAndParseCity()
    loadCities(cityData)

    // JFK
    //val stationCode = "USW00094789"
    println("Loading reading data")

    val stationList = getStations()
    resetReadings()
    stationList.forEach {
        println("Loading $it")
        val reading = pullAndParseMonthlyAvg(it)
        if (reading != null) {
            loadMonthlyAvg(it, reading)
        }
    }
}

fun getStations(): List<String> {
    val blah = mutableListOf<String>()
    getConnection().use { conn ->
        val st = conn.createStatement()
        st.executeQuery("select distinct station_code from cities").use { rs ->
            while (rs.next()) {
                blah.add(rs.getString(1))
            }
        }
    }
    return blah
}

fun loadCountries() {

    println("Loading country data (NOAA)")
    val data = pullAndParseCountryData()
    println("${data.size} countries loaded")

    getConnection().use { conn ->
        conn.createStatement().execute("truncate table countries cascade")
        conn.prepareStatement("insert into countries (country_code, name) values (?, ?)").use { ps ->
            data.forEach { row ->
                ps.setString(1, row["code"])
                ps.setString(2, row["name"])
                ps.executeUpdate()
            }
        }

        conn.commit()
    }
}

fun loadStations(data: List<Map<String, String>>) {
    getConnection().use { conn ->

        conn.createStatement().execute("truncate table stations cascade")
        conn.prepareStatement("insert into stations (station_code, country_code, name, location, elevation, state) values (?, ?, ?, ST_SetSRID(ST_MakePoint(?,?),4326), ?, ?)").use { ps ->
            data.forEach { row ->
                ps.setString(1, row["id"])
                ps.setString(2, row["id"]?.substring(0, 2))
                ps.setString(3, row["name"])
                ps.setDouble(4, row["long"]!!.toDouble())
                ps.setDouble(5, row["lat"]!!.toDouble())
                ps.setDouble(6, row["elevation"]?.toDouble() ?: 0.0)
                ps.setString(7, row["state"])

                if (hasValidReadings(row["id"] ?: "")) {
                    ps.executeUpdate()
                } else {
                    println("Skipping station ${row["id"]}. No TMAX")
                }
            }
        }
        conn.commit()
    }
}

private fun hasValidReadings(stationCode: String): Boolean {
    // ensure there is valid TMAX,TMIN for the city
    var isValid = false
    val url = URL(Config.readingUrl + "/$stationCode.dly")
    InputStreamReader(url.openStream()).use {
        it.forEachLine {
            if (it.contains("TMAX")) {
                isValid = true
                return@forEachLine
            }
        }
    }
    return isValid
}