<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2011-6-22
  Time: 11:37:55
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../include/include.jsp"%>

<html>
  <head>
      <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
      <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
      <title>�༭�佫</title>
  </head>
  <body>
    <c:set var="form" value="${wujiangForm}"/>
    <html:form action="/bwdl/wujiang.do">
        <input type="hidden" name="method"/>
        <html:hidden property="version"/>
        <html:hidden property="wujiangId"/>
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
                <td class="fieldname">��</th>
                <td class="fieldvalue">
                    <html:text property="ti"/>
                </td>
            </tr>
            <tr class="row1">
                <td class="fieldname">��</th>
                <td class="fieldvalue">
                    <html:text property="wu"/>
                </td>
            </tr>
            <tr class="row2">
                <td class="fieldname">֪</th>
                <td class="fieldvalue">
                    <html:text property="zhi"/>
                </td>
            </tr>
            <tr class="row1">
                <td class="fieldname">��</th>
                <td class="fieldvalue">
                    <html:text property="zhong"/>
                </td>
            </tr>
            <tr class="row2">
                <td class="fieldname">��</th>
                <td class="fieldvalue">
                    <html:text property="de"/>
                </td>
            </tr>
            <tr class="row1">
                <td class="fieldname">��</th>
                <td class="fieldvalue">
                    <html:text property="jing"/>
                </td>
            </tr>
            <tr class="row2">
                <td class="fieldname">����</th>
                <td class="fieldvalue">
                    <html:select property="bingzhong">
                        <html:option value="0">--��ѡ��--</html:option>
                        <c:forEach items="${bwdl:getAllBingZhongs()}" var="b" varStatus="i">
                            <c:if test="${i.index > 0}">
                                <html:option value="${i.index}">${b}</html:option>
                            </c:if>
                        </c:forEach>
                    </html:select>
                </td>
            </tr>
            <tr class="row1">
                <td class="fieldname">����</th>
                <td class="fieldvalue">
                    <html:select property="wuqi">
                        <html:option value="0">--��ѡ��--</html:option>
                        <c:forEach items="${bwdl:getAllWuQis(form.version)}" var="w">
                            <html:option value="${w.id}">${w.name}</html:option>
                        </c:forEach>
                    </html:select>
                </td>
            </tr>
            <tr class="row2">
                <td class="fieldname">����</th>
                <td class="fieldvalue">
                    <html:select property="fangju">
                        <html:option value="0">--��ѡ��--</html:option>
                        <c:forEach items="${bwdl:getAllFangJus(form.version)}" var="f">
                            <html:option value="${f.id}">${f.name}</html:option>
                        </c:forEach>
                    </html:select>
                </td>
            </tr>
            <tr class="row1">
                <td class="fieldname">ʿ��</th>
                <td class="fieldvalue">
                    <html:text property="shibing"/>
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