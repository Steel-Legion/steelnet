package org.legion.steel.steelnet.service.util

import org.legion.steel.steelnet.dto.ItemDTO
import org.legion.steel.steelnet.dto.ItemDTOInterface
import org.legion.steel.steelnet.model.ItemModel
import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class ItemMapperService : ItemMapperServiceInterface {

    override fun mapItemModelInBulk(dtoList: MutableList<ItemDTOInterface>): MutableList<ItemModel> {
        val returnList = emptyList<ItemModel>().toMutableList()

        var idCounter = BigInteger.ZERO

        dtoList.forEach {
            returnList.add(this.mapItemModel(it, idCounter))
            idCounter++
        }

        return returnList
    }

    override fun mapItemModel(input: ItemDTOInterface, id: BigInteger): ItemModel {
        val itemModel = ItemModel(id.toString())

        input.getName()?.let { itemModel.setName(it.trim()) }
        input.getNumMats()?.let { itemModel.setNumMats(it.trim()) }
        input.getMatsType()?.let { itemModel.setMatsType(it.trim()) }
        input.getStackingValues()?.let { itemModel.setStackingValues(*it) }
        input.getNumPerCrate()?.let { itemModel.setNumPerCrate(it.trim()) }
        input.getItemType()?.let { itemModel.setItemType(it.trim()) }
        input.getEquipSlot()?.let { itemModel.setEquipSlot(it.trim()) }
        input.getWeaponClass()?.let { itemModel.setWeaponClass(it.trim()) }
        input.getAmmoType()?.let { itemModel.setAmmoType(it.trim()) }
        input.getVehicleClass()?.let { itemModel.setVehicleClass(it.trim()) }
        input.getNumCrew()?.let { itemModel.setNumCrew(it.trim()) }
        input.getPrimaryArmament()?.let { itemModel.setPrimaryArmament(it.trim()) }
        input.getSecondaryArmament()?.let { itemModel.setSecondaryArmament(*it) }
        input.getDescription()?.let { itemModel.setDescription(it.trim()) }

        return itemModel
    }

    override fun mapItemDTOInBulk(databaseEntries: MutableList<ItemModel>): MutableList<ItemDTOInterface> {
        val returnList = emptyList<ItemDTOInterface>().toMutableList()

        databaseEntries.forEach {
            returnList.add(this.mapItem(it))
        }


        return returnList
    }

    override fun mapItem(itemModel: ItemModel): ItemDTOInterface {
        val returnModel = ItemDTO()

        when (itemModel.getName() != null) {
            true -> {
                itemModel.getName()?.let { returnModel.setName(it.trim()) }
            }

            false -> {
                returnModel.setName("UNKNOWN NAME")
            }
        }

        when (itemModel.getNumMats() != null) {
            true -> {
                itemModel.getNumMats()?.let { returnModel.setNumMats(it.trim()) }
            }

            false -> {
                returnModel.setNumMats("")
            }
        }

        when (itemModel.getStackingValues() != null) {
            true -> {
                itemModel.getStackingValues()?.let { returnModel.setStackingValues(*it) }
            }

            false -> {
                returnModel.setStackingValues(*emptyArray<String>())
            }
        }

        when (itemModel.getNumPerCrate() != null) {
            true -> {
                itemModel.getNumPerCrate()?.let { returnModel.setNumPerCrate(it.trim()) }
            }

            false -> {
                returnModel.setNumPerCrate("")
            }
        }

        when (itemModel.getItemType() != null) {
            true -> {
                itemModel.getItemType()?.let { returnModel.setItemType(it.trim()) }
            }

            false -> {
                returnModel.setItemType("")
            }
        }

        when (itemModel.getEquipWeight() != null) {
            true -> {
                itemModel.getEquipWeight()?.let { returnModel.setEquipWeight(it.trim()) }
            }

            false -> {
                returnModel.setEquipWeight("")
            }
        }

        when (itemModel.getEquipSlot() != null) {
            true -> {
                itemModel.getEquipSlot()?.let { returnModel.setEquipSlot(it.trim()) }
            }

            false -> {
                returnModel.setEquipSlot("")
            }
        }

        when (itemModel.getWeaponClass() != null) {
            true -> {
                itemModel.getWeaponClass()?.let { returnModel.setWeaponClass(it.trim()) }
            }

            false -> {
                returnModel.setWeaponClass("")
            }
        }

        when (itemModel.getAmmoType() != null) {
            true -> {
                itemModel.getAmmoType()?.let { returnModel.setAmmoType(it.trim()) }
            }

            false -> {
                returnModel.setAmmoType("")
            }
        }

        when (itemModel.getPrimaryArmament() != null) {
            true -> {
                itemModel.getPrimaryArmament()?.let { returnModel.setPrimaryArmament(it.trim()) }
            }

            false -> {
                returnModel.setPrimaryArmament("")
            }
        }

        when (itemModel.getSecondaryArmament() != null) {
            true -> {
                itemModel.getSecondaryArmament()?.let { returnModel.setSecondaryArmament(*it) }
            }

            false -> {
                returnModel.setSecondaryArmament("")
            }
        }

        return returnModel
    }
}