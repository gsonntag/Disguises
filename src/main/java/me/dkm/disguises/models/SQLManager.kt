package me.dkm.disguises.models

import me.dkm.disguises.Disguises
import org.bukkit.entity.Player
import java.lang.NullPointerException
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class SQLManager(val plugin: Disguises) {

    companion object {
        const val TABLE_NAME = "disguises"
    }

    private val connection: Connection

    init {
        Class.forName("com.mysql.jdbc.Driver")
        val host = plugin.config.getString("host") ?: throw NullPointerException("host")
        val port = plugin.config.getString("port") ?: throw NullPointerException("port")
        val database = plugin.config.getString("database") ?: throw NullPointerException("db")
        val username = plugin.config.getString("username") ?: throw NullPointerException("username")
        val password = plugin.config.getString("password") ?: throw NullPointerException("passwd")
        connection = DriverManager.getConnection("jdbc:mysql://$host:$port/$database", username, password)
        executeStatement(
                "create table if not exists $TABLE_NAME(" +
                        "active tinyint," +
                        "disguisename varchar(255)," +
                        "realname varchar(255)," +
                        "disguiseid varchar(255)," +
                        "realid varchar(255)," +
                        "skindata text," +
                        "skinsig text," +
                        "realskindata text," +
                        "realskinsig text," +
                        "rank varchar(255))"
        )
    }

    private fun executeStatement(sql: String) {
        connection.prepareStatement(sql).executeUpdate()
    }

    fun getDisguise(player: Player): Disguise? {
        val statement = connection.prepareStatement("select * from $TABLE_NAME where realid=?")
        statement.setString(1, player.uniqueId.toString())
        val results = statement.executeQuery()
        if(results.next()) {
            return Disguise(
                    results.getInt("active") == 1,
                    results.getString("disguisename"),
                    results.getString("realname"),
                    UUID.fromString(results.getString("disguiseid")),
                    UUID.fromString(results.getString("realid")),
                    Skin(
                            results.getString("skindata"),
                            results.getString("skinsig")
                    ),
                    Skin(
                          results.getString("realskindata"),
                          results.getString("realskinsig")
                    ),
                    Rank.of(results.getString("rank"))!!
            )
        }
        return null
    }

    fun addDisguise(disguise: Disguise) {
        val deleteOld = connection.prepareStatement("delete from $TABLE_NAME where realid=?")
        deleteOld.setString(1, disguise.realId.toString())
        deleteOld.executeUpdate()
        val statement = connection.prepareStatement(
                "insert into $TABLE_NAME values (?,?,?,?,?,?,?,?,?,?)"
        )
        statement.setInt(1, 1)
        statement.setString(2, disguise.name)
        statement.setString(3, disguise.realName)
        statement.setString(4, disguise.id.toString())
        statement.setString(5, disguise.realId.toString())
        statement.setString(6, disguise.skin.getData())
        statement.setString(7, disguise.skin.getSignature())
        statement.setString(8, disguise.realSkin.getData())
        statement.setString(9, disguise.realSkin.getSignature())
        statement.setString(10, disguise.rank.name)
        statement.executeUpdate()
    }

    fun setActivityState(disguise: Disguise, active: Boolean) {
        val statement = connection.prepareStatement(
                "update $TABLE_NAME set active=? where realid=?"
        )
        statement.setInt(1, if(active) 1 else 0)
        statement.setString(2, disguise.realId.toString())
        statement.executeUpdate()
    }
}
