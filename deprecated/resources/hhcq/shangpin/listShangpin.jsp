<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2010-2-24
  Time: 21:06:18
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../../include/include.jsp"%>

<html>
  <head>
      <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
      <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
      <title>��Ʒһ��</title>
  </head>
  <body>
    <c:set var="form" value="${shangpinForm}"/>
    <html:form action="/hhcq/shangpin.do?method=saveShangpin">
        ���֣�
        <html:text property="name"/><br/>
        ���
        <html:select property="leibieId">
            <html:option value="0">--</html:option>
            <c:forEach items="${form.leibies}" var="leibie">
                <html:option value="${leibie.id}">${leibie.pinyin}${leibie.name}</html:option>
            </c:forEach>
        </html:select>
        <html:submit value="����"/>
        <table>
            <tr>
                <th>���</th>
                <th>��Ʒ</th>
                <th>�������</th>
            </tr>
            <c:forEach items="${form.shangpins}" var="s">
                <tr>
                    <td>${s.id}</td>
                    <td>${s.name}</td>
                    <td>
                        ${hhcq:getLeibie(s.leibie).name}
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