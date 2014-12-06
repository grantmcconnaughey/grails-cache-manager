package grails.plugin.cachemanager

class CacheManagerTagLib {

    static namespace = "cacheManager"

    def pluginManager

    static final String EHCACHE_PLUGIN_NAME = 'cacheEhcache'

    def ehcacheInstalled = { attrs, body ->
        boolean ttlCachePluginInstalled = pluginManager.allPlugins.any { it.name == EHCACHE_PLUGIN_NAME }

        if (ttlCachePluginInstalled) {
            out << body()
        }
    }
}
