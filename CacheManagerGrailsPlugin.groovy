class CacheManagerGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Grails Cache Manager Plugin" // Headline display name of the plugin
    def author = "Grant McConnaughey"
    def authorEmail = "grantmcconnaughey@gmail.com"
    def description = '''\
A simple admin tool to manage the various caches in a Grails app.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/cache-manager"

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "Github", url: "https://github.com/grantmcconnaughey/Grails-Cache-Manager/issues" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/grantmcconnaughey/Grails-Cache-Manager" ]
}
