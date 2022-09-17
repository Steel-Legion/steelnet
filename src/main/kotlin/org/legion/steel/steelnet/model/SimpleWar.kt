package org.legion.steel.steelnet.model

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId
import java.util.Date

@Document(collection = "simple_war_data")
class SimpleWar(
    @MongoId
    private var warId: String?,

    @Field("war_number")
    private var warNumber: String?,

    @Field("winner")
    private var winner: String?,

    @Field("conquest_start_time")
    private var conquestStartTime: Date?,

    @Field("conquest_end_time")
    private var conquestEndTime: Date?,

    @Field("resistance_start_time")
    private var resistanceStartTime: Date?,

    @Field("required_victory_towns")
    private var requiredVictoryTowns: Int?,

    @Field("hex_grids")
    private var hexGrids: List<HexGridModel>?
) {
}