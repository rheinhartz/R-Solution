<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><g:layoutTitle default="R-Solution"/></title>

    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'R-SolutionLogo.ico')}" type="image/x-icon">

    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css/easyui/custom', file: 'easyui.css')}">
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css/easyui', file: 'icon.css')}">
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'jquery.dataTables.css')}">
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'jquery.jqplot.css')}">
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'tabber.css')}">
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'layout.css')}">
    

    <script src="${resource(dir: 'js', file: 'jquery.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'jquery.easyui.js')}" type="text/javascript"></script>
    
    <script src="${resource(dir: 'js', file: 'jquery.dataTables.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'jquery.jqplot.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'tabber.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'tigra.table.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'd3.v3.js')}" type="text/javascript"></script>
    <script src="${resource(dir: 'js', file: 'layout.js')}" type="text/javascript"></script>
    
    <script src="${resource(dir: 'js', file: 'custom.js')}" type="text/javascript"></script>
    
    <g:layoutHead/>
    <r:layoutResources />
    
</head>
<body>
    <div class="main_nav" id="main_nav" role="main_nav">
        <div id="" class="main_nav_container">
            <div id="rlogo" class="main_nav_rlogo">
                <img src="${resource(dir: 'images', file: 'R-SolutionLogoMin.png')}" width="100"/>
            </div>
            <div id="main_nav_buttons" class="main_nav_buttons">
                <a href="${createLink(uri: '/home')}" class="linkbutton">Home</a>
                <a href="${g.createLink(controller: 'auth', action: 'logOut')}" class="linkbutton">Logout</a>
            </div>
        </div>
    </div>
    <div class="header" id="header" role="banner"></div>
    
    <g:layoutBody/>
    
    <div class="footer" role="contentinfo"></div>
    <div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
    <g:javascript library="application"/>
    <r:layoutResources />
</body>
</html>
