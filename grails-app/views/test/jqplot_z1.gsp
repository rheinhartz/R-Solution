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

  <div id="chartZ1"></div>

  <script>

    tickFormatterX       =   function (format, val) { 
                                      var number = val/1000;
                                      return number+"K";
                             }

    var plotZ1;

    var dataZ1 =  []
    
    //dataZ1 =  [
    //            ['Apple', 120]
    //           ,['Orange', 9]
    //          ];
    
    //dataZ1 =  []
    //dataZ1.push( ['Apple',120] )
    //dataZ1.push( ['Orange', 9] )

    
    var optionZ1 =  {
                        //seriesStack:true,
                        //animate: !$.jqplot.use_excanvas,
                        seriesColors:['rgb(100,0,0)', 'rgb(0,100,0)', 'rgb(0,0,100)'],
                        seriesDefaults:{
                            renderer:$.jqplot.BarRenderer,
                            pointLabels: { show: true },
                            rendererOptions: {

                                barDirection: 'vertical'
                                ,barWidth:40
                                //,barMargin: 8
                                //,barPadding: -10
                                ,fillToZero: true
                                ,varyBarColor: true
                            }
                        },
                        axes: {
                            xaxis: {
                                pad: 0,
                                renderer: $.jqplot.CategoryAxisRenderer
                            },
                            yaxis: {
                                pad: 0,
                                //min: -1000,
                                //max: 10000,
                                //numberTicks: 12,
                                tickOptions: {
                                    formatter: tickFormatterX
                                }
                            }
                        },
                        highlighter: { show: false }
                      };

    $.ajax({
          url     : "${createLink(uri: '/test/jsonPopulationByRegion')}",
          asnyc   : false,
          dataType: "json",
          success : function( data ) {
                      dataZ1 = [];

                      
                      for(var i=0;i<data.length;i++){
                          var name  = (data[i].name)
                          var value = Number(data[i].value)
                          dataZ1.push( [ name , value ] );
                      }

                      if(plotZ1){
                        plotZ1.destroy()
                      }
                      plotZ1 = $.jqplot ('chartZ1', [dataZ1], optionZ1 )

                    },
          error   : function( result ){ alert(result) }
      });

    
    

    $('#chartZ1').bind('jqplotDataClick',
      function (ev, seriesIndex, pointIndex, data) {
        alert(data)
      }
    );

    $('#chartZ1').bind('jqplotDataHighlight', 
      function (ev, seriesIndex, pointIndex, data) {
        var $this = $(this);                
        $this.attr('title', data[1] );
      }
    );

  </script>

</body>

</html>