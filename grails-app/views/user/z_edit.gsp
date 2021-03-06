<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="layout"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User</title>
    <style type="text/css">
    </style>
    <script>
    function fSave(){
        
        if( $("#npass").val() != $("#cpass").val() ){
            //alert( $("#error").find("ul:eq(0)").html() )
            //alert( $("#error").find("li:last").html() )   
          
            var cond = false
            var ul = $("#error").find("ul:eq(0)")
            var ul_size = ul.find("li").length
            var li = $("<li>Password do not match</li>")
            
            for( i=0; i<ul_size; i++ ){
              
              if( ul.find("li:eq("+i+")").html() == "Password do not match" ){
                  cond = true
                  break;
              }
              
            }
            
            if( cond != true ){
              li.appendTo(ul)
            }

            return
        }else{
            //$("#flash").html("")
            $("#password").val( $("#npass").val() )
        }
    
        $("#permission_variable").prop("name","var")
    
        document.forms[0].submit()
    }
    </script>
</head>
<body>
<h1>Edit User</h1>
<hr>
<div id="center_container" class="center_container">
    
    <g:form action="save" method="POST">
    <input type="hidden" name="act" value="edit">
      
    <div class="parent_container">
      <div class="actionnav-right">
          <a href="javascript:fSave()" class="linkbutton">
              <img src="${resource(dir: 'images/skin', file: 'database_save.png')}" />Save
          </a>
      </div>
      <div class="easyui-tabs tab_main">
          <div title="Parent">
              <div id="flash"  class="message_flash">
                  <g:if test="${flash.message}"><ul><li>${flash.message}</li></ul></g:if>
              </div>
              <div id="error" class="message_error">
                  <g:renderErrors bean="${userInstance}" as="list" />
              </div>

              <input type="hidden" name="password" id="password">
              <input type="hidden" name="isdeleted">
              <input type="hidden" name="id" value="${userInstance?.id}" />
              
              <table>
                  <tr>
                      <td class="${hasErrors(bean:userInstance , field:'username','td_error')}">Username</td>
                      <td>: <input type="text" name="username" value="${userInstance?.username}"></td>
                  </tr>
                  <tr>
                      <td>New Password</td>
                      <td>: <input type="password" name="npass" id="npass" value="${userInstance?.npass}"></td>
                  </tr>
                  <tr>
                      <td>Confirm Password</td>
                      <td>: <input type="password" name="cpass" id="cpass" value="${userInstance?.cpass}"></td>
                  </tr>
                  <tr>
                      <td class="${hasErrors(bean:userInstance , field:'fname','td_error')}">First Name</td>
                      <td>: <input type="text" name="fname" value="${userInstance?.fname}"></td>
                  </tr>
                  <tr>
                      <td class="${hasErrors(bean:userInstance , field:'mname','td_error')}">Middle Name</td>
                      <td>: <input type="text" name="mname" value="${userInstance?.mname}"></td>
                  </tr>
                  <tr>
                      <td class="${hasErrors(bean:userInstance , field:'lname','td_error')}">Last Name</td>
                      <td>: <input type="text" name="lname" value="${userInstance?.lname}"></td>
                  </tr>
                  <tr>
                      <td>Active</td>
                      <td>: <g:checkBox name="isactive"/></td>
                  </tr>
              </table>
          </div>
      </div>
    </div>

    <div class="child_container">
    <div class="easyui-tabs tab_main">
        <div title="Child 1">
            <g:render template="permission" model="['userInstance':userInstance]" />
        </div>
    </div>
    </div>
      
      
    </g:form>

</div>

</body>
</html>
