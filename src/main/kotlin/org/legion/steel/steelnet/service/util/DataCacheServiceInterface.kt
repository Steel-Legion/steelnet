package org.legion.steel.steelnet.service.util

import org.legion.steel.steelnet.dto.ItemDTOInterface

interface DataCacheServiceInterface {
    fun getTokens(): List<String>
    fun getSheetsData(): HashMap<String?, ItemDTOInterface>
    fun getPossibleKeys(): List<String>
}