<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2011-6-19
  Time: 19:58:57
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
    <c:set var="form" value="${chengshiForm}"/>
    <html:form action="/bwdl/chengshi.do">
        <input type="hidden" name="method"/>
        <html:hidden property="version"/>
        <html:hidden property="chengshiId"/>
        <c:set var="allWuJiangs" value="${bwdl:getAllWuJiangsInit(form.version)}"/>
        <table width="40%" id="table">
            <tr class="row1">
                <td class="fieldname">
                    ����
                </td>
                <td class="fieldvalue">
                    <html:text property="name"/>
                </td>
                <td class="fieldname">
                    �佫1
                </td>
                <td class="fieldvalue">
                    <select name="wujiangIds">
                        <option value="0">--��--</option>
                        <c:forEach items="${allWuJiangs}" var="w">
                            <option value="${w.id}" <c:if test="${form.wujiangIds[0] == w.id}">selected</c:if>>${w.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr class="row2">
                <td class="fieldname">��</th>
                <td class="fieldvalue">
                    <html:text property="jin"/>
                </td>
                <td class="fieldname">
                    �佫2
                </td>
                <td class="fieldvalue">
                    <select name="wujiangIds">
                        <option value="0">--��--</option>
                        <c:forEach items="${allWuJiangs}" var="w">
                            <option value="${w.id}" <c:if test="${form.wujiangIds[1] == w.id}">selected</c:if>>${w.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr class="row1">
                <td class="fieldname">��</th>
                <td class="fieldvalue">
                    <html:text property="mi"/>
                </td>
                <td class="fieldname">
                    �佫3
                </td>
                <td class="fieldvalue">
                    <select name="wujiangIds">
                        <option value="0">--��--</option>
                        <c:forEach items="${allWuJiangs}" var="w">
                            <option value="${w.id}" <c:if test="${form.wujiangIds[2] == w.id}">selected</c:if>>${w.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr class="row2">
                <td class="fieldname">��</th>
                <td class="fieldvalue">
                    <html:text property="bao"/>
                </td>
                <td class="fieldname">
                    �佫4
                </td>
                <td class="fieldvalue">
                    <select name="wujiangIds">
                        <option value="0">--��--</option>
                        <c:forEach items="${allWuJiangs}" var="w">
                            <option value="${w.id}" <c:if test="${form.wujiangIds[3] == w.id}">selected</c:if>>${w.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr class="row1">
                <td class="fieldname">ͳ�ζ�</th>
                <td class="fieldvalue">
                    <html:text property="tongzhi"/>
                </td>
                <td class="fieldname">
                    �佫5
                </td>
                <td class="fieldvalue">
                    <select name="wujiangIds">
                        <option value="0">--��--</option>
                        <c:forEach items="${allWuJiangs}" var="w">
                            <option value="${w.id}" <c:if test="${form.wujiangIds[4] == w.id}">selected</c:if>>${w.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr class="row2">
                <td class="fieldname">����</th>
                <td class="fieldvalue">
                    <html:text property="tudi"/>
                </td>
                <td class="fieldname">
                    �佫6
                </td>
                <td class="fieldvalue">
                    <select name="wujiangIds">
                        <option value="0">--��--</option>
                        <c:forEach items="${allWuJiangs}" var="w">
                            <option value="${w.id}" <c:if test="${form.wujiangIds[5] == w.id}">selected</c:if>>${w.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr class="row1">
                <td class="fieldname">��ҵ</th>
                <td class="fieldvalue">
                    <html:text property="chanye"/>
                </td>
                <td class="fieldname">
                    �佫7
                </td>
                <td class="fieldvalue">
                    <select name="wujiangIds">
                        <option value="0">--��--</option>
                        <c:forEach items="${allWuJiangs}" var="w">
                            <option value="${w.id}" <c:if test="${form.wujiangIds[6] == w.id}">selected</c:if>>${w.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr class="row2">
                <td class="fieldname">�˿�</th>
                <td class="fieldvalue">
                    <html:text property="renkou"/>
                </td>
                <td class="fieldname">
                    �佫8
                </td>
                <td class="fieldvalue">
                    <select name="wujiangIds">
                        <option value="0">--��--</option>
                        <c:forEach items="${allWuJiangs}" var="w">
                            <option value="${w.id}" <c:if test="${form.wujiangIds[7] == w.id}">selected</c:if>>${w.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr class="row1">
                <td class="fieldname">����</th>
                <td class="fieldvalue">
                    <html:text property="fangzai"/>
                </td>
                <td class="fieldname">
                    �佫9
                </td>
                <td class="fieldvalue">
                    <select name="wujiangIds">
                        <option value="0">--��--</option>
                        <c:forEach items="${allWuJiangs}" var="w">
                            <option value="${w.id}" <c:if test="${form.wujiangIds[8] == w.id}">selected</c:if>>${w.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="fieldname">����</td>
                <td class="fieldvalue">
                    <c:set var="shilis" value="${bwdl:getAllShiLisInit(form.version)}"/>
                    <html:select property="shili">
                        <html:option value="0">--��--</html:option>
                        <c:forEach items="${shilis}" var="s">
                            <html:option value="${s.id}">${s.name}</html:option>
                        </c:forEach>
                    </html:select>
                </td>
                <td class="fieldname">
                    �佫10
                </td>
                <td class="fieldvalue">
                    <select name="wujiangIds">
                        <option value="0">--��--</option>
                        <c:forEach items="${allWuJiangs}" var="w">
                            <option value="${w.id}" <c:if test="${form.wujiangIds[9] == w.id}">selected</c:if>>${w.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="fieldname">X����</td>
                <td class="fieldvalue">
                    <html:text property="locationX"/>
                </td>
                <td class="fieldname">&nbsp;</td>
                <td class="fieldvalue">&nbsp;</td>
            </tr>
            <tr>
                <td class="fieldname">Y����</td>
                <td class="fieldvalue">
                    <html:text property="locationY"/>
                </td>
                <td class="fieldname">&nbsp;</td>
                <td class="fieldvalue">&nbsp;</td>
            </tr>
        </table>
        <input type="button" value="����" onclick="saveChengShi();" class="otterbtn"/>
  </html:form>
  <script type="text/javascript">
      function saveChengShi()
      {
          document.getElementsByName('method')[0].value = 'save';
          document.forms[0].submit();
      }
  </script>
  </body>
</html>