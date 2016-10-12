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

  <script>

    tickFormatterX       =   function (format, val) { 
                                      var number = val/1000;
                                      return number+"K";
                             }

    var plotZ2;

    var dataZ2 =  []
    
    //dataZ2 =  [
    //            ['Apple', 120]
    //           ,['Orange', 9]
    //          ];
    
    //dataZ2 =  []
    //dataZ2.push( ['Apple',120] )
    //dataZ2.push( ['Orange', 9] )

    
    var optionZ2 =   {
                        //seriesColors:['rgb(100,0,0)', 'rgb(0,100,0)', 'rgb(0,0,100)'],
                        animate: !$.jqplot.use_excanvas,
                        seriesDefaults: {
                          // Make this a pie chart.
                          renderer: $.jqplot.PieRenderer,
                          rendererOptions: {
                            // Put data labels on the pie slices.
                            // By default, labels show the percentage of the slice.
                            showDataLabels: true
                          }
                        }, 
                        //legend: { show:true, location: 's', placement: "inside" }
                     };

    $.ajax({
          url     : "${createLink(uri: '/test/jsonPopulationByRegion')}",
          asnyc   : false,
          dataType: "json",
          success : function( data ) {
                      dataZ2 = [];

                      
                      for(var i=0;i<data.length;i++){
                          var name  = (data[i].name)
                          var value = Number(data[i].value)
                          dataZ2.push( [ name , value ] );
                      }

                      if(plotZ2){
                        plotZ2.destroy()
                      }
                      plotZ2 = $.jqplot ('chartZ2', [dataZ2], optionZ2 )

                    },
          error   : function( result ){ alert(result) }
      });

    
    

    $('#chartZ2').bind('jqplotDataClick',
      function (ev, seriesIndex, pointIndex, data) {
        alert(data)
      }
    );

    $('#chartZ2').bind('jqplotDataHighlight', 
      function (ev, seriesIndex, pointIndex, data) {
        var $this = $(this);                
        $this.attr('title', data[1] );
      }
    );

  </script>

</body>

</html>