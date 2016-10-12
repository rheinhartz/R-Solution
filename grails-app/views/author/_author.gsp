<div class="dialog">
    <table>
        <tbody>
            <tr class="prop">
               <td valign="top" class="name"><label for="name">Name:</label></td>
               <td valign="top" class="value ${hasErrors(bean:authorInstance,field:'name','errors')}">
                   <input type="text" id="name" name="name" value="${fieldValue(bean:authorInstance,field:'name')}"/>
               </td>
            </tr>
            <tr class="prop">
               <td valign="top" class="name"><label for="name">Age:</label></td>
               <td valign="top" class="value ${hasErrors(bean:authorInstance,field:'name','errors')}">
                   <input type="text" id="age" name="age" value="${fieldValue(bean:authorInstance,field:'name')}"/>
               </td>
            </tr>
            <tr class="prop">
                <td valign="top" class="name"><label for="books">Books:</label></td>
                <td valign="top" class="value ${hasErrors(bean:authorInstance,field:'books','errors')}">
                    <g:render template="books" model="['authorInstance':authorInstance]" />
                </td>
            </tr>
        </tbody>
    </table>
</div>
