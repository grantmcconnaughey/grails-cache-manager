# Grails Cache Manager Plugin

**A simple admin interface to manage the various caches in a Grails app.**

## Installation

To install the latest version of the plugin add this to your `BuildConfig.groovy` in the plugins section (be sure to use the latest version):
```groovy
compile ':cache-manager:0.9.0'
```

## Usage

This plugin contains a single admin view for controller the caches in a Grails app. Navigate to `/cacheManager/list` to view the caches. This interface allows you to do the following:

### Bulk Manage Caches
* **Clear All Caches**
* **Clear Blocks Cache**
* **Clear Templates Cache**

### Manage Individual Caches
* **View the caches in your app**
* **Clear an individual cache**

### Manage Individual Caches (with the Ehcache Plugin)
* **View each cache's current Time To Live**
* **Set a new Time To Live**
* **Disable a cache**

## Configuration

The Grails Cache Manager Plugin allows configuration to change the look and functionality of the interface. All config values start with `grails.plugin.cachemanager`.

| Name               | Default      | Description                                                                              |
| ------------------ | ------------ | ---------------------------------------------------------------------------------------- |
| excludedCacheNames | `[]`         | A list of cache names that should not be included in the cache manager page.             |
| clearOnNewTTL      | `true`       | True if the cache manager should clear a cache when its Time To Live setting is changed. |
| layout             | `"main"`     | The layout to use for the cache manager.                                                 |
| newTTLValues       | `[0: 'None (Disable Cache)', (60 * 60): 'One Hour', (60 * 60 * 24): 'One Day', (60 * 60 * 24 * 7): 'One Week']` | A Map for the New Time To Live select box, where the key is the new TTL and the value is a String containing the value's description. |

## Compatibility

This plugin is currently only available with the [Grails Cache Plugin](http://grails.org/plugin/cache) or with the [Grails Cache Plugin](http://grails.org/plugin/cache) **and** [Ehcache Cache Plugin](http://grails.org/plugin/cache-ehcache). If the Ehcache Cache Plugin is also installed then the cache manager interface will including settings for Time to Live and disabling/enabling a cache.

## Security

You will likely want to restrict access to the cache manager when you deploy a live app using this plugin. The URL path `/cacheManager/list` should be secured.

### Spring Security example
```groovy
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
    '/cacheManager/**': ['ROLE_ADMIN']
]
```