<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Purchase Order</title>
        <script type="text/javascript">
        tigra_tables('','trtbx', 1, 1, '#D8D8D8', '#D0D0D0', '#c6ecff', '#ffd8d8');
        $(document).ready(function() {
            $('#dt').dataTable();
        } );

        function rowSelected(id){
           $("#id").val(id)
        }
        
        function fAction(act){
         
          with( document.forms['frm'] ){
            
            if( act == "add" ){
              action = "${createLink(controller: 'purchaseOrder'  ,action: 'add')}"
            }
            
            if( act == "edit" ){
              if( $("#id").val() == "" || $("#id").val() == "0" ){
                return
              }
              action = "${createLink(controller: 'purchaseOrder'  ,action: 'edit')}"
            }
            
            if( act == "delete" ){
              alert("Disabled")
              return
              action = "${createLink(controller: 'purchaseOrder'  ,action: 'delete')}"
            }
            
            if( act == "view" ){
              action = "${createLink(controller: 'purchaseOrder'  ,action: 'view')}"
            }
            
            submit()
            
          }
        }
        </script>
    </head>
    <body>
        <h1>Purchase Orders</h1>
        <hr>
        
        <form name="frm" id="frm" method="post">
          <input type="hidden" name="id" id="id" value="0">
        </form>
        
        <div class="actionnav" style="vertical-align: middle">
            <a href="javascript:fAction('add')" class="linkbutton">
                <img src="${resource(dir: 'images/skin', file: 'database_add.png')}" />Add
            </a>
            <a href="javascript:fAction('edit')" class="linkbutton">
                <img src="${resource(dir: 'images/skin', file: 'database_edit.png')}" />Edit
            </a>
            <a href="javascript:fAction('delete')" class="linkbutton">
                <img src="${resource(dir: 'images/skin', file: 'database_delete.png')}" />Delete
            </a>
            <a href="javascript:fAction('view')" class="linkbutton">
                <img src="${resource(dir: 'images/skin', file: 'database_view.png')}" />View
            </a>
        </div>
        <div class="actioncontent">
        <table id="dt" class="trtbx">
            <thead>
                <tr>
                    <th>Document No</th>
                    <th>Location</th>
                    <th>Date</th>
                    <th>Kind</th>
                    <th>Type</th>
                </tr>
            </thead>
            
            <tfoot>
                <tr>
                    <td colspan="5"></td>
                </tr>
            </tfoot>
            
            <g:each in="${list}" status="i" var="listVar">
            <tr onclick="rowSelected(${list[i].purchase_order_id})">
                <td>${list[i].doc_no}</td>
                <td>${list[i].location}</td>
                <td>${(list[i].date).toString().substring(0,10)}</td>
                <td>${list[i].kind}</td>
                <td>${list[i].type}</td>
            </tr>
            </g:each>
            
        </table>
        </div>
    </body>
</html>
