grails {
    plugin {
        cachemanager {
            excludedCacheNames = []
            clearOnNewTTL = true
            layout = 'main'
            newTTLValues = [0: 'None (Disable Cache)', (60 * 60): 'One Hour', (60 * 60 * 24): 'One Day', (60 * 60 * 24 * 7): 'One Week']
        }
    }
}