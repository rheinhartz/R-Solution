<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<title><g:if env="development">Grails Runtime Exception</g:if><g:else>Error</g:else></title>
		<meta name="layout" content="main">
                <title>404 Error</title>
		<g:if env="development"><link rel="stylesheet" href="${resource(dir: 'css', file: 'errors.css')}" type="text/css"></g:if>
	</head>
        <body>
                <h2>Hmm, Something's wrong here</h2>

                <p>
                    This page doesn't seem to exist anymore, or perhaps it never did. It's tough to say for sure.
                    What I do know, however, is that you can find plenty of other great content right over
                    <a href="${createLink(uri: '/')}"><b>here</b></a>.
                </p>
        </body>
</html>