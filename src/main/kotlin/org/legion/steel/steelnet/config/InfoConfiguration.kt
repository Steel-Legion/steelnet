package org.legion.steel.steelnet.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "service.information")
class InfoConfiguration(
    private var apiList: List<String>
) {
    fun getApiList(): List<String> {
        return this.apiList
    }

    fun setApiList(apiList: List<String>) {
        this.apiList = apiList
    }
}