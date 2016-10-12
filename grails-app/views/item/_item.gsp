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
            <g:renderErrors bean="${itemInstance}" as="list" />
        </div>

        <input type="hidden" name="isdeleted">
        <input type="hidden" name="id" value="${itemInstance?.id}" />
        <input type="hidden" name="stock" value="${itemInstance?.stock}" />
        
        <table>
            <tr>
                <td class="${hasErrors(bean:itemInstance , field:'name','td_error')}">Name</td>
                <td>: <input type="text" name="name" value="${itemInstance?.name}"></td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:itemInstance , field:'kind','td_error')}">Kind</td>
                <td>: <input type="text" name="kind" value="${itemInstance?.kind}"></td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:itemInstance , field:'type','td_error')}">Type</td>
                <td>: <input type="text" name="type" value="${itemInstance?.type}"></td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:itemInstance , field:'brand','td_error')}">Brand</td>
                <td>: <input type="text" name="brand" value="${itemInstance?.brand}"></td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:itemInstance , field:'code','td_error')}">Code</td>
                <td>: <input type="text" name="code" value="${itemInstance?.code}"></td>
            </tr>
        </table>
      </div>
    </div>
</div>

<script>
    function fSave(){
    
        $("#pack_variable_unit").prop("name","var1")
        $("#pack_variable_pack").prop("name","var2")
        
        document.forms[0].submit()
    }
</script>