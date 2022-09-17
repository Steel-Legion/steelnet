package org.legion.steel.steelnet.service.item

import org.legion.steel.steelnet.dto.ItemDTOInterface
import org.springframework.http.ResponseEntity

interface ItemServiceInterface {
    fun buildResponse(name: String, token: String): ResponseEntity<ItemDTOInterface>
    fun buildListResponse(
        vararg itemType: String,
        token: String,
        searchForTypes: Boolean
    ): ResponseEntity<List<ItemDTOInterface>>

    fun buildResponseForAll(token: String): ResponseEntity<List<ItemDTOInterface>>
    fun getItemListByTypeFromDB(itemType: String, foundItems: MutableList<ItemDTOInterface>)
    fun getItemListByXFromDB(key: String, value: String, foundItems: MutableList<ItemDTOInterface>)
}