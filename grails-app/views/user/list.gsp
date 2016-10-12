<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User</title>
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
              action = "${createLink(controller: 'user'  ,action: 'add')}"
            }
            
            if( act == "edit" ){
              if( $("#id").val() == "" || $("#id").val() == "0" ){
                return
              }
              action = "${createLink(controller: 'user'  ,action: 'edit')}"
            }
            
            if( act == "delete" ){
              alert("Disabled")
              return
              action = "${createLink(controller: 'user'  ,action: 'delete')}"
            }
            
            if( act == "view" ){
              action = "${createLink(controller: 'user'  ,action: 'view')}"
            }
            
            submit()
            
          }
        }
        </script>
    </head>
    <body>
        <h1>Users</h1>
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
                    <th>Username</th>
                    <th>First Name</th>
                    <th>Middle Name</th>
                    <th>Last Name</th>
                </tr>
            </thead>
            
            <tfoot>
                <tr>
                    <td colspan="4"></td>
                </tr>
            </tfoot>
            
            <g:each in="${list}" status="i" var="listVar">
            <tr onclick="rowSelected(${list[i].user_id})">
                <td>${list[i].username}</td>
                <td>${list[i].fname}</td>
                <td>${list[i].mname}</td>
                <td>${list[i].lname}</td>
            </tr>
            </g:each>
            
        </table>
        </div>
    </body>
</html>
