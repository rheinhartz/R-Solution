<div class="actionnav-right">
    <a href="javascript:fAddChild1()"     class="linkbutton"><img src="${resource(dir: 'images/skin', file: 'database_add.png')}" />Add</a>
    <a href="javascript:fEditChild1()"    class="linkbutton"><img src="${resource(dir: 'images/skin', file: 'database_edit.png')}" />Edit</a>
    <a href="javascript:fDeleteChild1()"  class="linkbutton"><img src="${resource(dir: 'images/skin', file: 'database_delete.png')}" />Delete</a>
</div>

<div id="childList">
  <g:each var="role" in="${userInstance.roles}" status="i">
    <div id="role${i}">
      <g:textField name='roles[${i}].id' value='${role.id}'/>
      <g:textField name='roles[${i}].name' value='${role.name}'/>
    </div>
  </g:each>
</div>