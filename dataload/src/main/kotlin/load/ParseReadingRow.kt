package load

import data.MonthlyAverage
import java.io.BufferedReader
import java.io.InputStreamReader
import java.math.BigDecimal
import java.net.URL


private data class RawReadingRow(val year: Int, val month: Int, val avgValue: Double, val element: String)

fun pullAndParseMonthlyAvg(id: String): List<MonthlyAverage>? {

    val url = URL("${Config.readingUrl}/$id.dly")
    val rawData = mutableListOf<RawReadingRow>()
    BufferedReader(InputStreamReader(url.openStream())).use {
        for (line in it.lines()) {
            rawData.addAll(parseReadingRow(line))
        }
    }

    val data = createMonthlyAverages(rawData).sortedBy { it.month }

    return if (data.isNotEmpty()) data else null
}

private fun createMonthlyAverages(rawData: List<RawReadingRow>): List<MonthlyAverage> {

    return rawData.groupBy { it.month }.map {
        val tMaxAvg = it.value.filter { it.element == "TMAX" }.map { it.avgValue }.average() / 10
        val tMinAvg = it.value.filter { it.element == "TMIN" }.map { it.avgValue }.average() / 10
        val peravg = it.value.filter { it.element == "PRCP" }.map { it.avgValue }.average() / 10

        MonthlyAverage(it.key, tMaxAvg, tMinAvg, peravg)
    }
}

/**
 * ------------------------------
Variable   Columns   Type
------------------------------
ID            1-11   Character
YEAR         12-15   Integer
MONTH        16-17   Integer
ELEMENT      18-21   Character
VALUE1       22-26   Integer
MFLAG1       27-27   Character
QFLAG1       28-28   Character
SFLAG1       29-29   Character
VALUE2       30-34   Integer
MFLAG2       35-35   Character
QFLAG2       36-36   Character
SFLAG2       37-37   Character
.           .          .
.           .          .
.           .          .
VALUE31    262-266   Integer
MFLAG31    267-267   Character
QFLAG31    268-268   Character
SFLAG31    269-269   Character
------------------------------

TMAX: max temperature
TMIN: min tempoerature
TSUN: total sun (percent)
PRCP: total precipitation
AWND = Average daily wind speed (tenths of meters per second)

 */
private fun parseReadingRow(rowData: String): List<RawReadingRow> {

    val data = mutableListOf<RawReadingRow>()
    val element = rowData.substring(17, 21)
    if (arrayOf("TMAX", "TMIN", "TSUN", "AWND", "PRCP").contains(element)) {

        val year = rowData.substring(11, 15).toInt()
        val month = rowData.substring(15, 17).toInt()

        val v = mutableListOf<Int>()
        for (i in 1..31) {

            val increment = 8
            val start = 21 + ((i - 1) * increment)
            val end = start + increment - 1

            val a = rowData?.substring(start, end).trim().toIntOrNull()
            if (a != null && a != -9999) {
                v.add(a.toInt())
            }
        }

        if (v.isNotEmpty()) {
            val avg = v.average()
            data.add(RawReadingRow(year, month, avg, element))
        }
    }

    return data
}