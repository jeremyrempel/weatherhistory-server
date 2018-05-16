package parse

import data.Station
import load.Config
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

fun pullAndParseStationNormalData(): List<Station> {
    val url = URL(Config.stationUrlNormals)
    val data = mutableListOf<Station>()
    BufferedReader(InputStreamReader(url.openStream())).use {
        for (line in it.lines()) {

            data.add(line.run {
                Station(
                        substring(0, 11),
                        substring(0, 2),
                        substring(41, 71).trim(),
                        substring(12, 20).toDouble(),
                        substring(12, 20).toDouble(),
                        substring(32, 37).toDouble(),
                        substring(38, 40)
                )
            })
        }
    }
    return data
}