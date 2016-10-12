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

  <div id="chartBarH"></div>

  <script>

    var plotBarH;

    //var dataBarH =  [ ['Heavy Industry', 120],['Retail', 9] ];
    var dataBarH =  []
    dataBarH.push( [120,'Apple'] )
    dataBarH.push( [9,'Orange'] )

    var optionBarH =  {
                        animate: !$.jqplot.use_excanvas,
                        seriesDefaults:{
                            renderer:$.jqplot.BarRenderer,
                            pointLabels: { show: true },
                            rendererOptions: {

                                barDirection: 'horizontal'
                                ,barWidth:40
                                //,barMargin: 8
                                //,barPadding: -10

                            }
                        },
                        axes: {
                            yaxis: {
                                renderer: $.jqplot.CategoryAxisRenderer
                            },
                            xaxis: {

                            }
                        },
                        highlighter: { show: false }
                      };

    if(plotBarH){
      plotBarH.destroy()
    }
    plotBarH = $.jqplot ('chartBarH', [dataBarH], optionBarH )


    $('#chartBarH').bind('jqplotDataClick',
      function (ev, seriesIndex, pointIndex, data) {
        alert(data)
      }
    );

    $('#chartBarH').bind('jqplotDataHighlight', 
      function (ev, seriesIndex, pointIndex, data) {
        var $this = $(this);                
        //$this.attr('title', formatter.format( data[0] ) );
        $this.attr('title', data[0] );
      }
    );

  </script>

</body>

</html>