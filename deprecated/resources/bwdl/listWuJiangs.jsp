<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2011-6-18
  Time: 0:01:41
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../include/include.jsp"%>

<html>
  <head>
      <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
      <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
      <title>�佫һ��</title>
  </head>
  <body>
    <c:set var="form" value="${wujiangForm}"/>
    <html:form action="/bwdl/wujiang.do">
        <input type="hidden" name="method"/>
        <html:hidden property="version"/>
        <input type="hidden" name="wujiangId"/>
        ���֣�
        ���
        <html:submit value="��ѯ"/>
        <table width="40%" id="table" cellpadding="0" cellspacing="1">
            <tr class="head">
                <th width="10%">����</th>
                <th>��</th>
                <th>��</th>
                <th>֪</th>
                <th>��</th>
                <th>��</th>
                <th>��</th>
                <th>����</th>
                <th>����</th>
                <th>����</th>
                <th>��</th>
                <th>�ǳ����</th>
                <th>����</th>
            </tr>
            <c:forEach items="${form.wujiangs}" var="wujiang" varStatus="i">
                <tr class="row${i.index % 2 + 1}">
                    <td>${wujiang.name}</td>
                    <td>${wujiang.ti}</td>
                    <td>${wujiang.wu}</td>
                    <td>${wujiang.zhi}</td>
                    <td>${wujiang.zhong}</td>
                    <td>${wujiang.de}</td>
                    <td>${wujiang.jing}</td>
                    <td>
                        ${bwdl:getBingzhong(wujiang.bingzhong)}
                    </td>
                    <td>
                        ${bwdl:getWuQi(wujiang.wuqi).name}
                    </td>
                    <td>
                    ${bwdl:getFangJu(wujiang.fangju).name}
                    </td>
                    <td>${wujiang.shibing}</td>
                    <td>${wujiang.chuchang}</td>
                    <td>
                        <input type="button" class="otterbtn" onclick="edit(${wujiang.id});" value="�༭"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <input type="button" value="����" onclick="addWuJiang();" class="otterbtn"/>
  </html:form>
  <script type="text/javascript">
      function addWuJiang()
      {
          document.getElementsByName('method')[0].value = 'edit';
          document.forms[0].submit();
      }

      function edit(id)
      {
          document.getElementsByName('wujiangId')[0].value = id;
          document.getElementsByName('method')[0].value = 'edit';
          document.forms[0].submit();
      }
  </script>
  </body>
</html>