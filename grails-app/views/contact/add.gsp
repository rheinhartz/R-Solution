<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="layout"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Contact</title>
    <style type="text/css">
    </style>
</head>
<body>
<h1>Add Contact</h1>
<hr>
<div id="center_container" class="center_container">
    
    <g:form action="save" method="POST">
    <input type="hidden" name="act" value="add">
      
    <div class="parent_container">
        <g:render template="contact" model="['contactInstance':contactInstance]" />
    </div>

    <div class="child_container">
    </div>
      
      
    </g:form>

</div>

</body>
</html>
