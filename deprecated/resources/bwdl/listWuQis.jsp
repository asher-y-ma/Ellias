<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2011-6-18
  Time: 23:21:37
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
    <c:set var="form" value="${wuqiForm}"/>
    <html:form action="/bwdl/wuqi.do">
        <input type="hidden" name="method"/>
        <html:hidden property="version"/>
        <input type="hidden" name="wuqiId"/>
        ���֣�
        ���
        <html:submit value="��ѯ"/>
        <table width="40%" id="table">
            <tr class="head">
                <th width="10%">����</th>
                <th>���</th>
                <th>����</th>
                <th>����</th>
                <th>�۸�</th>
            </tr>
            <c:forEach items="${form.wuqis}" var="wuqi">
                <tr>
                    <td>${wuqi.name}</td>
                    <td>
                        ${bwdl:getWuQiType(wuqi.type)}
                    </td>
                    <td>${wuqi.gongji}</td>
                    <td>${wuqi.weight}</td>
                    <td>${wuqi.price}</td>
                </tr>
            </c:forEach>
        </table>
        <input type="button" value="����" onclick="addWuQi();" class="otterbtn"/>
  </html:form>
  <script type="text/javascript">
      function addWuQi()
      {
          document.getElementsByName('method')[0].value = 'edit';
          document.forms[0].submit();
      }
  </script>
  </body>
</html>