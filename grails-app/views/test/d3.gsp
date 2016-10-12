<html>
  <head>
    <meta name="layout" content="layout"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>D3 Visualization</title>
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
 
  <iframe src="${createLink(uri: '/test/d3_choropleth')}"></iframe>
  <iframe src="${createLink(uri: '/test/d3_treemap')}"></iframe>
  <iframe src="${createLink(uri: '/test/d3_sunburst')}"></iframe>
    
  </body>
</html>
