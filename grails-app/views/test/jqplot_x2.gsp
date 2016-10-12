<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JQPlot Charts</title>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'R-SolutionLogo.ico')}" type="image/x-icon">

    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'jquery.jqplot.css')}">
    
    <script src="${resource(dir: 'js', file: 'jquery.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'jquery.jqplot.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'custom.js')}" type="text/javascript"></script>
    
    <script src="${resource(dir:'js/jqplot',file:'jqplot.donutRenderer.min.js')}"></script>
    <script src="${resource(dir:'js/jqplot',file:'jqplot.pieRenderer.min.js')}"></script>
    <script src="${resource(dir:'js/jqplot',file:'jqplot.barRenderer.min.js')}"></script>
    <script src="${resource(dir:'js/jqplot',file:'jqplot.canvasAxisLabelRenderer.min.js')}"></script>
    <script src="${resource(dir:'js/jqplot',file:'jqplot.canvasAxisTickRenderer.min.js')}"></script>
    <script src="${resource(dir:'js/jqplot',file:'jqplot.canvasTextRenderer.min.js')}"></script>
    <script src="${resource(dir:'js/jqplot',file:'jqplot.categoryAxisRenderer.min.js')}"></script>
    
    <style>
      body{
        font: 10px sans-serif;
      }
    </style>
  </head>
  
  <body style="overflow: hidden;">

  <div id="chartZ2"></div>

  <input type="button" value="asdsad" onclick="toJson()">

  <script>

  var data = []
  var arr = []


  arr.push("A")
  arr.push(1)
  arr.push(2)
  arr.push(3)
  arr.push(4)

  data.push( ["A", 7, 5, 3, 2] )
  data.push( arr )
  data.push( ["B", 7, 5, 3, 2] )
  data.push( ["C", 14, 9, 3, 8] )




  plot3 = $.jqplot('chartZ2', data, {
      //stackSeries: true,
      captureRightClick: true,
      seriesDefaults:{
          renderer:$.jqplot.BarRenderer,
          rendererOptions: {
              barDirection: 'vertical'
              ,barWidth:40
              //,barMargin: 8
              //,barPadding: -10
              ,fillToZero: true
              //,varyBarColor: true
          },
          pointLabels: {show: true}
      },
      legend: {
          show: true,
          location: 'e',
          placement: 'outside'
      }      
  });


  function toJson(){
      var url  = "${createLink(uri: '/test/toJson')}"
      var itemx = []
      $.ajax({
          url     : url,
          asnyc   : false,
          dataType: "json",
          success : function( data ) {
                      items = [];

                      alert( data.length )

                      for(var i=0;i<data.length;i++){
                          //itemx.push([data[i].namex,data[i].valuex]);
                      }

                      alert(itemx)

                    },
          error   : function( result ){ alert(result) }
      });
  }
  </script>
  
</body>

</html>