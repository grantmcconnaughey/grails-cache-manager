package grails.plugin.cachemanager

class CacheManagerController {

    def grailsCacheManager

    static defaultAction = "list"

    def list() {
        def caches = []
        grailsCacheManager.cacheNames.each { cacheName ->
            if (!cacheName.contains('grails')) {
                caches << grailsCacheManager.getCache(cacheName)
            }
        }

        [caches: caches.sort { a, b -> a.name <=> b.name }]
    }

    def clear(String cacheName) {
        if (!cacheName) {
            flash.error = "Please submit the name of a cache to clear."
            redirect action: 'list'
            return
        }

        def cache = grailsCacheManager.getCache(cacheName)
        cache?.clear()

        flash.message = "Cache $cacheName cleared."

        redirect(action: 'list')
    }

    def changeTimeToLive(String cacheName, Integer newTimeToLiveSeconds) {
        if (!cacheName) {
            flash.error = "Please submit the name of a cache to clear."
            redirect action: 'list'
            return
        }

        def cache = grailsCacheManager.getCache(cacheName)
        cache?.clear()

        def nativeCache = cache.nativeCache

        if ( newTimeToLiveSeconds == 0 ) {
            // Disable the cache
            nativeCache?.setDisabled(true)

            flash.message = "Cache $cacheName disabled."
        } else {
            // Make sure the cache is enabled
            if ( nativeCache.isDisabled() ) {
                nativeCache.setDisabled(false)
            }

            def config = nativeCache.cacheConfiguration
            config?.setTimeToLiveSeconds(newTimeToLiveSeconds)

            flash.message = "Cache $cacheName cleared and Time To Live set to $newTimeToLiveSeconds seconds."
        }

        redirect(action: 'list')
    }
}
