package jdbc

import load.Config
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

fun getConnection(): Connection {
    val url = "jdbc:postgresql://${Config.dbhost}/${Config.dbname}"
    val props = Properties()
    props.setProperty("user", Config.dbuser)
    props.setProperty("password", Config.dbpass)
    val con = DriverManager.getConnection(url, props)
    con.autoCommit = false
    return con
}