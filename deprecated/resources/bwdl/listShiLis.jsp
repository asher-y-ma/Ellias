<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2011-6-25
  Time: 20:42:11
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../include/include.jsp"%>
<html>
  <head>
      <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
      <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
      <title>����һ��</title>
  </head>
  <body>
    <c:set var="form" value="${shiliForm}"/>
    <html:form action="/bwdl/shili.do">
        <input type="hidden" name="method"/>
        <html:hidden property="version"/>
        <input type="hidden" name="shiliId"/>
        ���֣�
        ���
        <html:submit value="��ѯ"/>
        <table width="40%" id="table">
            <tr class="head">
                <th>ID</th>
                <th width="10%">����</th>
            </tr>
            <c:forEach items="${form.shilis}" var="shili">
                <tr>
                    <td>${shili.id}</td>
                    <td>${shili.name}</td>
                </tr>
            </c:forEach>
        </table>
        <input type="button" value="����" onclick="addShiLi();" class="otterbtn"/>
  </html:form>
  <script type="text/javascript">
      function addShiLi()
      {
          document.getElementsByName('method')[0].value = 'edit';
          document.forms[0].submit();
      }
  </script>
  </body>
</html>