package org.legion.steel.steelnet.service.google.sheets

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import org.apache.commons.io.IOUtils
import org.legion.steel.steelnet.config.GoogleConfiguration
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.*


abstract class AbstractGoogleSheetsService(
    override var googleConfiguration: GoogleConfiguration
) : GoogleSheetsServiceInterface {

    override val httpTransport: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    override val jsonFactory: GsonFactory = GsonFactory.getDefaultInstance()
    override val scopes: MutableList<String> = Collections.singletonList(SheetsScopes.SPREADSHEETS)

    override fun getCredentials(): Credential {
        val credentialsString = this.googleConfiguration.getCredentialsString()
        val readCredentials = IOUtils.toInputStream(credentialsString, StandardCharsets.UTF_8)

        return GoogleCredential.fromStream(readCredentials).createScoped(scopes)
    }

    override fun fetchGoogleSheetsData(spreadsheetId: String, tablename: String): List<List<Any>>? {
        val service = Sheets.Builder(this.httpTransport, this.jsonFactory, this.getCredentials())
            .setApplicationName(this.googleConfiguration.getApplicationName())
            .build()
        val response = service.spreadsheets().values().get(spreadsheetId, tablename)
            .execute()
        return response.getValues()
    }
}