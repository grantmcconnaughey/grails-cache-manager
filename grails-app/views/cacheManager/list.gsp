<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>App Caches</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/pure/0.5.0/pure-min.css">
    <style>
        #content {
            width: 95%;
            margin: 0 auto;
        }

        .pure-button {
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 4px;
            padding: .3em 1em;
        }

        .go-button {
            margin-left: 1em;
        }

        .enabled {
            background-color: rgba(28, 184, 65, 0.25) !important;
        }

        .disabled {
            background-color: rgba(202, 60, 60, 0.25) !important;
        }
    </style>
</head>
<body>
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

        <table class="pure-table pure-table-striped">
            <thead>
                <tr>
                    <th>Cache Name</th>
                    <th>Enabled?</th>
                    <cacheManager:appSupportsTTL>
                        <th>Time To Live (seconds)</th>
                        <th>Set new Time To Live</th>
                    </cacheManager:appSupportsTTL>
                    <th>Clear Cache</th>
                </tr>
            </thead>
            <tbody>
            <g:each in="${caches}" var="cache">
                <tr>
                    <td>${cache.name}</td>
                    <td class="${cache.nativeCache.isDisabled() ? 'disabled' : 'enabled'}">${cache.nativeCache.isDisabled() ? 'Disabled' : 'Enabled'}</td>
                    <cacheManager:appSupportsTTL>
                        <td style="text-align: right;">${cache.nativeCache.cacheConfiguration.timeToLiveSeconds}</td>
                        <td>
                            <g:form action="changeTimeToLive" method="POST">
                                <g:select name="newTimeToLiveSeconds" optionKey="key" optionValue="value"
                                          from="${grailsApplication.config.grails.plugin.cacheManager.newTTLValues}" />
                                <g:hiddenField name="cacheName" value="${cache.name}" />
                                <g:submitButton name="submit" value="Go" class="pure-button pure-button-primary go-button" />
                            </g:form>
                        </td>
                    </cacheManager:appSupportsTTL>
                    <td><g:link action="clear" params="[cacheName: cache.name]" class="pure-button">Clear</g:link></td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</body>
</html>