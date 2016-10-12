

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="layout" />
        <title>Create Author</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'home')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Author List</g:link></span>
        </div>
        <div class="body">
            <h1>Create Author</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${authorInstance}">
            <div class="errors">
                <g:renderErrors bean="${authorInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <g:render template="author" model="['authorInstance':authorInstance]"/>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
