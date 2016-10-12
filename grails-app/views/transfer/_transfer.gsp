<div class="actionnav-right">
    <a href="javascript:fSave()" class="linkbutton">
        <img src="${resource(dir: 'images/skin', file: 'database_save.png')}" />Save
    </a>
</div>
<div class="easyui-tabs tab_main">
    <div title="Parent">
      <div style="height:250px">

        <input type="hidden" name="isdeleted">
        <input type="hidden" name="id" value="${transferInstance?.id}" />

        <table>
            <tr>
                <td class="${hasErrors(bean:transferInstance , field:'doc_no','td_error')}">Document No</td>
                <td>: <input type="text" name="doc_no" value="${transferInstance?.doc_no}"></td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:transferInstance , field:'location_id_fr','td_error')}">Location From</td>
                <td>: 
                  <g:select name='location_id_fr' from="${listLocation}" class="easyui-combobox"
                            style="width:155px"
                            optionKey="idname" optionValue="fieldname" value="${transferInstance?.location_id_fr}"
                            data-options="editable: false
                                         ,onChange: fChangeLocationFR
                                         "
                  />
                </td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:transferInstance , field:'location_id_to','td_error')}">Location To</td>
                <td>: 
                  <g:select name='location_id_to' from="${listLocation}" class="easyui-combobox"
                            style="width:155px"
                            optionKey="idname" optionValue="fieldname" value="${transferInstance?.location_id_to}"
                            data-options="editable: false
                                         ,onChange: fChangeLocationTO
                                         "
                  />
                </td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:transferInstance , field:'date','td_error')}">Date</td>
                <td>:
                    <input type="date" id="date" name="date" value="" onchange="fSetDate()" style="width:150px">
                    <input type="hidden" id="date_year" name="date_year">
                    <input type="hidden" id="date_month" name="date_month">
                    <input type="hidden" id="date_day" name="date_day">
                    <input type="hidden" id="date_hour" name="date_hour">
                    <input type="hidden" id="date_minute" name="date_minute">
                </td>
            </tr>
        </table>
        
      </div>
    </div>
</div>

<g:if test="${flash.message}">
<div id="dlg" class="easyui-dialog" 
     title=" Notice" 
     data-options="closed:false,modal:true,iconCls:'icon-no'"
     style="width:800px;height:400px;padding:10px">
     <div id="flash"  class="message_flash">
       <ul><li>${flash.message}</li></ul>
     </div>
</div>
</g:if>

<g:if test="${hasErrors(bean:transferInstance , field:'*','false')}">
<div id="dlg2" class="easyui-dialog" 
     title=" Notice" 
     data-options="closed:false,modal:true,iconCls:'icon-no'"
     style="width:800px;height:400px;padding:10px">
     <div id="error" class="message_error">
        <g:renderErrors bean="${transferInstance}" as="list" />
     </div>
</div>
</g:if>

<div id="dlg3" class="easyui-dialog" 
     title=" Notice" 
     data-options="closed:true,modal:true,iconCls:'icon-no'"
     style="width:800px;height:400px;padding:10px">
     <div class="message_flash">
       <ul><li id="dlg3li"></li></ul>
     </div>
</div>


<script>
    var d = new Date()
    var dt = "${transferInstance?.date}"
        
    if( $("#date").val() == "" ){
      var yr = d.getFullYear()
      var mt = ((d.getMonth()+1) < 10) ? ("0" + (d.getMonth()+1)) : (d.getMonth()+1)
      var dy = ((d.getDate()) < 10) ? ("0" + d.getDate()) : d.getDate()
      $("#date").val( yr + "-" + mt + "-" + dy )
    }else{
      $("#date").val( dt.substring(0,10) )
    }
    
    function fSetDate(){
      
      var year  = ($("#date").val()).substring(0,4)
      var month = ($("#date").val()).substring(5,7)
      var day   = ($("#date").val()).substring(8,10)
      
      $("#date_year").val(year)
      $("#date_month").val(month)
      $("#date_day").val(day)
      $("#date_hour").val(d.getHours())
      $("#date_minute").val(d.getMinutes())
      
    }

    function fSave(){
        fSetDate()
        
        document.forms[0].submit()
    }
</script>