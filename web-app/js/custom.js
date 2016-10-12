//----------------------------------------------------------------------------------------------------------------------
//Practice Zone
//----------------------------------------------------------------------------------------------------------------------
//var x = .rem
//var tbody     = $("#tbl_permission").find("tbody:eq(0)").rem
//var d = new Date()
//alert( d.  )

//----------------------------------------------------------------------------------------------------------------------
//Global Action Binding
//----------------------------------------------------------------------------------------------------------------------
$('input[type^="number"]').bind('click', function(){
     //alert( "sadas" )
});


//----------------------------------------------------------------------------------------------------------------------
//JqPlot Global Functions and Variables
//----------------------------------------------------------------------------------------------------------------------
    var plot1;
    var timeout;
    var timer = 0;
    var stock_temp = 0;
    
    /*
    var formatter       =   new Intl.NumberFormat('en-US', {
                                style: 'currency',
                                currency: 'PHP',
                                minimumFractionDigits: 2
                            });

    var tickFormatter       =   function (format, val) { 
                                var number = val/1000000000;
                                return number+"B";
                            }

    var jqplot_option_pie   =   { 
                                seriesDefaults: {
                                  // Make this a pie chart.
                                  renderer: $.jqplot.PieRenderer,
                                  rendererOptions: {
                                    // Put data labels on the pie slices.
                                    // By default, labels show the percentage of the slice.
                                    showDataLabels: true
                                  }
                                }, 
                                legend: { show:true, location: 'e' }
                            }

    var jqplot_option_donut =   { 
                                seriesDefaults: {
                                  // Make this a pie chart.
                                  renderer: $.jqplot.DonutRenderer,
                                  rendererOptions: {
                                    //sliceMargin: 1,
                                    startAngle: -90,
                                    showDataLabels: true,
                                    dataLabels: 'percent'
                                  }
                                }, 
                                legend: { show:true, location: 'e' }
                            }

    var jqplot_option_hbar =    {
                                seriesDefaults: {
                                    renderer:$.jqplot.BarRenderer,
                                    //pointLabels: { show: true, location: 'e', edgeTolerance: -15 },
                                    shadowAngle: 135,
                                    rendererOptions: {

                                        barDirection: 'horizontal'
                                        ,barWidth:10
                                        //,barMargin: 8
                                        ,barPadding: -10

                                    }
                                },
                                axes: {
                                    yaxis: {
                                        renderer: $.jqplot.CategoryAxisRenderer
                                    },
                                    xaxis: {
                                        pad: 1.05,
                                        tickOptions: {
                                            formatter: tickFormatter
                                        }
                                        //tickOptions: {formatString: 'P%d'}
                                    }
                                }
                            }

    var jqplot_option_vbar =    {
                                seriesDefaults: {
                                    renderer:$.jqplot.BarRenderer,
                                    //pointLabels: { show: true, location: 'e', edgeTolerance: -15 },
                                    shadowAngle: 135,
                                    rendererOptions: {

                                        barDirection: 'vertical'
                                        //,barWidth:15
                                        //,barMargin: 8
                                        //,barPadding: -10

                                    }
                                },
                                axes: {
                                    xaxis: {
                                        renderer: $.jqplot.CategoryAxisRenderer
                                    },
                                    yaxis: {
                                        pad: 1.05,
                                        tickOptions: {
                                            formatter: tickFormatter
                                        }
                                    }
                                }
                            }
     */