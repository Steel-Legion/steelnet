package org.legion.steel.steelnet.service.item

import org.legion.steel.steelnet.dto.ItemDTOInterface
import org.legion.steel.steelnet.repository.ItemRepository
import org.legion.steel.steelnet.service.util.TokenValidator
import org.legion.steel.steelnet.service.util.DataCacheService
import org.legion.steel.steelnet.service.util.ItemMapperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemService(
    @Autowired private var dataCacheService: DataCacheService,
    @Autowired private var itemRepository: ItemRepository,
    @Autowired private var itemMapperService: ItemMapperService,
    @Autowired private var tokenValidator: TokenValidator
) : AbstractItemService() {
    override fun buildResponse(name: String, token: String): ResponseEntity<ItemDTOInterface> {
        if(this.tokenValidator.validateToken(token)) {
            val foundItem = this.dataCacheService.getSheetsData()[name]
            if (foundItem != null) {
                return ResponseEntity.ok(foundItem)
            }
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

    override fun buildListResponse(
        vararg itemType: String,
        token: String,
        searchForTypes: Boolean
    ): ResponseEntity<List<ItemDTOInterface>> {
        if(this.tokenValidator.validateToken(token)) {
            val foundItems = emptyList<ItemDTOInterface>().toMutableList()

            when {
                searchForTypes && itemType.size == 1 -> {
                    // ask the cache first.
                    this.getItemListByTypeFromCache(itemType[0], foundItems)
                    if(foundItems.isEmpty()) {
                        this.getItemListByTypeFromDB(itemType[0], foundItems)
                    }
                }
                itemType.size == 2 -> {
                    // ask the cache first
                    this.getItemListByXFromCache(itemType[0], itemType[1], foundItems = foundItems)
                    if(foundItems.isEmpty()) {
                        this.getItemListByXFromDB(itemType[0], itemType[1], foundItems = foundItems)
                    }
                }
            }

            if (foundItems.isEmpty()) {
                return ResponseEntity.notFound().build()
            }

            return ResponseEntity.ok(foundItems)
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

    override fun buildResponseForAll(token: String): ResponseEntity<List<ItemDTOInterface>> {
        if(this.tokenValidator.validateToken(token)) {
            var responseList = this.dataCacheService.getSheetsData().values.toList()
            if(responseList.isEmpty()) {
                responseList = this.itemMapperService.mapItemDTOInBulk(this.itemRepository.findAll())
            }
            return ResponseEntity.ok(responseList)
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

    override fun getItemListByTypeFromDB(itemType: String, foundItems: MutableList<ItemDTOInterface>) {
        val databaseEntries = this.itemRepository.getItemModelsByItemType(itemType)
        foundItems.addAll(this.itemMapperService.mapItemDTOInBulk(databaseEntries))
    }

    override fun getItemListByXFromDB(key: String, value: String, foundItems: MutableList<ItemDTOInterface>) {
        val databaseEntries = this.itemRepository.getItemModelByFieldAndValue(key, value)
        foundItems.addAll(this.itemMapperService.mapItemDTOInBulk(databaseEntries))
    }

    private fun getItemListByTypeFromCache(itemType: String, foundItems: MutableList<ItemDTOInterface>) {

        this.dataCacheService.getSheetsData().forEach {
            if (it.value.getItemType() != null && it.value.getItemType() == itemType) {
                foundItems.add(it.value)
            }
        }
    }

    private fun getItemListByXFromCache(vararg itemType: String, foundItems: MutableList<ItemDTOInterface>) {

        this.dataCacheService.getSheetsData().forEach { it ->
            val key = itemType[1].lowercase(Locale.getDefault())
            val value = itemType[0].lowercase(Locale.getDefault())

            when {
                key == "name" && value == it.value.getName() -> {
                    foundItems.add(it.value)
                }
                key == "nummats" && value == it.value.getNumMats() -> {
                    foundItems.add(it.value)
                }
                key == "matstype" && value == it.value.getMatsType() -> {
                    foundItems.add(it.value)
                }
                key == "stackingvalues" && it.value.getStackingValues() != null && it.value.getStackingValues()!!.contains(value) -> {
                    foundItems.add(it.value)
                }
                key == "numpercrate" && value == it.value.getNumPerCrate() -> {
                    foundItems.add(it.value)
                }
                key == "itemtype" && value == it.value.getItemType() -> {
                    foundItems.add(it.value)
                }
                key == "equipweight" && value == it.value.getEquipWeight() -> {
                    foundItems.add(it.value)
                }
                key == "equipslot" && value == it.value.getEquipSlot() -> {
                    foundItems.add(it.value)
                }
                key == "weaponclass" && value == it.value.getWeaponClass() -> {
                    foundItems.add(it.value)
                }
                key == "ammotype" && value == it.value.getAmmoType() -> {
                    foundItems.add(it.value)
                }
                key == "vehicleclass" && value == it.value.getVehicleClass() -> {
                    foundItems.add(it.value)
                }
                key == "numcrew" && value == it.value.getNumCrew() -> {
                    foundItems.add(it.value)
                }
                key == "primaryarmament" && value == it.value.getPrimaryArmament() -> {
                    foundItems.add(it.value)
                }
                key == "secondaryarmament" && it.value.getSecondaryArmament() != null && it.value.getSecondaryArmament()!!.contains(value) -> {
                    foundItems.add(it.value)
                }
                key == "description" && it.value.getDescription()!!.startsWith(value) -> {
                    foundItems.add(it.value)
                }

            }

        }
    }
}