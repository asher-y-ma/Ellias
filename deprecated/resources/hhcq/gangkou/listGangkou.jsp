<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2010-2-24
  Time: 0:28:17
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../../include/include.jsp"%>

<html>
  <head>
      <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
      <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
      <title>�ۿ�һ��</title>
  </head>
  <body>
    <c:set var="form" value="${gangkouForm}"/>
    <html:form action="/hhcq/gangkou.do?method=saveGangkou">
        ���֣�
        <html:text property="name"/><br/>
        ���ң�
        <html:select property="guojiaId">
            <html:option value="0">--</html:option>
            <c:forEach items="${form.guojias}" var="guojia">
                <html:option value="${guojia.id}">${guojia.pinyin}${guojia.name}</html:option>
            </c:forEach>
        </html:select>
        <html:submit value="����"/>
        <table>
            <tr>
                <th>���</th>
                <th>�ۿ�</th>
                <th>��������</th>
            </tr>
            <c:forEach items="${form.gangkous}" var="g">
                <tr>
                    <td>${g.id}</td>
                    <td>${g.name}</td>
                    <td>
                        ${hhcq:getGuojia(g.guojia).name}
                    </td>
                </tr>
            </c:forEach>
        </table>
  </html:form>
  <script type="text/javascript">
      document.getElementById('name').focus();
  </script>
  </body>
</html>