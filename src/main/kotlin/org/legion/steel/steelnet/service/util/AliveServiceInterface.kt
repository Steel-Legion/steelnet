package org.legion.steel.steelnet.service.util

import org.legion.steel.steelnet.dto.AliveStats
import org.springframework.http.ResponseEntity

interface AliveServiceInterface {
    fun createAliveStats(): ResponseEntity<AliveStats>
}