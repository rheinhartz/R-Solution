<%--
<input type='text' id='books[0].title' name='books[0].title' value="book1"/>
--%>

<script type="text/javascript">
    var childCount = ${authorInstance?.books.size()} + 0;
    
    function addChild() {

        var htmlId = "books" + childCount;
        var deleteIcon = "${resource(dir:'images/skin', file:'database_delete.png')}";
        var templateHtml = "<div id='" + htmlId + "' name='" + htmlId + "'>\n";
        //templateHtml += "<input type='text' id='expandableBookList[" + childCount + "].title' name='expandableBookList[" + childCount + "].title' />\n";
        templateHtml += "<input type='text' id='books[" + childCount + "].title' name='books[" + childCount + "].title' />\n";
        templateHtml += "<span onClick='$(\"#" + htmlId + "\").remove();'><img src='" + deleteIcon + "' /></span>\n";
        templateHtml += "</div>\n";
        $("#childList").append(templateHtml);
        childCount++;

    }
</script>

<div id="childList">
    <g:each var="book" in="${authorInstance.books}" status="i">
        <%--<g:render template='book' model="['book':book,'i':i]"/>--%>
        <div id="book${i}">
            <g:hiddenField name='books[${i}].id' value='${book.id}'/>
            <g:textField name='books[${i}].title' value='${book.title}'/>
            <input type="hidden" name='books[${i}].isdeleted' id='books[${i}].isdeleted' value='false'/>
            <span onClick="$('#books\\[${i}\\]\\.isdeleted').val('true'); $('#book${i}').hide()"><img src="${resource(dir:'images/skin', file:'database_delete.png')}" /></span>
        </div>
    </g:each>
</div>
<input type="button" value="Add Book" onclick="addChild();" />
