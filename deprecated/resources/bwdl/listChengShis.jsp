<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2011-6-19
  Time: 20:01:52
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
    <c:set var="form" value="${chengshiForm}"/>
    <html:form action="/bwdl/chengshi.do">
        <input type="hidden" name="method"/>
        <html:hidden property="version"/>
        <input type="hidden" name="chengshiId"/>
        ���֣�
        ���
        <html:submit value="��ѯ"/>
        <table width="40%" id="table">
            <tr class="head">
                <th>id</th>
                <th width="10%">����</th>
                <th>��</th>
                <th>��</th>
                <th>��</th>
                <th>ͳ�ζ�</th>
                <th>����</th>
                <th>��ҵ</th>
                <th>�˿�</th>
                <th>����</th>
                <th>����</th>
                <th>X����</th>
                <th>Y����</th>
                <th>����</th>
            </tr>
            <c:forEach items="${form.chengshis}" var="chengshi">
                <tr>
                    <td align="right">${chengshi.id}</td>
                    <td>${chengshi.name}</td>
                    <td align="right">${chengshi.jin}</td>
                    <td align="right">${chengshi.mi}</td>
                    <td align="right">${chengshi.bao}</td>
                    <td align="right">${chengshi.tongzhi}</td>
                    <td align="right">${chengshi.tudi}</td>
                    <td align="right">${chengshi.chanye}</td>
                    <td align="right">${chengshi.renkou}</td>
                    <td align="right">${chengshi.fangzai}</td>
                    <td>
                        ${bwdl:getShiLi(chengshi.shili).name}
                    </td>
                    <td align="right">
                        ${chengshi.locationX}
                    </td>
                    <td align="right">
                        ${chengshi.locationY}
                    </td>
                    <td>
                        <input type="button" class="otterbtn" value="�޸�" onclick="edit(${chengshi.id})"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <input type="button" value="����" onclick="addChengShi();" class="otterbtn"/>
  </html:form>
  <script type="text/javascript">
      function edit(id)
      {
          document.getElementsByName('chengshiId')[0].value = id;
          document.getElementsByName('method')[0].value = 'edit';
          document.forms[0].submit();
      }
      function addChengShi()
      {
          document.getElementsByName('method')[0].value = 'edit';
          document.forms[0].submit();
      }
  </script>
  </body>
</html>