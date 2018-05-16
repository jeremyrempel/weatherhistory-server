package load

object Config {
    val dbhost = "localhost"
    val dbuser = "weather"
    val dbpass = "weather"
    val dbname = "weather"

    //val stationUrl = "ftp://ftp.ncdc.noaa.gov/pub/data/ghcn/daily/ghcnd-stations.txt"
    //val countryUrl = "ftp://ftp.ncdc.noaa.gov/pub/data/ghcn/daily/ghcnd-countries.txt"
    val stationUrl = "file:///Users/jrempel/temp/noaa/ghcnd-stations.txt"
    val countryUrl = "file:///Users/jrempel/temp/noaa/ghcnd-countries.txt"
    val readingUrl = "file:///Users/jrempel/temp/noaa/ghcnd_all"
    val cityUrl = "file:///Users/jrempel/temp/geoname/cities15000.txt"

    val stationUrlNormals = "file:///Users/jrempel/temp/noaa/normals/allstations.txt"
}