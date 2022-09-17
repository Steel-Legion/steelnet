package org.legion.steel.steelnet.service.util

import org.legion.steel.steelnet.dto.ItemDTOInterface
import org.legion.steel.steelnet.model.ItemModel
import java.math.BigInteger

interface ItemMapperServiceInterface {
    fun mapItemModelInBulk(dtoList: MutableList<ItemDTOInterface>): MutableList<ItemModel>
    fun mapItemModel(input: ItemDTOInterface, id: BigInteger): ItemModel
    fun mapItemDTOInBulk(databaseEntries: MutableList<ItemModel>): MutableList<ItemDTOInterface>
    fun mapItem(itemModel: ItemModel): ItemDTOInterface
}