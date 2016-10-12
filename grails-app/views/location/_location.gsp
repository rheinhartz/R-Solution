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
            <g:renderErrors bean="${locationInstance}" as="list" />
        </div>

        <input type="hidden" name="isdeleted">
        <input type="hidden" name="id" value="${locationInstance?.id}" />

        <table>
            <tr>
                <td class="${hasErrors(bean:locationInstance , field:'name','td_error')}">Name</td>
                <td>: <input type="text" name="name" value="${locationInstance?.name}"></td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:locationInstance , field:'kind','td_error')}">Kind</td>
                <td>: <input type="text" name="kind" value="${locationInstance?.kind}"></td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:locationInstance , field:'type','td_error')}">Type</td>
                <td>: <input type="text" name="type" value="${locationInstance?.type}"></td>
            </tr>
            <tr>
                <td class="${hasErrors(bean:locationInstance , field:'code','td_error')}">Code</td>
                <td>: <input type="text" name="code" value="${locationInstance?.code}"></td>
            </tr>
        </table>
      </div>
    </div>
</div>

<script>
    function fSave(){
        document.forms[0].submit()
    }
</script>