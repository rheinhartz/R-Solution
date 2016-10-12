<%@ page import="com.r.domain.Author" %>
<%@ page import="groovy.sql.Sql" %>
<%@ page import="java.awt.print.Book" %>

<%
    def list = new ArrayList()
    def datasource = grailsApplication.mainContext.getBean('dataSource')
    def conn = Sql.newInstance(datasource)
   // def sql = """
   //             SELECT P.name,C1.title FROM author_ P
   //                 LEFT JOIN author_book_ C1 ON P.id = C1.author_id
   //           """
    def sql = """
                SELECT a.dname FROM scott.dept a
              """
              
    
    conn.eachRow(sql){
        //list.add "$it.name"
        list.add "$it.dname"
    }
    
%>
<g:each in="${list}" status="x" var="lt">
    <br>${lt}
</g:each>

<g:each in="${authorInstanceList}" status="i" var="authorInstance">
    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td><g:link action="show" id="${authorInstance.id}">${fieldValue(bean:authorInstance, field:'id')}</g:link></td>
        <td>${fieldValue(bean:authorInstance, field:'name')}</td>
        <td>${fieldValue(bean:authorInstance, field:'books')}</td>
    </tr>
</g:each>