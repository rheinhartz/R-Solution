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

  <div id="chartBarV"></div>

  <script>

    var plotBarV;

    //var dataBarV =  [ ['Heavy Industry', 120],['Retail', 9] ];
    var dataBarV =  []
    dataBarV.push( ['Apple',120] )
    dataBarV.push( ['Orange', 9] )

    var optionBarV =  {
                        animate: !$.jqplot.use_excanvas,
                        seriesDefaults:{
                            renderer:$.jqplot.BarRenderer,
                            pointLabels: { show: true },
                            rendererOptions: {

                                barDirection: 'vertical'
                                ,barWidth:40
                                //,barMargin: 8
                                //,barPadding: -10

                            }
                        },
                        axes: {
                            xaxis: {
                                renderer: $.jqplot.CategoryAxisRenderer
                            },
                            yaxis: {

                            }
                        },
                        highlighter: { show: false }
                      };

    if(plotBarV){
      plotBarV.destroy()
    }
    plotBarV = $.jqplot ('chartBarV', [dataBarV], optionBarV )


    $('#chartBarV').bind('jqplotDataClick',
      function (ev, seriesIndex, pointIndex, data) {
        alert(data)
      }
    );

    $('#chartBarV').bind('jqplotDataHighlight', 
      function (ev, seriesIndex, pointIndex, data) {
        var $this = $(this);                
        $this.attr('title', data[1] );
      }
    );

  </script>

</body>

</html>