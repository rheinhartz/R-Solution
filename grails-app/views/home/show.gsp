<html>
<head>
    <meta name="layout" content="layout"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home</title>
</head>
<body>
<h1>Home</h1>
<hr>

<div id="container" class="container">
  <div class="easyui-layout" style="width:1024px;height:1000px;">
    <div data-options="region:'west',split:true" title=" " style="width:207px;">
      <g:render template="left_div" model=""/>
    </div>
    <div data-options="region:'center',title:'Main',iconCls:'icon-window'">
      <div id="center_div" style="overflow-x: hidden;">
        <g:render template="center_div" model=""/>
      </div>
    </div>
    <div data-options="region:'east',split:true" title=" " style="width:207px;">
      <g:render template="right_div" model=""/>
    </div>
  </div>
</div>

</body>
</html>