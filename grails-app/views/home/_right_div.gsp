<div id="profile_right" class="profile_right">    
    <div class="profile_stats_right">
    </div>
</div>

<div id="modules_right">
    <div title="Maintenance" style="padding:5px;" data-options="iconCls:''">
        <table id="" class="trtbl_right">
            <tr onclick="">
                <td style="width: 80%">Backups</td>
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
            </tr>
            <tr onclick="">
                <td style="width: 80%">Updates</td>
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
            </tr>
            <tr onclick="">
                <td style="width: 80%">Logs</td>
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
            </tr>
        </table>
    </div>
    <div title="Reports" style="padding:5px;" data-options="iconCls:''">
        <table id="" class="trtbl_right">
            <tr onclick="fViewRight('${createLink(uri: '/report/filter')}')">
                <td style="width: 80%">Item</td>
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
            </tr>
        </table>
    </div>
    <div title="Charts" style="padding:5px;" data-options="iconCls:''">
        <table id="" class="trtbl_right">
            <tr onclick="fViewRight('${createLink(uri: '/chart/filter')}')">
                <td style="width: 80%">Item</td>
                <td style="width: 10%"><img src="${resource(dir: 'images/icon', file: '')}" /></td>
            </tr>
        </table>
    </div>    
</div>

<%--
<br>
<br>
<h4>Controllers:</h4>
<ul>
    <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
        <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
        <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.logicalPropertyName}</g:link></li>
    </g:each>
</ul>
--%>

<script type="text/javascript"> 
    tigra_tables('','trtbl_right', 0, 0, '#D8D8D8', '#D0D0D0', '#557DBE', '#CD5C5C');

    function fViewRight(str){
       $("#center_div").load(str)
    }

    $(function() {
        $('#modules_right').accordion({
            selected: false,
            width: 200,
            height: 500,
            fit: false,
            border: true
        });
    });
</script>