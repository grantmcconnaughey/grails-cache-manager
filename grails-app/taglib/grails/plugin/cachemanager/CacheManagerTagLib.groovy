package grails.plugin.cachemanager

class CacheManagerTagLib {

    static defaultEncodeAs = [taglib: 'html']
    static namespace = "cacheManager"

    def pluginManager

    static final TTL_CACHE_PLUGINS = ['cache-ehcache']

    def appSupportsTTL = { attrs, body ->
        def installedPluginNames = pluginManager.allPlugins.collect { it.name }
        def ttlCachePluginInstalled = false

        for (cachePlugin in TTL_CACHE_PLUGINS) {
            if (installedPluginNames.contains(cachePlugin)) {
                ttlCachePluginInstalled = true
            }
        }
        
        if (ttlCachePluginInstalled) {
            out << body()
        }
    }
}
