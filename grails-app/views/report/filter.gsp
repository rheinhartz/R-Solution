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
          
        function generateReport(){
          var paramx = "";
          
          with( document.forms['filter'] ){
            
            if( type.value == "PDF" ){
              action = "${createLink(uri: '/report/item_listing_pdf')}" + paramx
              submit();
            }
            
          }
          
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
        
        <form name="filter" action="" target="_blank">
        <table>
          <tr>
            <td>File Type</td>
            <td>
                <select name="type" >
                  <option value="PDF">PDF</option>
                  <option value="EXCEL">EXCEL</option>
                  <option value="HTML">HTML</option>
                </select>
            </td>
          </tr>
          <tr>
            <td>Location</td>
            <td>
               <g:select name='location_id' from="${listLocation}" class="easyui-combobox"
                            style="width:155px"
                            optionKey="idname" optionValue="fieldname" value=""
                            data-options="editable: false"
               /> 
            </td>
          </tr>
          <tr>
            <td><input type="button" value="Generate" onclick="generateReport()"></td>
            <td></td>
          </tr>
        </table>
        </form>
    </body>
</html>
