<div class="actionnav-right">
<a href="javascript:fAddChild1()"     class="linkbutton"><img src="${resource(dir: 'images/skin', file: 'database_add.png')}" />Add</a>
  <a href="javascript:fEditChild1()"    class="linkbutton"><img src="${resource(dir: 'images/skin', file: 'database_edit.png')}" />Edit</a>
  <a href="javascript:fDeleteChild1()"  class="linkbutton"><img src="${resource(dir: 'images/skin', file: 'database_delete.png')}" />Delete</a>
</div>

<div id="childList" style="margin-top: 30px;margin-left: 10px;margin-right: 10px;">
  <div style="height:250px;overflow-y:scroll;overflow-x:hidden">
    <table id="tbl_pack"  title="" style="width:950px;height:auto;">
      <thead>
        <tr>
          <th>Unit</th>
          <th>Pack</th>
        </tr>
      </thead>
      <tbody>
      <tr id="pack_tr_variable" style="display:none">
        <td width="100px">
          <g:textField id="pack_variable_unit" name='packs[variable].unit' value=""/>
        </td>
        <td width="100px">
          <g:textField id="pack_variable_pack" name='packs[variable].packing' value=""/>
        </td>
      </tr>
      <g:each var="pack" in="${itemInstance.packs}" status="i">
        <tr id="pack_tr_${i}">
          <td width="100px">
            <g:textField name='packs[${i}].unit' value="${pack.unit}"/>
          </td>
          <td width="100px">
            <g:textField name='packs[${i}].packing' value="${pack.packing}"/>
          </td>
        </tr>
      </g:each>
      </tbody>
    </table>
  </div>
</div>

<script type="text/javascript">
    var childCount1 = ${itemInstance?.packs.size()} + 0;
  
    $(document).ready(function() {
        //$('#dt').dataTable();
    } );
    
    function fAddChild1() {

        var tbody     = $("#tbl_pack").find("tbody:eq(0)")
        var tr_first  = (tbody.find("tr:first")).clone()
        
        var tr = $( "<tr>" + (tr_first.html()).replace(/variable/g, childCount1)  + "</tr>" )

        tr.appendTo(tbody)
        
        childCount1++;
    }
    
    function fConcat(i){
        var value = $("#controller_"+i).val() + ":" + $("#action_"+i).val()
        //alert(value)
        $("#pack_"+i).val( value )
    }
</script>