<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2011-6-19
  Time: 1:54:10
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
    <c:set var="form" value="${fangjuForm}"/>
    <html:form action="/bwdl/fangju.do">
        <input type="hidden" name="method"/>
        <html:hidden property="version"/>
        <input type="hidden" name="fangjuId"/>
        ���֣�
        ���
        <html:submit value="��ѯ"/>
        <table width="40%" id="table">
            <tr class="head">
                <th width="10%">����</th>
                <th>����</th>
                <th>����</th>
                <th>�۸�</th>
            </tr>
            <c:forEach items="${form.fangjus}" var="fangju">
                <tr>
                    <td>${fangju.name}</td>
                    <td>${fangju.fangyu}</td>
                    <td>${fangju.weight}</td>
                    <td>${fangju.price}</td>
                </tr>
            </c:forEach>
        </table>
        <input type="button" value="����" onclick="addFangJu();" class="otterbtn"/>
  </html:form>
  <script type="text/javascript">
      function addFangJu()
      {
          document.getElementsByName('method')[0].value = 'edit';
          document.forms[0].submit();
      }
  </script>
  </body>
</html>