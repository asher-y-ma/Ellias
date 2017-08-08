<%@ page import="com.saille.pampers.PampersService" %>
<%@ page import="servlet.GlobalContext" %>
<%@ page import="com.saille.pampers.RobPampers" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.saille.pampers.DetectPampers" %>
<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2013-2-10
  Time: 1:17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../include/include.jsp"%>
<html>
    <head>
        <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
        <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
        <script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'> </script>
        <script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'> </script>
        <script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/PampersDwr.js'></script>
    </head>
    <%
        PampersService service = (PampersService) GlobalContext.getSpringContext().getBean("pampersService", PampersService.class);
        List<RobPampers[]> listPre = service.getIds();
        List<RobPampers> list = new ArrayList<RobPampers>();
        for(RobPampers[] s : listPre) {
            for(RobPampers r : s) {
                list.add(r);
            }
//            list.add(s[0]);
//            list.add(s[1]);
        }
    %>
    <c:set var="ids" value="<%=list%>"/>
    <body>
    <input type="button" onclick="init();" value="��ʼ��"/>
        <table id="info" width="100%" border="0" cellpadding="1" cellspacing="1" class="rowover">
            <tr class="head">
                <th>��¼��</th>
                <th>����</th>
                <th>����</th>
                <th>email</th>
                <th>�ֻ�</th>
                <th>ʡ��</th>
                <th>����</th>
                <th>��ַ</th>
                <th>�ʱ�</th>
                <th>���Ķ���</th>
                <th>��ǰ����</th>
                <th>�Ƿ�<br/>����</th>
                <th>�������ʱ��</th>
                <th>ѭ������</th>
                <th>�ص�¼����</th>
                <th>��ǰ״̬</th>
                <th>����</th>
            </tr>
            <c:forEach items="${ids}" var="id" varStatus="i">
                <tr class="row${i.index + 1}">
                    <td>${id.user}</td>
                    <td>${id.pwd}</td>
                    <td>${id.realname}</td>
                    <td>${id.email}</td>
                    <td>${id.mobile}</td>
                    <td>${pampers:getProvinceById(id.province)}</td>
                    <td>${pampers:getCityById(id.city)}</td>
                    <td>${id.address}</td>
                    <td>${id.zipcode}</td>
                    <td>${pampers:getItemName(id.itemId)}</td>
                    <td>
                        ${id.score}<br/><fmt:formatDate value="${id.scoreTime}" pattern="HH:mm:ss"/>
                    </td>
                    <td>${id.isRunning}<br/>${id.exitReason}</td>
                    <td><fmt:formatDate value="${id.lastOperateTime}" pattern="HH:mm:ss"/></td>
                    <td>${id.count}</td>
                    <td>${id.relogintimes}</td>
                    <th>${id.currentStatus}</th>
                    <td>
                        <input type="button" onclick="doStart('${id.user}');" value="��ʼ"/>
                        <input type="button" onclick="doStop('${id.user}');" value="ֹͣ"/>
                        <input type="button" onclick="deleteId('${id.user}');" value="ɾ��"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    <br/>
    ����ʺţ�
    <table>
        <tr>
            <td class="fieldname">��¼�ʺ�</td>
            <td class="fieldvalue">
                <input type="text" id="user"/>
            </td>
        </tr>
        <tr>
            <td class="fieldname">����</td>
            <td class="fieldvalue">
                <input type="text" id="pwd"/>
            </td>
        </tr>
        <tr>
            <td class="fieldname">����</td>
            <td class="fieldvalue">
                <input type="text" id="name"/>
            </td>
        </tr>
        <tr>
            <td class="fieldname">Email</td>
            <td class="fieldvalue">
                <input type="text" id="email"/>
            </td>
        </tr>
        <tr>
            <td class="fieldname">�ֻ�</td>
            <td class="fieldvalue">
                <input type="text" id="mobile"/>
            </td>
        </tr>
        <tr>
            <td class="fieldname">ʡ��</td>
            <td class="fieldvalue">
                <select id="province" onchange="updateCity();">
                    <option value="-1">--��ѡ��--</option>
                    <c:forEach items="${pampers:getProvinces()}" var="p">
                        <option value="${p[0]}">${p[1]}</option>
                    </c:forEach>
                </select>
                <!--<input type="text" id="province"/>-->
            </td>
        </tr>
        <tr>
            <td class="fieldname">����</td>
            <td class="fieldvalue">
                <select id="city">
                    <option value="-1">--��ѡ��--</option>
                </select>
                <!--<input type="text" id="city"/>-->
            </td>
        </tr>
        <tr>
            <td class="fieldname">��ַ</td>
            <td class="fieldvalue">
                <input type="text" id="address"/>
            </td>
        </tr>
        <tr>
            <td class="fieldname">�ʱ�</td>
            <td class="fieldvalue">
                <input type="text" id="zipcode"/>
            </td>
        </tr>
        <tr>
            <%
                DetectPampers detectservice = (DetectPampers) GlobalContext.getSpringContext().getBean("pampersDetect", DetectPampers.class);
                List<Map<String, Object>> items = detectservice.currentItems;
            %>
            <td class="fieldname">������Ʒ</td>
            <td class="fieldvalue">
                <select id="itemId">
                    <option value="-1">--��ѡ��--</option>
                    <c:forEach items="<%=items%>" var="p">
                        <option value="${p['ITEMID']}">${p['NAME']}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>
    <input type="button" onclick="doAdd()" value="���"/>
    </body>
<script type="text/javascript">
    function doAdd()
    {
        var user = document.getElementById('user').value;
        var pwd = document.getElementById('pwd').value;
        var name = document.getElementById('name').value;
        var email = document.getElementById('email').value;
        var mobile = document.getElementById('mobile').value;
        var province = document.getElementById('province').value;
        var city = document.getElementById('city').value;
        var address = document.getElementById('address').value;
        var zipcode = document.getElementById('zipcode').value;
        var itemid = document.getElementById('itemId').value;
        PampersDwr.addId(user, pwd, name, email, mobile, province, city, address, zipcode, itemid, afterDoAdd);
    }

    function doStart(id) {
        PampersDwr.startRun(id, afterDoAdd);
    }

    function doStop(id) {
        PampersDwr.stopRun(id, afterDoAdd);
    }

    function deleteId(id) {
        PampersDwr.deleteId(id, afterDoAdd);
    }

    function afterDoAdd(obj) {
        alert(obj);
    }

    function init() {
        PampersDwr.init(afterDoAdd);
    }

    function updateCity() {
        var p = document.getElementById('province').value;
        PampersDwr.getCities(p, afterUpdateCity);
        var c = document.getElementById('city');
        for(var i = c.options.length - 1; i > 0; i--) {
            c.options[i] = null;
        }
    }

    function afterUpdateCity(list) {
        var c = document.getElementById('city');
        for(var i = c.options.length - 1; i > 0; i--) {
            c.options[i] = null;
        }
        for(var i = 0; i < list.length; i++) {
            c.options[i+1] = new Option();
            c.options[i+1].value = list[i][0];
            c.options[i+1].text  = list[i][1];
        }
    }
</script>
</html>