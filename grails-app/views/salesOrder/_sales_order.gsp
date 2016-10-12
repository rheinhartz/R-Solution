<div class="actionnav-right">
    <a href="javascript:fSave()" class="linkbutton">
        <img src="${resource(dir: 'images/skin', file: 'database_save.png')}" />Save
    </a>
</div>
<div class="easyui-tabs tab_main">
    <div title="Parent">
      <div style="height:250px">
        <div id="flash"  class="message_flash">
            <g:if test="${flash.message}"><ul><li>${flash.message}</li></ul></g:if>
        </div>
        <div id="error" class="message_error">
            <g:renderErrors bean="${sales_orderInstance}" as="list" />
        </div>

        <input type="hidden" name="isdeleted">
        <input type="hidden" name="id" value="${sales_orderInstance?.id}" />

        <table>
            <tr>
                <td class="${hasErrors(bean:sales_orderInstance , field:'doc_no','td_error')}">Document No</td>
                <td>: <input type="text" name="doc_no" value="${sales_orderInstance?.doc_no}"></td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:sales_orderInstance , field:'location_id','td_error')}">Location</td>
                <td>: 
                  <g:select name='location_id' from="${listLocation}" class="easyui-combobox"
                            data-options="editable: false"
                            style="width:155px"
                            optionKey="idname" optionValue="fieldname" value="${sales_orderInstance?.location_id}"
                  />
                </td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:sales_orderInstance , field:'customer_id','td_error')}">Customer</td>
                <td>: 
                  <g:select name='customer_id' from="${listCustomer}" class="easyui-combobox"
                            data-options="editable: false"
                            style="width:155px"
                            optionKey="idname" optionValue="fieldname" value="${sales_orderInstance?.customer_id}"
                  />
                </td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:sales_orderInstance , field:'date','td_error')}">Date</td>
                <td>:
                    <input type="date" id="date" name="date" value="" onchange="fSetDate()" style="width:150px">
                    <input type="hidden" id="date_year" name="date_year">
                    <input type="hidden" id="date_month" name="date_month">
                    <input type="hidden" id="date_day" name="date_day">
                </td>
            </tr>
        </table>
      </div>
    </div>
</div>

<script>
    var d = new Date()
    var dt = "${sales_orderInstance?.date}"
    
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
      
    }

    function fSave(){
        fSetDate()
        
        document.forms[0].submit()
    }
</script>