package grails.plugin.cachemanager

import org.springframework.cache.Cache

class CacheManagerController {

    def grailsCacheAdminService
    def grailsCacheManager

    static defaultAction = "list"

    def list() {
        List<Cache> caches = grailsCacheManager.cacheNames
            .findAll { !(it in pluginConfig.excludedCacheNames) }
            .collect { grailsCacheManager.getCache(it) }
            .sort { it.name }
        [caches: caches]
    }

    def clear(String cacheName) {
        if (!cacheName) {
            flash.error = message(code: 'cacheManager.noCache', default: 'Please submit the name of a cache to clear.')
            redirect action: 'list'
            return
        }

        grailsCacheAdminService.clearCache(cacheName)

        flash.message = message(code: 'cacheManager.cache.cleared', args: [cacheName], default: 'Cache {0} cleared.')

        redirect action: 'list'
    }

    def changeTimeToLive(String cacheName, Integer newTimeToLiveSeconds) {
        if (!cacheName) {
            flash.error = message(code: 'cacheManager.noCache', default: 'Please submit the name of a cache to clear.')
            redirect action: 'list'
            return
        }

        def cache = grailsCacheManager.getCache(cacheName)

        if (pluginConfig.clearOnNewTTL) {
            cache.clear()
        }

        def nativeCache = cache.nativeCache

        if (newTimeToLiveSeconds == 0) {
            // Disable the cache
            nativeCache.setDisabled(true)

            flash.message = message(code: 'cacheManager.cache.disabled', args: [cacheName], default: 'Cache {0} disabled.')
        } else {
            // Make sure the cache is enabled
            if ( nativeCache.isDisabled() ) {
                nativeCache.setDisabled(false)
            }

            def config = nativeCache.cacheConfiguration
            config?.setTimeToLiveSeconds(newTimeToLiveSeconds)

            if (pluginConfig.clearOnNewTTL) {
                flash.message = message(code: 'cacheManager.cache.clearedAndNewTTL',
                                        args: [cacheName, newTimeToLiveSeconds],
                                        default: 'Cache {0} cleared and Time To Live set to {1} seconds.')
            } else {
                flash.message = message(code: 'cacheManager.cache.newTTL',
                                        args: [cacheName, newTimeToLiveSeconds],
                                        default: 'Cache {0} Time to Live set to {1} seconds.')
            }
        }

        redirect action: 'list'
    }

    def clearAllCaches() {
        grailsCacheAdminService.clearAllCaches()
        flash.message = message(code: 'cacheManager.cache.allCaches.cleared', default: 'All caches cleared.')
        redirect action: 'list'
    }

    def clearBlocksCache() {
        grailsCacheAdminService.clearBlocksCache()
        flash.message = message(code: 'cacheManager.cache.blocksCache.cleared', default: 'Blocks cache cleared.')
        redirect action: 'list'
    }

    def clearTemplatesCache() {
        grailsCacheAdminService.clearTemplatesCache()
        flash.message = message(code: 'cacheManager.cache.templatesCache.cleared', default: 'Templates cache cleared.')
        redirect action: 'list'
    }

    protected getPluginConfig() {
        grailsApplication.mergedConfig.grails.plugin.cachemanager
    }
}
