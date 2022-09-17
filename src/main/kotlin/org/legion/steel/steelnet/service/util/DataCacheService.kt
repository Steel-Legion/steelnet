package org.legion.steel.steelnet.service.util

import org.legion.steel.steelnet.config.GoogleConfiguration
import org.legion.steel.steelnet.dto.ItemDTOInterface
import org.legion.steel.steelnet.service.google.sheets.GoogleSheetsResolver
import org.legion.steel.steelnet.service.google.sheets.GoogleTokenSheetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DataCacheService(
    @Autowired private var googleSheetsResolver: GoogleSheetsResolver,
    @Autowired private var googleTokenSheetService: GoogleTokenSheetService,
    @Autowired private var googleConfiguration: GoogleConfiguration
) : DataCacheServiceInterface {

    init {
        this.googleTokenSheetService.checkForUpdatesEveryXHours(this.googleConfiguration.getCycle())
        this.googleSheetsResolver.checkForUpdatesEveryXHours(this.googleConfiguration.getCycle())
    }

    override fun getTokens(): List<String> {
        return this.googleTokenSheetService.getTokenList()
    }

    override fun getSheetsData(): HashMap<String?, ItemDTOInterface> {
        return this.googleSheetsResolver.getStoredSheetsData()
    }

    override fun getPossibleKeys(): List<String> {
        return this.googleSheetsResolver.getPossibleKeys()
    }
}