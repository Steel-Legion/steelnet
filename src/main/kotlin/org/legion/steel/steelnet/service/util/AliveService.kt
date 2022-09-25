package org.legion.steel.steelnet.service.util

import org.legion.steel.steelnet.config.InfoConfiguration
import org.legion.steel.steelnet.dto.AliveStats
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class AliveService(
    @Autowired private var mongoTemplate: MongoTemplate,
    @Autowired private var infoConfiguration: InfoConfiguration
) : AliveServiceInterface {

    override fun createAliveStats(): ResponseEntity<AliveStats> {
        val aliveStats = AliveStats(this.mongoTemplate, this.infoConfiguration)
        return ResponseEntity.ok(aliveStats)
    }
}