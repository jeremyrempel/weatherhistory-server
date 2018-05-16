package jdbc

import data.MonthlyAverage

fun resetReadings() {
    getConnection().use {
        it.createStatement().execute("truncate table monthlyavg cascade")
        it.commit()
    }
}

fun loadMonthlyAvg(station: String, data: List<MonthlyAverage>) {
    getConnection().use { conn ->
        conn.prepareStatement("INSERT INTO monthlyavg (station_code, month, maxtemp, mintemp, precip) VALUES (?, ?, ?, ?, ?)").use { ps ->
            ps.setString(1, station)

            data.forEach {
                ps.setInt(2, it.month)
                ps.setDouble(3, it.maxTemp)
                ps.setDouble(4, it.minTemp)
                ps.setDouble(5, it.percipitationAvg)
                ps.executeUpdate()
            }
        }
        conn.commit()
    }
}