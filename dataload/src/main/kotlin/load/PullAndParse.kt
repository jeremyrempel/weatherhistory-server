package load

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

fun pullAndParseCountryData(): List<Map<String, String>> {
    val url = URL(Config.countryUrl)
    val data = mutableListOf<Map<String, String>>()
    BufferedReader(InputStreamReader(url.openStream())).use {
        for (line in it.lines()) {
            data.add(processCountryRow(line))
        }
    }
    return data
}

fun pullAndParseStationData(): List<Map<String, String>> {
    val url = URL(Config.stationUrl)
    val data = mutableListOf<Map<String, String>>()
    BufferedReader(InputStreamReader(url.openStream())).use {
        for (line in it.lines()) {
            data.add(processStationRow(line))
        }
    }
    return data
}


/**
------------------------------
Variable   Columns   Type
------------------------------
ID            1-11   Character
LATITUDE     13-20   Real
LONGITUDE    22-30   Real
ELEVATION    32-37   Real
STATE        39-40   Character
NAME         42-71   Character
GSN FLAG     73-75   Character
HCN/CRN FLAG 77-79   Character
WMO ID       81-85   Character
------------------------------
 */
fun processStationRow(rowData: String): Map<String, String> {
    return rowData.run {
        mapOf(
                "id" to substring(0, 11),
                "lat" to substring(12, 20),
                "long" to substring(21, 30),
                "state" to substring(38, 40).trim().apply { if (isNotEmpty()) this else null },
                "name" to substring(41, 71).trim(),
                "elevation" to substring(31, 37)
        )
    }
}

/**
 * ------------------------------
Variable   Columns   Type
------------------------------
CODE          1-2    Character
NAME         4-50    Character
------------------------------
 */
fun processCountryRow(rowData: String): Map<String, String> {
    return rowData.run {
        mapOf(
                "code" to substring(0, 2),
                "name" to substring(3, this.length).trim()
        )
    }
}