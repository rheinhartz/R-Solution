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

  <div id="chartDonut"></div>

  <script>

    var plotDonut;

    //var dataDonut =  [['Heavy Industry', 120],['Retail', 9]];
    var dataDonut =  []
    dataDonut.push( ['Apple',120] )
    dataDonut.push( ['Orange', 9] )

    var optionDonut = { 
                        animate: !$.jqplot.use_excanvas,
                        seriesDefaults: {
                          // Make this a pie chart.
                          renderer: $.jqplot.DonutRenderer,
                          rendererOptions: {
                            // Put data labels on the pie slices.
                            // By default, labels show the percentage of the slice.
                            showDataLabels: true
                          }
                        }, 
                        legend: { show:true, location: 'e' }
                       };

    if(plotDonut){
      plotDonut.destroy()
    }
    plotDonut = $.jqplot ('chartDonut', [dataDonut], optionDonut )


    $('#chartDonut').bind('jqplotDataClick',
      function (ev, seriesIndex, pointIndex, data) {
        alert(data)
      }
    );

    $('#chartDonut').bind('jqplotDataHighlight', 
      function (ev, seriesIndex, pointIndex, data) {
        var $this = $(this);                
        $this.attr('title', data[1] );
      }
    );

  </script>

</body>

</html>