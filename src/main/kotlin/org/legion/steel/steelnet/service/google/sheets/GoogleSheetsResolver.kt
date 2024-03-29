package org.legion.steel.steelnet.service.google.sheets




import org.legion.steel.steelnet.config.GoogleConfiguration
import org.legion.steel.steelnet.dto.ItemDTO
import org.legion.steel.steelnet.dto.ItemDTOInterface
import org.legion.steel.steelnet.model.ItemModel
import org.legion.steel.steelnet.repository.ItemRepository
import org.legion.steel.steelnet.service.util.ItemMapperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.time.Duration
import java.util.*
import kotlin.concurrent.thread


@Service
class GoogleSheetsResolver(
    @Autowired googleConfiguration: GoogleConfiguration,
    @Autowired private var itemRepository: ItemRepository,
    @Autowired private var mappingService: ItemMapperService
) : AbstractGoogleSheetsService(
    googleConfiguration
) {

    private lateinit var storedSheetsData: HashMap<String?, ItemDTOInterface>
    private lateinit var possibleKeys: List<String>


    private fun sortGoogleSheetsData(): MutableMap<String?, ItemDTOInterface> {

        val sortedData = HashMap<String?, ItemDTOInterface>().toMutableMap()

        val fetchedData = this.fetchGoogleSheetsData(this.googleConfiguration.getSpreadSheetId(), "items")

        if (!fetchedData.isNullOrEmpty()) {
            val titleRow = emptyList<String>().toMutableList()

            for (cell in fetchedData[0]) {
                titleRow.add(cell.toString())
            }

            this.possibleKeys = titleRow.toList()

            for (row in fetchedData) {
                if (row[0].toString() == "Name") {
                    continue
                }

                val itemDTO = ItemDTO()

                val mpfVals = emptyList<String>().toMutableList()
                for (index in row.indices) {
                    when {
                        titleRow[index] == "Name" -> {
                            itemDTO.setName(row[index].toString().lowercase(Locale.getDefault()).trim())
                        }

                        titleRow[index] == "Type" -> {
                            itemDTO.setItemType(row[index].toString().lowercase(Locale.getDefault()).trim())
                        }

                        titleRow[index] == "Base Required Materials" -> {
                            itemDTO.setNumMats(row[index].toString().lowercase(Locale.getDefault()).trim())
                        }

                        titleRow[index] == "Mat Type" -> {
                            itemDTO.setMatsType(row[index].toString().lowercase(Locale.getDefault()).trim())
                        }

                        titleRow[index] == "Num Per Crate" -> {
                            itemDTO.setNumPerCrate(row[index].toString().lowercase(Locale.getDefault()).trim())
                        }

                        titleRow[index] == "Encumbrance" -> {
                            itemDTO.setEquipWeight(row[index].toString().lowercase(Locale.getDefault()).trim())
                        }

                        titleRow[index] == "Equip Slot" -> {
                            itemDTO.setEquipSlot(row[index].toString().lowercase(Locale.getDefault()).trim())
                        }

                        titleRow[index] == "Weapon Class" -> {
                            itemDTO.setWeaponClass(row[index].toString().lowercase(Locale.getDefault()).trim())
                        }

                        titleRow[index] == "Ammo Type" -> {
                            itemDTO.setAmmoType(row[index].toString().lowercase(Locale.getDefault()).trim())
                        }

                        titleRow[index] == "Vehicle Class" -> {
                            itemDTO.setVehicleClass(row[index].toString().lowercase(Locale.getDefault()).trim())
                        }

                        titleRow[index] == "Num Crew" -> {
                            itemDTO.setNumCrew(row[index].toString().lowercase(Locale.getDefault()).trim())
                        }

                        titleRow[index] == "Primary Armament" -> {
                            itemDTO.setPrimaryArmament(row[index].toString().lowercase(Locale.getDefault()).trim())
                        }

                        titleRow[index] == "Secondary Armament" -> {
                            itemDTO.setSecondaryArmament(row[index].toString().lowercase(Locale.getDefault()).trim())
                        }

                        titleRow[index].startsWith("MPF ") -> {
                            mpfVals.add(row[index].toString().lowercase(Locale.getDefault()).trim())
                        }
                    }
                    itemDTO.setStackingValues(*mpfVals.toTypedArray())
                }

                sortedData[itemDTO.getName()] = itemDTO

            }
        }

        val receivedModels = emptyList<ItemModel>().toMutableList()

        var idCounter = BigInteger.ZERO

        sortedData.forEach{
            receivedModels.add(
                this.mappingService.mapItemModel(it.value, idCounter)
            )
                idCounter++
        }

        this.itemRepository.saveAll(receivedModels)

        return sortedData
    }

    fun checkForUpdatesEveryXHours(hours: Long) {
        thread(
            start = true
        ) {
            while (!Thread.currentThread().isInterrupted) {
                Thread.sleep(Duration.ofHours(hours).toMillis())
                this.storedSheetsData = this.sortGoogleSheetsData() as HashMap<String?, ItemDTOInterface>
            }
        }
    }

    init {
        this.storedSheetsData = this.sortGoogleSheetsData() as HashMap<String?, ItemDTOInterface>
    }

    fun getStoredSheetsData(): HashMap<String?, ItemDTOInterface> {
        return this.storedSheetsData
    }

    fun getPossibleKeys(): List<String> {
        return this.possibleKeys
    }
}
