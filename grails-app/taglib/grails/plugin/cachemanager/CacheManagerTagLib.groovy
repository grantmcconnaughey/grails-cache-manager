package grails.plugin.cachemanager

class CacheManagerTagLib {

    static namespace = "cacheManager"

    def pluginManager

    static final EHCACHE_PLUGIN_NAME = 'cacheEhcache'

    def ehcacheInstalled = { attrs, body ->
        def installedPluginNames = pluginManager.allPlugins.collect { it.name }
        def ttlCachePluginInstalled = false

        if (installedPluginNames.contains(EHCACHE_PLUGIN_NAME)) {
            ttlCachePluginInstalled = true
        }
        
        if (ttlCachePluginInstalled) {
            out << body()
        }
    }
}
