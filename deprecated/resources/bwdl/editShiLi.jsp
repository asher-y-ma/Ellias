<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2011-6-25
  Time: 21:32:12
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../include/include.jsp"%>
<html>
  <head>
      <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
      <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
      <title>�༭����</title>
  </head>
  <body>
    <c:set var="form" value="${shiliForm}"/>
    <html:form action="/bwdl/shili.do">
        <input type="hidden" name="method"/>
        <html:hidden property="version"/>
        <html:hidden property="shiliId"/>
        <table width="40%" id="table">
            <tr class="row1">
                <td class="fieldname">
                    ����
                </td>
                <td class="fieldvalue">
                    <html:text property="name"/>
                </td>
            </tr>
        </table>
        <input type="button" value="����" onclick="saveShiLi();" class="otterbtn"/>
  </html:form>
  <script type="text/javascript">
      function saveShiLi()
      {
          document.getElementsByName('method')[0].value = 'save';
          document.forms[0].submit();
      }
  </script>
  </body>
</html>