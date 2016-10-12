<div id="book${i}">
    <g:hiddenField name='books[${i}].id' value='${book.id}'/>
    <g:textField name='books[${i}].title' value='${book.title}'/>
    <input type="text" name='books[${i}]._deleted' id='books[${i}]._deleted' value='false'/>
    <%--<span onClick="$('#bookList\\[${i}\\]\\._deleted').val('true'); $('#book${i}').hide()"><img src="${resource(dir:'images/skin', file:'database_delete.png')}" /></span>--%>
</div>
<%--
<div id="book${i}">
    <g:hiddenField name='expandableBookList[${i}].id' value='${book.id}'/>
    <g:textField name='expandableBookList[${i}].title' value='${book.title}'/>
    <input type="hidden" name='expandableBookList[${i}]._deleted' id='expandableBookList[${i}]._deleted' value='false'/>
    <span onClick="$('#expandableBookList\\[${i}\\]\\._deleted').val('true'); $('#book${i}').hide()"><img src="${resource(dir:'images/skin', file:'database_delete.png')}" /></span>
</div>
--%>