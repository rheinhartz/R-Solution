<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta name="layout" content="layout"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User</title>
        <style type="text/css">
        
        </style>
        <script type="text/javascript">
        tigra_tables('','trtby', 1, 1, '#D8D8D8', '#D0D0D0', 'lightblue', '#FF99CC');
        $(document).ready(function() {
            $('#dt').dataTable();
        } );

        function rowSelected(id){
            //alert("User.id : "+id);
        }
        </script>
    </head>
    <body>
        <h1>Users</h1>
        <hr>
        <div id="center_container" class="center_container">
        <div>
            <a href="javascript:f_edit()"   class="linkbutton">Print</a>
            <a href="javascript:f_delete()" class="linkbutton">Save As</a>
        </div>
        
        <br>
        
        <g:if test="${flash.message}">
            <div id="flash"  class="message_flash">
                <g:if test="${flash.message}"><ul><li>${flash.message}</li></ul></g:if>
            </div>
        </g:if>
        
        <div class="actioncontent">
        <table id="dt" class="trtby">
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
            <tr onclick="rowSelected(${list[i].id})">
                <td>${list[i].username}</td>
                <td>${list[i].fname}</td>
                <td>${list[i].mname}</td>
                <td>${list[i].lname}</td>
            </tr>
            </g:each>
            
        </table>
        </div>
        </div>
    </body>
</html>
