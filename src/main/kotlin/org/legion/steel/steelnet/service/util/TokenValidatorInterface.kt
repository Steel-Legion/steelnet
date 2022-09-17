package org.legion.steel.steelnet.service.util

interface TokenValidatorInterface {
    fun validateToken(unformattedToken: String): Boolean
    fun checkFormatCorrect(unformattedToken: String): Boolean
    fun formatToken(unformattedToken: String): String
    fun tokenInSheet(formattedToken: String): Boolean
}