<!--
  To change this license header, choose License Headers in Project Properties.
  To change this template file, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
    <head>
        <meta name="layout" content="main"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>R-Solution</title>
        <style>
        </style>
    </head>
    
    <body>
        
        <table border="0" width="300" align="center">
            <tr>
                    <td align="center" height="20">&nbsp;</td>
            </tr>

            <tr>
                    <td align="center"><img src="${resource(dir: 'images', file: 'R-SolutionLogo.png')}" width="400"></td>
            </tr>


            <tr>
            <td align="center">
            <table border="0" width="350">
            <tr>
            <td>
            <div class="tabber">

            <div class="tabbertab" id="logintab" title="Login">
            <g:form action="logIn" method="POST">

                    <table border="0" align="center" width="250">
                    <tr height="50">
                            <td>&nbsp;</td>
                    </tr>
                    <tr>
                            <td class="td1">Username:</td>
                            <td class="td1"><input type="text" class="clstext" id="username" name="username" maxlength="20"></td>
                    </tr>
                    <tr>
                            <td class="td1">Password:</td>
                            <td class="td1"><input type="password" id="password" name="password" maxlength="20"></td>
                    </tr>
                    <tr>
                            <td class="td1">Remember</td>
                            <td class="td1"><g:checkBox name="rememberMe" value="${rememberMe}" /></td>
                    </tr>
                    <tr>
                            <td colspan="2" align="center">
                                    <div class="rflashmessage">
                                      <g:if test="${flash.message}">
                                          ${flash.message}
                                      </g:if>
                                    </div>
                                <br>
                                <br>
                                    <a href="javascript:document.forms[0].submit()" class="linkbutton">Login</a>
                                    <%--<a href="${createLink(uri: '/home')}" class="linkbutton">Test</a>--%>

                            </td>
                    </tr>
                    <tr height="50">
                            <td colspan="2" align="center"><div id="msg" class="msgDiv" align="center"></div></td>
                    </tr>
                    </table>
            </g:form>
            </div>

            <div class="tabbertab" id="logintab" title="About">
            <table border="0" align="center">
                    <tr>
                            <td align="center"><img src="${resource(dir: 'images', file: 'R-Logo.jpg')}"/></td>
                    </tr>
                    <tr>
                            <td class="td1" align="center">Created By:</td>
                    </tr>
                    <tr>
                            <td class="td1" align="center">Richard Eric France Trijo</td>
                    </tr>
                    <tr>
                            <td class="td1" align="center"><%=new Date()%></td>
                    </tr>
            </table>
            </div>

            </div>
            </td>
            </tr>
            </table>

            </td>
            </tr>
        </table>
        
        <script>
        $('body').bind('keypress', function(ev){
            var charcode;
            
            if(ev && ev.which){
                charcode = ev.which;
            }
            else if( window.event ){
                ev = window.event;
                charcode = ev.keyCode;
            }
            
            if(charcode == 13) {
              document.forms[0].submit()
            }
            
        });
        </script>
        
    </body>
    
</html>
