<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<%@ page contentType="text/html;charset=UTF-8" %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>R-Solution</title>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'R-SolutionLogo.ico')}" type="image/x-icon">

    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css/easyui/custom', file: 'easyui.css')}">
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css/easyui', file: 'icon.css')}">
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'tabber.css')}">
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'main.css')}">

    <script src="${resource(dir: 'js', file: 'jquery.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'jquery.easyui.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'tabber.js')}" type="text/javascript"></script>

    <script type="text/javascript">
        /*
        $(function() {
            $('#tab_main').tabs({
                //tabWidth: 400px
                width: 800,
                height: 300,
                //icon:'icon-add.jpg',
                onSelect:function(param){
                    //alert(title)
                }
            });

            $('#accordion_main').accordion({
                fit: false,
                border: true
                //width: auto,
                //height: auto
            });
        });
        */
    </script>
    
    <g:layoutHead/>
    <r:layoutResources />
    
</head>
<body>
    <div class="header" id="header" role="banner"></div>
    <g:layoutBody/>
    <div class="footer" role="contentinfo"></div>
    <div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
    <g:javascript library="application"/>
    <r:layoutResources />
</body>
</html>
