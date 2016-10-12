
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <script src="${resource(dir: 'js', file: 'd3.v3.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.js')}"></script>
    <style>  
      
    svg {
      font: 10px sans-serif;
      overflow: hidden;
      text-overflow: clip;
    }

    #chart {
      display: inline-block;
      float:left;
      width: 730px;
      height: 500px;
      background: #ddd;
      margin-top: 0px;
      text-overflow: clip;
    }

    text {
      pointer-events: none;
    }

    .grandparent text {
      font-weight: bold;
    }

    rect {
      fill: none;
      stroke: #ffffff;
    }

    rect.parent,
    .grandparent rect {
      stroke-width: 2px;
    }

    .grandparent rect {
      fill: orange;
    }

    .grandparent:hover rect {
      fill: #ee9700;
    }

    .children rect.parent,
    .grandparent rect {
      cursor: pointer;
    }

    .children rect.parent {
      fill: #bbb;
      fill-opacity: .5;
    }

    .children:hover rect.child {
      fill: #bbb;
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
    <p id="chart">
    </p>
    <div id="divList" class="divList">
        Loading...
    </div>
</body>

<script>

var margin = {top: 20, right: 0, bottom: 0, left: 0},
    width = 730,
    height = 500 - margin.top - margin.bottom,
    formatNumber = d3.format(",d"),
    transitioning;

var color = d3.scale.category20c();

var x = d3.scale.linear()
    .domain([0, width])
    .range([0, width]);

var y = d3.scale.linear()
    .domain([0, height])
    .range([0, height]);

var treemap = d3.layout.treemap()
    .children(function(d, depth) { return depth ? null : d._children; })
    .sort(function(a, b) { return a.value - b.value; })
    .ratio(height / width * 0.5 * (1 + Math.sqrt(5)))
    .round(false);

var svg = d3.select("#chart").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.bottom + margin.top)
    .style("margin-left", -margin.left + "px")
    .style("margin.right", -margin.right + "px")
    .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
    .style("shape-rendering", "crispEdges")
    ;

var grandparent = svg.append("g")
    .attr("class", "grandparent");

grandparent.append("rect")
    .attr("y", -margin.top)
    .attr("width", width)
    .attr("height", margin.top);

grandparent.append("text")
    .attr("x", 6)
    .attr("y", 6 - margin.top)
    .attr("dy", ".75em");
    
d3.json("${createLink(uri: '/test/jsonPopulationTreeMap')}", function(root) {
  initialize(root);
  accumulate(root);
  layout(root);
  display(root);

  function initialize(root) {
    root.x = root.y = 0;
    root.dx = width;
    root.dy = height;
    root.depth = 0;
  }

  // Aggregate the values for internal nodes. This is normally done by the
  // treemap layout, but not here because of our custom implementation.
  // We also take a snapshot of the original children (_children) to avoid
  // the children being overwritten when when layout is computed.
  function accumulate(d) {
    return (d._children = d.children)
        ? d.value = d.children.reduce(function(p, v) { return p + accumulate(v); }, 0)
        : d.value;
  }

  // Compute the treemap layout recursively such that each group of siblings
  // uses the same size (1×1) rather than the dimensions of the parent cell.
  // This optimizes the layout for the current zoom state. Note that a wrapper
  // object is created for the parent node for each group of siblings so that
  // the parent’s dimensions are not discarded as we recurse. Since each group
  // of sibling was laid out in 1×1, we must rescale to fit using absolute
  // coordinates. This lets us use a viewport to zoom.
  function layout(d) {
    if (d._children) {
      treemap.nodes({_children: d._children});
      d._children.forEach(function(c) {
        c.x = d.x + c.x * d.dx;
        c.y = d.y + c.y * d.dy;
        c.dx *= d.dx;
        c.dy *= d.dy;
        c.parent = d;
        layout(c);
      });
    }
  }

  function display(d) {    
    grandparent
        .datum(d.parent)
        .on("click", transition)
        .select("text")
        .text(name(d));

    var g1 = svg.insert("g", ".grandparent")
        .datum(d)
        .attr("class", "depth");

    var g = g1.selectAll("g")
        .data(d._children)
      .enter().append("g");

    g.filter(function(d) { return d._children; })
        .classed("children", true)
        .on("click", transition);
        
    g.selectAll(".child")
        .data(function(d) { return d._children || [d]; })
      .enter().append("rect")
        .attr("class", "child")
        /*Eric*/
        //.style("fill",function(d) { return d._children ? color(d.name) : null; })
        //.style("opacity","0.1")
        .call(rect);

    g.append("rect")
        .attr("class", "parent")
        /*Eric*/
        //.style("fill",function(d) { return d._children ? color(d.name) : color(d.parent.name); })
        .style("fill",function(d) { return d._children ? color(d.name) : color(d.name); })
        .call(rect)
      .append("title")
        .text(function(d) { return formatNumber(d.value); });

    g.append("text")
        .attr("dy", ".75em")
        //.attr("style", "font-size:20px")
        .text(function(d) {
            
            //alert(g.rect)
        
            var namex = d.name
            if( d.name.length > 6 ){
                //namex = (d.name).substring(0,2) + "..."
            }
            return namex;

        })
        .call(text);
    
    
    function transition(d) {    
    
      if (transitioning || !d) return;
      transitioning = true;
      
      
      
      var g2 = display(d),
          t1 = g1.transition().duration(750),
          t2 = g2.transition().duration(750);

      // Update the domain only after entering new elements.
      x.domain([d.x, d.x + d.dx]);
      y.domain([d.y, d.y + d.dy]);

      // Enable anti-aliasing during the transition.
      svg.style("shape-rendering", null);

      // Draw child nodes on top of parent nodes.
      svg.selectAll(".depth").sort(function(a, b) { return a.depth - b.depth; });

      // Fade-in entering text.
      g2.selectAll("text").style("fill-opacity", 0);

      // Transition to the new view.
      t1.selectAll("text").call(text).style("fill-opacity", 0);
      t2.selectAll("text").call(text).style("fill-opacity", 1);
      t1.selectAll("rect").call(rect);
      t2.selectAll("rect").call(rect);

      // Remove the old node when the transition is finished.
      t1.remove().each("end", function() {
        svg.style("shape-rendering", "crispEdges");
        transitioning = false;
      });
    }
    
    /*
    var formatter       =   new Intl.NumberFormat('en-US', {
                                style: 'currency',
                                currency: 'PHP',
                                minimumFractionDigits: 2,
                            });    
    */
   
    var str = ""
    str += "<table border=0 style='width: 100%; white-space: nowrap; table-layout: fixed;'>"
    str += "<tr><td style='width:70px'>Name</td><td style='width:*; text-align: right'>Value</td></tr>"
    for( r=0; r<d._children.length; r++ ){
        var n = d._children[r].name;
        var v = (d._children[r].size).toFixed(2);
        //var v = formatter.format( d._children[r].value );
        
        //var c = ( d._children 0 ?  color(d._children[r].name) : "gray");
        var c = color(d._children[r].name)
        //var c = d.parent ? color(d.name) : color(d._children[r].name) //d._children[r] ? color(d._children[r].name) : "gray";
           
        var trstyle   = "background-color:" + c + ";" + "opacity:0.8;"
        var tdstyle1  = "overflow: hidden; text-overflow: ellipsis;opacity:2"
        
        str += "<tr style='"+trstyle+"' onclick='transition'>"
                 +"<td style='"+tdstyle1+"'>"+n+"</td>"
                 +"<td style='text-align: right'>"+v+"</td>"
             + "</tr>"
           
        //str += "<tr><td style=''>"+n+"</td><td style='text-align: right'>"+v+"</td></tr>"
        //str = str + "<br>"
        //str = str + d._children[r].name + " -- " + d._children[r].value
    }
    str += "</table>"
      
    $("#divList").html( str )

    return g;
  }

  function text(text) {
    text.attr("x", function(d) { return x(d.x) + 6; })
        .attr("y", function(d) { return y(d.y) + 6; });
  }

  function rect(rect) {
    rect.attr("x", function(d) { return x(d.x); })
        .attr("y", function(d) { return y(d.y); })
        .attr("width", function(d) { return x(d.x + d.dx) - x(d.x); })
        .attr("height", function(d) { return y(d.y + d.dy) - y(d.y); });
  }

  function name(d) {
    return d.parent
        ? name(d.parent) + "." + d.name
        : d.name;
  }
  
});
    
</script>


</html>