package grails.plugin.cachemanager

class CacheManagerController {

    def grailsCacheManager
    def grailsApplication

    static defaultAction = "list"

    def list() {
        def caches = []
        for (cacheName in grailsCacheManager.cacheNames) {
            if ( !(cacheName in grailsApplication.config.grails.plugin.cachemanager.excludedCacheNames) ) {
                caches << grailsCacheManager.getCache(cacheName)
            }
        }

        def sortedCaches = caches.sort { a, b ->
            a.name <=> b.name
        }

        [caches: sortedCaches]
    }

    def clear(String cacheName) {
        if (!cacheName) {
            flash.error = message(code: 'cacheManager.noCache', default: 'Please submit the name of a cache to clear.')
            redirect action: 'list'
            return
        }

        def cache = grailsCacheManager.getCache(cacheName)
        cache.clear()

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

        if (grailsApplication.config.grails.plugin.cacheManager.clearOnNewTTL) {
            cache.clear()
        }

        def nativeCache = cache.nativeCache

        if ( newTimeToLiveSeconds == 0 ) {
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

            if (grailsApplication.config.grails.plugin.cacheManager.clearOnNewTTL) {
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
}
