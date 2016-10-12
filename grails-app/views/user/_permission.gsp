<script type="text/javascript">
    var childCount1 = ${userInstance?.permissions.size()} + 0;
  
    $(document).ready(function() {
        //$('#dt').dataTable();
    } );
    
    function fAddChild1() {

        var tbody     = $("#tbl_permission").find("tbody:eq(0)")
        var tr_first  = (tbody.find("tr:first")).clone()
        
        //var tbody_rows  = tbody.find("tr").length
        //alert(tbody.html())
        //alert(tr_first.html())
        //var tr = (tr_first.html()).replaceAll("variable",childCount1)
        //alert( $(tr) )
        
        var tr = $( "<tr>" + (tr_first.html()).replace(/variable/g, childCount1)  + "</tr>" )

        tr.appendTo(tbody)
        
        childCount1++;
    }
    
    function fConcat(i){
        var value = $("#controller_"+i).val() + ":" + $("#action_"+i).val()
        //alert(value)
        $("#permission_"+i).val( value )
    }
    
    
</script>

<div class="actionnav-right">
  <a href="javascript:fAddChild1()"     class="linkbutton"><img src="${resource(dir: 'images/skin', file: 'database_add.png')}" />Add</a>
  <a href="javascript:fEditChild1()"    class="linkbutton"><img src="${resource(dir: 'images/skin', file: 'database_edit.png')}" />Edit</a>
  <a href="javascript:fDeleteChild1()"  class="linkbutton"><img src="${resource(dir: 'images/skin', file: 'database_delete.png')}" />Delete</a>
</div>

<div id="childList" style="margin-top: 30px;margin-left: 10px;margin-right: 10px;">
  <div style="height:250px;overflow-y:scroll;overflow-x:hidden">
    <table id="tbl_permission" title="" style="width:950px;height:auto;">
      <thead>
        <tr>
          <th>Module</th>
          <th>Action</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
      <tr id="permission_tr_variable" style="display:none">
        <td width="100px">
          <g:select id='controller_variable' name='controller'  from="${controllerList}"  optionKey="fieldname" optionValue="fieldname" value="" onchange="fConcat(variable)"/>
        </td>
        <td width="100px">
          <g:select id='action_variable'     name='action'      from="${actionList}"      optionKey="fieldname" optionValue="fieldname" value="" onchange="fConcat(variable)"/>
        </td>
        <td>
          <input type="hidden" id="permission_variable" name="permissions[variable]" value="*:*">
        </td>
      </tr>
      <g:each var="permission" in="${userInstance.permissions}" status="i">
        <%
          def arr = permission.split(":")
        %>
        <tr id="permission_tr_${i}">
          <td width="100px">
            <g:select id='controller_${i}' name='controller'  from="${controllerList}"  optionKey="fieldname" optionValue="fieldname" value="${arr[0]}" onchange="fConcat(${i})"/>
          </td>
          <td width="100px">
            <g:select id='action_${i}'     name='action'      from="${actionList}"      optionKey="fieldname" optionValue="fieldname" value="${arr[1]}" onchange="fConcat(${i})"/>
          </td>
          <td>
            <input type="hidden" id="permission_${i}" name="permissions[${i}]" value="${permission}">
          </td>
        </tr>
      </g:each>
      </tbody>
    </table>
  </div>
</div>

