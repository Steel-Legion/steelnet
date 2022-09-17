package org.legion.steel.steelnet.service.google.sheets

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import org.legion.steel.steelnet.config.GoogleConfiguration

interface GoogleSheetsServiceInterface {
    var googleConfiguration: GoogleConfiguration
    val httpTransport: NetHttpTransport
    val jsonFactory: GsonFactory
    val scopes: MutableList<String>
    fun getCredentials(): Credential
    fun fetchGoogleSheetsData(spreadsheetId: String, tablename: String): List<List<Any>>?
}