<html>
  <head>
    <meta name="layout" content="layout"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JQPlot Charts</title>
    <script src="${resource(dir:'js',file:'jquery.js')}"></script>
    <script src="${resource(dir: 'js', file: 'custom.js')}"></script>
    <style>
      iframe{
        width:95%;
        height:520px;
        margin-top: 20px;
        margin-left: 20px;
        overflow: hidden;
        border: 4px solid gray;
      }
    </style>
    
  </head>
  
  <body>
  
  <hr>
  <h1 align="center">Sample</h1>
  <hr>
  <iframe src="${createLink(uri: '/test/jqplot_barh')}"></iframe>
  <iframe src="${createLink(uri: '/test/jqplot_barv')}"></iframe>
  <iframe src="${createLink(uri: '/test/jqplot_pie')}"></iframe>
  <iframe src="${createLink(uri: '/test/jqplot_donut')}"></iframe>
  
  <br>
  <br>
  <br>
  <hr>
  <h1 align="center">Data</h1>
  <hr>
  <iframe src="${createLink(uri: '/test/jqplot_z1')}"></iframe>
  <iframe src="${createLink(uri: '/test/jqplot_z2')}"></iframe>
  
  <br>
  <br>
  <br>
  <hr>
  <h1 align="center">Custom</h1>
  <hr>

  <iframe src="${createLink(uri: '/test/jqplot_x1')}"></iframe>
  <iframe src="${createLink(uri: '/test/jqplot_x2')}"></iframe>
  
  
  
  </body>
</html>