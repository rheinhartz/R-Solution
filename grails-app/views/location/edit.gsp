<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="layout"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Location</title>
    <style type="text/css">
    </style>
</head>
<body>
<h1>Edit Location</h1>
<hr>
<div id="center_container" class="center_container">
    
    <g:form action="save" method="POST">
    <input type="hidden" name="act" value="edit">
      
    <div class="parent_container">
        <g:render template="location" model="['locationInstance':locationInstance]" />
    </div>

    <div class="child_container">
    </div>
      
      
    </g:form>

</div>

</body>
</html>
