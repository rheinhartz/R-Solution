
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <script src="${resource(dir: 'js', file: 'd3.v3.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.js')}"></script>
    <style>  
      
    path {
      stroke: #fff;
      fill-rule: evenodd;
    }
    
    svg{
      height: 500px;
      width: 700px;
      /*width: 80%;*/
    }
    
    #chart {
      display: inline-block;
      float:left;
      width: 700px;
      /*width: 80%;*/
      height: 500px;
      margin-top: 0px;
      text-overflow: clip;
      padding-top: 0px;
    }
    
    .divList{
       font: 10px sans-serif;
       text-overflow: ellipsis;
    
       position: absolute;
       right: 0px;
       margin-top: 0px;
       margin-right: 10px;
       
       width:200px;
       height:500px; 
       background-color:#E0ECFF;
       
       overflow-y: scroll;
    }
    
    </style>
</head>

<body style="overflow: hidden;">
    <div id="chart">
    </div>
    <div id="divList" class="divList">
        Loading...
    </div>
</body>

<script>

var //width = screen.width - (screen.width * 0.21),
    width = 700,
    height = 480,
    radius = Math.min(width, height) / 2;

var x = d3.scale.linear()
    .range([0, 2 * Math.PI]);

var y = d3.scale.sqrt()
    .range([0, radius]);

var color = d3.scale.category20c();

var svg = d3.select("#chart").append("svg")
    .attr("width", width)
    .attr("height", height)
  .append("g")
    .attr("transform", "translate(" + width / 2 + "," + (height / 2 + 10) + ")");

var partition = d3.layout.partition()
    .value(function(d) { return d.size; });

var arc = d3.svg.arc()
    .startAngle(function(d) { return Math.max(0, Math.min(2 * Math.PI, x(d.x))); })
    .endAngle(function(d) { return Math.max(0, Math.min(2 * Math.PI, x(d.x + d.dx))); })
    .innerRadius(function(d) { return Math.max(0, y(d.y)); })
    .outerRadius(function(d) { return Math.max(0, y(d.y + d.dy)); });

d3.json("${createLink(uri: '/chart/jsonChartItem2')}", function(error, root) {
  var path = svg.selectAll("path")
      .data(partition.nodes(root))
    .enter().append("path")
      .attr("d", arc)
      /*Eric*/
      //.style("fill", function(d) { return color((d.children ? d : d.parent).name); })
      .style("fill", function(d) { return d.children ? color(d.name) : color(d.name); })
      .on("click", click);

  click(root);
  
  function click(d) {
    
    var str = ""
    str += "<table border=0 style='width: 100%; white-space: nowrap; table-layout: fixed;'>"
    str += "<tr><td style='width:70px'>Name</td><td style='width:*; text-align: right'>Value</td></tr>"
    for( r=0; r<d.children.length; r++ ){
        var n = d.children[r].name;
        //var v = formatter.format( d._children[r].value );
        var v = (d.children[r].value).toFixed(2);
        //var c = color( (d.children ? d : d.parent).name )
        
        var c = color( d.children[r].name )
        
        var trstyle   = "background-color:" + c + ";" + "opacity:0.8;"
        var tdstyle1  = "overflow: hidden; text-overflow: ellipsis;opacity:2"
        
        str += "<tr style='"+trstyle+"' onclick='transition'>"
                 +"<td style='"+tdstyle1+"'>"+n+"</td>"
                 +"<td style='text-align: right'>"+v+"</td>"
             + "</tr>"

    }
    str += "</table>"
      
    $("#divList").html( str )
    
    path.transition()
      .duration(750)
      .attrTween("d", arcTween(d));
  }
});

//d3.select(self.frameElement).style("height", height + "px");

// Interpolate the scales!
function arcTween(d) {
  var xd = d3.interpolate(x.domain(), [d.x, d.x + d.dx]),
      yd = d3.interpolate(y.domain(), [d.y, 1]),
      yr = d3.interpolate(y.range(), [d.y ? 20 : 0, radius]);
  return function(d, i) {
    return i
        ? function(t) { return arc(d); }
        : function(t) { x.domain(xd(t)); y.domain(yd(t)).range(yr(t)); return arc(d); };
  };
}
    
</script>


</html>