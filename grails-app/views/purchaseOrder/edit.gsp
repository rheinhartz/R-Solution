<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="layout"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Purchase Order</title>
    <style type="text/css">
    </style>
</head>
<body>
<h1>Edit Purchase Order</h1>
<hr>
<div id="center_container" class="center_container">
    
    <g:form action="save" method="POST">
    <input type="hidden" name="act" value="edit">
      
    <div class="parent_container">
        <g:render template="purchase_order" model="['purchase_orderInstance':purchase_orderInstance]" />
    </div>

    <div class="child_container">
      <div class="easyui-tabs tab_main">
        <div title="Child 1">
            <g:render template="item" model="['purchase_orderInstance':purchase_orderInstance]" />  
        </div>
      </div>
    </div>
      
      
    </g:form>

</div>

</body>
</html>
