<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2011-6-19
  Time: 1:55:06
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
    <c:set var="form" value="${fangjuForm}"/>
    <html:form action="/bwdl/fangju.do">
        <input type="hidden" name="method"/>
        <html:hidden property="version"/>
        <html:hidden property="fangjuId"/>
        <table width="40%" id="table">
            <tr class="row1">
                <td class="fieldname">
                    ����
                </td>
                <td class="fieldvalue">
                    <html:text property="name"/>
                </td>
            </tr>
            <tr class="row2">
                <td class="fieldname">����</th>
                <td class="fieldvalue">
                    <html:text property="fangyu"/>
                </td>
            </tr>
            <tr class="row1">
                <td class="fieldname">����</th>
                <td class="fieldvalue">
                    <html:text property="weight"/>
                </td>
            </tr>
            <tr class="row2">
                <td class="fieldname">�۸�</th>
                <td class="fieldvalue">
                    <html:text property="price"/>
                </td>
            </tr>
        </table>
        <input type="button" value="����" onclick="saveFangJu();" class="otterbtn"/>
  </html:form>
  <script type="text/javascript">
      function saveFangJu()
      {
          document.getElementsByName('method')[0].value = 'save';
          document.forms[0].submit();
      }
  </script>
  </body>
</html>