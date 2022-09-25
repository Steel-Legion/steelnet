package org.legion.steel.steelnet.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.mongodb.BasicDBObject
import org.legion.steel.steelnet.config.InfoConfiguration
import org.springframework.data.mongodb.core.MongoTemplate
import java.text.SimpleDateFormat
import java.util.*

@JsonRootName("stats")
class AliveStats(
    private var mongoTemplate: MongoTemplate,
    private var infoConfiguration: InfoConfiguration
) {

    @JsonProperty("alive_for")
    private var aliveFor: String

    @JsonProperty("database_reachable")
    private var databaseReachable: Boolean

    @JsonProperty("listening_to_apis")
    private var listeningToApis: List<String>

    companion object {
        var startupTime: Long = 0
    }

    init {

        aliveFor = this.calculateUpTime()

        listeningToApis = this.infoConfiguration.getApiList()

        databaseReachable = this.getDatabaseReachable()

    }

    fun getAliveFor(): String {
        return this.aliveFor
    }

    private fun getDatabaseReachable(): Boolean {
        if(!this.databaseReachable) {
            this.databaseReachable = this.pingMongoDB()
        }
        return this.databaseReachable
    }

    fun getListeningToApis(): List<String> {
        return this.listeningToApis
    }

    private fun pingMongoDB(): Boolean {


        try {

            val ping = BasicDBObject("ping", "1")

            val answer: org.bson.Document = this.mongoTemplate.db.runCommand(ping)

            if(answer.containsKey("ok") && answer["ok"] == 1) {
                return true
            }

        } catch (_: Exception) {
            return false
        }

        return false
    }

    private fun calculateUpTime(): String {

        if(startupTime > 0L) {
            val upTimeMillis = System.currentTimeMillis() - startupTime
            val simpleDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSSS Z")
            val resultingDate = Date(upTimeMillis)
            return simpleDateFormat.format(resultingDate)
        }

        return "Unknown"
    }

}