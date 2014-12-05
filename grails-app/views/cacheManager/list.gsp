<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="${grailsApplication.mergedConfig.grails.plugin.cachemanager.layout}" />
    <title>App Caches</title>
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/pure/0.5.0/buttons-min.css" />
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/pure/0.5.0/tables-min.css" />
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'cache-manager.css', plugin: 'cache-manager')}" />
</head>
<body>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" default="Home" /></a></li>
        </ul>
    </div>
    <div id="content">
        <h1>Cache Manager</h1>

        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <g:if test="${flash.error}">
            <ul class="errors" role="alert">
                <li>${flash.error}</li>
            </ul>
        </g:if>

        <h2>Manage Bulk Caches</h2>
        <div>
            <g:link controller="cacheManager" action="clearAllCaches" class="pure-button">Clear All Caches</g:link>
            <g:link controller="cacheManager" action="clearBlocksCache" class="pure-button">Clear Blocks Cache</g:link>
            <g:link controller="cacheManager" action="clearTemplatesCache" class="pure-button">Clear Templates Cache</g:link>
        </div>


        <h2>Manage Individual Caches</h2>
        <table id="cache-manager-table" class="pure-table pure-table-striped">
            <thead>
                <tr>
                    <th>Cache Name</th>
                    <cacheManager:ehcacheInstalled>
                        <th>Enabled?</th>
                        <th>Time To Live (seconds)</th>
                        <th>Set new Time To Live</th>
                    </cacheManager:ehcacheInstalled>
                    <th>Clear Cache</th>
                </tr>
            </thead>
            <tbody>
            <g:each in="${caches}" var="cache">
                <tr>
                    <td>${cache.name}</td>
                    <cacheManager:ehcacheInstalled>
                    <td style="text-align: center;">
                        <span class="${cache.nativeCache.isDisabled() ? 'disabled' : 'enabled'}"></span>
                    </td>
                        <td class="ttl-seconds">${cache.nativeCache.cacheConfiguration.timeToLiveSeconds}</td>
                        <td>
                            <g:form action="changeTimeToLive" method="POST">
                                <g:select name="newTimeToLiveSeconds" optionKey="key" optionValue="value"
                                          from="${grailsApplication.mergedConfig.grails.plugin.cachemanager.newTTLValues}" />
                                <g:hiddenField name="cacheName" value="${cache.name}" />
                                <g:submitButton name="submit" value="Go" class="pure-button pure-button-primary go-button" />
                            </g:form>
                        </td>
                    </cacheManager:ehcacheInstalled>
                    <td>
                        <g:link action="clear" params="[cacheName: cache.name]" class="pure-button">Clear</g:link>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</body>
</html>