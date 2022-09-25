package org.legion.steel.steelnet.config

import com.github.benmanes.caffeine.cache.Caffeine
import com.google.api.client.util.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.Duration


@Configuration
@ConfigurationProperties(prefix = "service.caching")
class CacheConfiguration {

    private var initialCapacity: Int = 0
    private var maximumSize: Long = 0
    private var expireAfterWrite: Long = 0

    companion object {
        public const val RESPONSE_CACHE: String = "ResponseCache"
    }

    @Bean
    @Primary
    public fun cacheManager(): CacheManager {
        val caffeineCacheManager = CaffeineCacheManager()
        caffeineCacheManager.setCacheNames(
            listOf(
                RESPONSE_CACHE
            )
        )
        caffeineCacheManager.isAllowNullValues = true
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofHours(expireAfterWrite))
            .initialCapacity(this.initialCapacity.toInt())
            .maximumSize(this.maximumSize)
        )
        return caffeineCacheManager
    }

    fun getInitialCapacity(): Int {
        return this.initialCapacity
    }

    fun getMaximumSize(): Long {
        return this.maximumSize
    }

    fun getExpireAfterWrite(): Long {
        return this.expireAfterWrite
    }

    fun setInitialCapacity(initialCapacity: Int) {
        this.initialCapacity = initialCapacity
    }

    fun setMaximumSize(maximumSize: Long) {
        this.maximumSize = maximumSize
    }

    fun setExpireAfterWrite(expireAfterWrite: Long) {
        this.expireAfterWrite = expireAfterWrite
    }

}