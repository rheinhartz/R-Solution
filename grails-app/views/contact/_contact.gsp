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
            <g:renderErrors bean="${contactInstance}" as="list" />
        </div>

        <input type="hidden" name="isdeleted">
        <input type="hidden" name="id" value="${contactInstance?.id}" />

        <table>
            <tr>
                <td class="${hasErrors(bean:contactInstance , field:'name','td_error')}">Name</td>
                <td>: <input type="text" name="name" value="${contactInstance?.name}"></td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:contactInstance , field:'kind','td_error')}">Kind</td>
                <td>: <input type="text" name="kind" value="${contactInstance?.kind}"></td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:contactInstance , field:'type','td_error')}">Type</td>
                <td>: <input type="text" name="type" value="${contactInstance?.type}"></td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:contactInstance , field:'iscustomer','td_error')}">Customer</td>
                <td>: <g:checkBox name='iscustomer' value="${contactInstance?.iscustomer}"/></td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:contactInstance , field:'issupplier','td_error')}">Supplier</td>
                <td>: <g:checkBox name='issupplier' value="${contactInstance?.issupplier}"/></td>
            </tr>
        </table>
      </div>
    </div>
</div>

<script>
    function fSave(){
        with( document.forms[0] ){
          /*
          _issupplier.value = 0
          _iscustomer.value = 0
          
          if( iscustomer.checked ){
            _iscustomer.value = 1
          }
          if( issupplier.checked ){
            _issupplier.value = 1
          }
          */
          
          //return
          submit()
        }
    }
</script>