<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Item Chart</title>
        <script type="text/javascript">
          
        function generateChart(){
          var chartx = "";
          var paramx = "";
          
          with( document.forms['filter'] ){
            if( type.value == "Treemap" ){
              chartx = "${createLink(uri: '/chart/item_treemap')}"
            }
            else if( type.value == "Sunburst" ){
              chartx = "${createLink(uri: '/chart/item_sunburst')}"
            }
            
            action = chartx
            target = "_blank"
            submit()
          }
          
          $("#chart").attr("src",chartx)
        }
        
        </script>
        <style>
        iframe{
          width:97.3%;
          height:520px;
          margin-top: 20px;
          margin-left: 1%;
          margin-right: 1%;
          overflow: hidden;
          border: 4px solid gray;
        }
        </style>
    </head>
    <body>
        <h1>Item Chart</h1>
        <hr>
        
        <form name="filter">
        <table>
          <tr>
            <td>Chart Type</td>
            <td>
                <select name="type" >
                  <option value="Treemap">Treemap</option>
                  <option value="Sunburst">Sunburst</option>
                  <option value="Bargraph">Bargraph</option>
                  <option value="Piechart">Piechart</option>
                </select>
            </td>
          </tr>
          <tr>
            <td><input type="button" value="Generate" onclick="generateChart()"></td>
            <td></td>
          </tr>
        </table>
        </form>
          
        <iframe id="chart" src=""></iframe>
    </body>
</html>
