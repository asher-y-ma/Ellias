<%@ page import="com.saille.rxqq.RxqqInstance" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.saille.rxqq.RxqqUtils" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2011-9-19
  Time: 23:43:43
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../include/include.jsp"%>
<html>
    <head>
        <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
        <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
        <script type='text/javascript' src='../include/json.js'> </script>
        <script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'> </script>
        <script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'> </script>
        <script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/RxqqDwr.js'></script>
    </head>
    <%
        Map<String, Object[]> maps = RxqqInstance.id;
    %>
    <body>
        <table>
            <tr>
                <td>
                    <input type="button" value="��ʼ��С��" onclick="init(1);"/>
                    <input type="button" value="��ʼ�����" onclick="init(2);"/>
                    �û�����
                    <input type="text" name="newusername"/>
                    ���룺
                    <input type="password" name="newpassword"/>
                    <input type="button" class="otterbtn" value="����" onclick="adduser();"/>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td rowspan="3">
                    Ⱥ������
                </td>
                <td>
                    <input type="button" class="otterbtn" value="������" onclick="doJJC('');"/>
                    <input type="button" class="otterbtn" value="��C��(С��)" onclick="guaCPlayer('', 2);"/>
                    <input type="button" class="otterbtn" value="��C��(���)" onclick="guaCPlayer('', 1);"/>
                    <input type="button" class="otterbtn" value="������(ȫ��)" onclick="doChangGuiAll('');"/>
                    <input type="button" class="otterbtn" value="�鿴����" onclick="beibao('');"/>
                    <input type="button" class="otterbtn" value="�鿴����" onclick="paimai('');"/>
                    <input type="button" class="otterbtn" value="��������(С��)" onclick="beibaozhengli('');"/>
                    <input type="button" class="otterbtn" value="���׳�������ֽ⿨" onclick="juanxian('', 101);"/>
                    <input type="button" class="otterbtn" value="�����м�����ֽ⿨" onclick="juanxian('', 102);"/>
                    <input type="button" class="otterbtn" value="����ӡ����" onclick="juanxian('', 301);"/>
                    <input type="button" class="otterbtn" value="���ױ���Ĥ" onclick="juanxian('', 401);"/>
                    <input type="button" class="otterbtn" value="���װٴ�" onclick="juanxian('', 402);"/>
                    <input type="button" class="otterbtn" value="ʹ�ó�������ֽ⿨" onclick="usewupin('', 8500101);"/>
                    <input type="button" class="otterbtn" value="ʹ�ø߼�����ֽ⿨" onclick="usewupin('', 8500103);"/>
                    <input type="button" class="otterbtn" value="ʹ�ó�������ֽ⿨" onclick="usewupin('', 8500104);"/>
                    <input type="button" class="otterbtn" value="ʹ��VIP(7��)" onclick="usewupin('', 8500151);"/>
                    <input type="button" class="otterbtn" value="ʹ��ӡ����" onclick="usewupin('', 8500113);"/>
                    <input type="button" class="otterbtn" value="ʹ��ף��" onclick="usewupin('', 8500111);"/>
                    <input type="button" class="otterbtn" value="ʹ�ù������" onclick="usewupin('', 8500211);"/>
                    <input type="button" class="otterbtn" value="ʹ��ʥ�����" onclick="usewupin('', 8500215);"/>
                    <input type="button" class="otterbtn" value="ʹ�ý�ɫ����" onclick="usewupin('', 8500209);"/>
                    <input type="button" class="otterbtn" value="ʹ�����˽����" onclick="usewupin('', 8500217);"/>
                    <br/>
                    <input type="button" class="otterbtn" value="��������" onclick="doDftt('');"/>
                    <input type="button" class="otterbtn" value="�������" onclick="doKftt('');"/>
                    <input type="button" class="otterbtn" value="���������Ϸ�" onclick="doDftt('1');"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" class="otterbtn" value="������������" onclick="diuqiwupin('', 8500302);"/>
                    <input type="button" class="otterbtn" value="����'��'" onclick="diuqiwupin('', 8600401);"/>
                    <input type="button" class="otterbtn" value="����'��'" onclick="diuqiwupin('', 8600402);"/>
                    <input type="button" class="otterbtn" value="����'��'" onclick="diuqiwupin('', 8600403);"/>
                    <input type="button" class="otterbtn" value="����'ʥ'" onclick="diuqiwupin('', 8600101);"/>
                    <input type="button" class="otterbtn" value="����'��'" onclick="diuqiwupin('', 8600102);"/>
                    <input type="button" class="otterbtn" value="����'��'" onclick="diuqiwupin('', 8600103);"/>
                    <input type="button" class="otterbtn" value="����'��'" onclick="diuqiwupin('', 8600104);"/>
                    <input type="button" class="otterbtn" value="�����ȿ�������" onclick="diuqiwupin('', 8500503);"/>
                    <input type="button" class="otterbtn" value="�����μ�ϴ��ʯ" onclick="diuqiwupin('', 8500594);"/>
                    <input type="button" class="otterbtn" value="������ͨϴ��ʯ" onclick="diuqiwupin('', 8500593);"/>
                    <input type="button" class="otterbtn" value="����ϴ��ʯ��Ƭ" onclick="diuqiwupin('', 8500590);"/>
                    <input type="button" class="otterbtn" value="����L" onclick="diuqiwupin('', 8600501);"/>
                    <input type="button" class="otterbtn" value="����O" onclick="diuqiwupin('', 8600502);"/>
                    <input type="button" class="otterbtn" value="����V" onclick="diuqiwupin('', 8600503);"/>
                    <input type="button" class="otterbtn" value="����E" onclick="diuqiwupin('', 8600504);"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" class="otterbtn" value="�����������" onclick="doGhbs('');"/>
                    <input type="button" class="otterbtn" value="��������" onclick="doBs('');"/>
                    <input type="button" class="otterbtn" value="�鿴��ʱ����" onclick="linshi('');"/>
                    <input type="button" class="otterbtn" value="��������" onclick="jingcailiansai('');"/>
                </td>
            </tr>
            <tr>
                <td>
                    ��ʼ��
                </td>
                <td>
                    <input type="button" class="otterbtn" value="C���۸�" onclick="initCPlayer();"/>
                    <input type="button" class="otterbtn" value="��־��Ա" onclick="initYizhi();"/>
                    ����������
                    <input type="text" value="<%=RxqqInstance.DF_SFFS%>" id="dffs"/>
                    <input type="button" class="otterbtn" onclick="updateDFFS();" value="�趨"/>
                    ���������
                    <input type="text" value="<%=RxqqInstance.DF_SFFS%>" id="kffs"/>
                    <input type="button" class="otterbtn" onclick="kfnk();" value="����ÿ�"/>
                    <script type="text/javascript">
                        function kfnk()
                        {
                            var fs = document.getElementById('kffs').value;
                            RxqqDwr.kfnk(fs, kfnkcallback);
                        }
                        function kfnkcallback(ret)
                        {
                            alert(ret);
                        }
                        function updateDFFS()
                        {
                            var fs = document.getElementById('dffs').value;
                            RxqqDwr.updateDFFS(fs, updateDFFScallback);
                        }
                        function updateDFFScallback(ret)
                        {
                            alert(ret);
                        }
                    </script>
                </td>
            </tr>
        </table>
        <br/>
        <%=RxqqInstance.id.size()%>
        <input type="button" value="չ��/�������" onclick="showTable('idlist');"/>
        <table id="idlist" width="100%" border="0" cellpadding="1" cellspacing="1" style="display:none;">
            <tr class="head">
                <th width="10%">��¼��</th>
                <th width="90%">����</th>
            </tr>
            <%
                int i = 1;
                List<String> ids = new ArrayList<String>();
                for (String s : maps.keySet()) {
                    ids.add(s);
                }
                Collections.sort(ids);
                for(String id : ids) {
                    i = 3 - i;
            %>
            <tr class="row<%=i%>">
                <td><%=id%></td>
                <td>
                    <input type='button' value='���µ�¼' class='otterbtn' onclick="relogin('<%=id%>');"/>
                    <input type='button' value='ע��' class='otterbtn' onclick="deleteuser('<%=id%>');"/>
                    <input type='button' value='������' class='otterbtn' onclick="doChangGui('<%=id%>');"/>
                    <input type='button' value='������(ȫ��)' class='otterbtn' onclick="doChangGuiAll('<%=id%>');"/>
                    <input type='button' value='��������' class='otterbtn' onclick="doDftt('<%=id%>');"/>
                    <input type='button' value='�鿴����' class='otterbtn' onclick="beibao('<%=id%>');"/>
                    <input type='button' value='��������(С��)' class='otterbtn' onclick="beibaozhengli('<%=id%>')"/>
                    <input type='button' value='��C����Ա(С��)' class='otterbtn' onclick="guaCPlayer('<%=id%>', 2)"/>
                    <input type='button' value='��C����Ա(���)' class='otterbtn' onclick="guaCPlayer('<%=id%>', 1)"/>
                    <input type='button' value='ħ����' class='otterbtn' onclick="mofashe('<%=id%>')"/>
                    <input type='button' value='ħ����(ȫ��)' class='otterbtn' onclick="mofasheAll('<%=id%>')"/>
                </td>
            </tr>
            <%
                }
            %>
        </table>
        <input type="button" value="չ��/������Ϣ" onclick="showTable('info');"/>
        <table id="info" width="100%" border="0" cellpadding="1" cellspacing="1">
            <tr class="head">
                <th>��¼��</th>
                <!--<th>ManagerId</th>-->
                <th>��Ϸ��</th>
                <th>��������</th>
                <th>��������</th>
                <th>����</th>
                <th>�ȼ�</th>
                <th>�ֽ�</th>
                <th>ʤ��</th>
                <th>����</th>
                <th>��ȯ</th>
                <th>MatchTime</th>
                <th>MaxMatchTime</th>
                <th>�ж���</th>
                <th>����ж���</th>
                <th>CurrentDate</th>
                <th>��������</th>
                <th>��������</th>
                <th>�������</th>
                <th>�������</th>
            </tr>
            <%
                i = 1;
                List<String> unlogedIds = new ArrayList<String>();
                for (String id : ids) {
                    i = 3 - i;
                    JSONObject json = RxqqInstance.info.get(id);
                    if(json.has("Message") && json.getString("Message").equals("����δ��¼")) {
                        unlogedIds.add(id);
                        continue;
                    }
//                    JSONObject json = new JSONObject(RxqqUtils.myInfo(id));
            %>
                <tr class="row<%=i%>">
                    <td><%=id%></td>
                    <%--<td><%=json.get("ManagerId")%></td>--%>
                    <td><%=json.opt("Name")%></td>
                    <td><%=json.opt("ManagerType")%></td>
                    <td><%=json.opt("ManagerTypeDes")%></td>
                    <td><%=json.opt("Area")%></td>
                    <td align="right"><%=json.opt("Level")%></td>
                    <td align="right"><%=json.opt("Money")%></td>
                    <td align="right"><%=json.opt("WinRate")%></td>
                    <%--<td><%=json.get("Stamina")%></td>--%>
                    <%--<td><%=json.get("MaxStamina")%></td>--%>
                    <td align="right"><%=json.opt("SP")%></td>
                    <%--<td><%=json.get("Score")%></td>--%>
                    <td align="right"><%=json.opt("CurPoint")%></td>
                    <td align="right"><%=json.opt("MatchTime")%></td>
                    <td align="right"><%=json.opt("MaxMatchTime")%></td>
                    <td align="right"><%=json.opt("Execution")%></td>
                    <td align="right"><%=json.opt("MaxExecution")%></td>
                    <td>
                        <%=json.opt("CurrentDate")%>
                        <input type='button' value='���µ�¼' onclick="relogin('<%=id%>');"/>
                    </td>
                    <%
                        JSONObject[] arenaRank = RxqqInstance.arenaRank.get(id);
                        JSONObject[] crossRank = RxqqInstance.crossRank.get(id);
                    %>
                    <%
                        if(arenaRank == null || arenaRank.length < 2) {
                    %>
                    <td>-</td><td>-</td>
                    <%
                        } else {
                    %>
                    <td><%=arenaRank[0].optInt("Score", 0)%></td>
                    <td><%=arenaRank[1].optInt("MyRank", 0)%></td>
                    <%
                        }
                        if(crossRank == null || crossRank.length < 2) {
                    %>
                    <td>-</td><td>-</td>
                    <%
                        } else {
                    %>
                    <td><%=crossRank[0].optInt("RealScore", 0)%></td>
                    <td><%=crossRank[1].optInt("MyRank", 0)%></td>
                    <%
                        }
                    %>
                </tr>
            <%
                }
            %>
        </table>
        <input type="button" value="չ��/���𱳰�" onclick="showTable('beibao');"/>
        <table id="beibao" width="100%" border="0" cellpadding="1" cellspacing="1">
            <tr class="head">
                <th>�ʺ�</th>
                <th>Code</th>
                <th>ItemId</th>
                <th>��</th>
                <th>����</th>
                <th>ImageId</th>
                <th>PID</th>
                <th>Level</th>
                <th>Type</th>
                <th>Strengthen</th>
                <th>Increment</th>
                <th>Count</th>
                <th>Order</th>
                <th>Status</th>
                <th>Description</th>
                <th>EquipPrefix</th>
                <th>EquipQuality</th>
                <th>EquipBuff</th>
            </tr>
        </table>
        <input type="button" value="չ��/��������" onclick="showTable('paimai');"/>
        <table id="paimai" width="100%" border="0" cellpadding="1" cellspacing="1">
            <tr class="head">
                <th>�ʺ�</th>
                <th>itemName</th>
                <th>itemStrengthLevel</th>
                <th>sellStartPrice</th>
                <th>sellEndPrice</th>
                <th>buyPrice</th>
                <th>rowTime</th>
                <th>limitTime</th>
                <!--<th>Type</th>-->
                <!--<th>Strengthen</th>-->
                <!--<th>Increment</th>-->
                <!--<th>Count</th>-->
                <!--<th>Order</th>-->
                <!--<th>Status</th>-->
                <!--<th>Description</th>-->
                <!--<th>EquipPrefix</th>-->
                <!--<th>EquipQuality</th>-->
                <!--<th>EquipBuff</th>-->
            </tr>
        </table>
        <input type="button" value="չ��/������ʱ" onclick="showTable('linshi');"/>
        <table id="linshi" width="100%" border="0" cellpadding="1" cellspacing="1">
            <tr class="head">
                <th>�ʺ�</th>
                <th>Code</th>
                <th>����</th>
                <th>ʣ������</th>
            </tr>
        </table>
    δ��¼�ʺţ�
    <%
        for(String id : unlogedIds) {
    %>
        <%=id%>
        <input type='button' value='���µ�¼' onclick="relogin('<%=id%>');"/>
        <br/>
    <%
        }
    %>
    </body>
<script type="text/javascript">
    function initYizhi()
    {
        RxqqDwr.initYizhi(initYizhiCallBack);
    }

    function initYizhiCallBack()
    {
        alert('done');
    }

    function initCPlayer()
    {
        RxqqDwr.initCPlayer(initCPlayerCallBack);
    }

    function initCPlayerCallBack(ret)
    {
        alert('done');
    }

    function init(type)
    {
        RxqqDwr.init(type, initcallback);
    }

    function initcallback(ret)
    {
        document.location = "main.jsp";
    }

    function adduser()
    {
        var name = document.getElementsByName('newusername')[0].value;
        var pwd = document.getElementsByName('newpassword')[0].value;
        for(var i = 0; i < document.getElementsByName('user').length; i++)
        {
            if(document.getElementsByName('user')[i].value == name)
            {
                alert('���û��Ѵ���');
                return;
            }
        }
        RxqqDwr.login(name, pwd, logincallback);
    }

    function deleteuser(id)
    {
        RxqqDwr.logout(id, '', logoutcallback);
    }

    function logoutcallback(ret)
    {
        document.location = "main.jsp";
    }

    function relogin(id)
    {
        RxqqDwr.login(id, '', logincallback);
    }

    function logincallback(result)
    {
//        document.location = "main.jsp";
    }

    function getInfo(id)
    {

    }

    function doDftt(email)
    {
        RxqqDwr.dftt(email, dfttcallback);
    }

    function dfttcallback(ret)
    {
        alert(ret);
    }

    function doKftt(email)
    {
        RxqqDwr.kftt(email, kfttcallback);
    }

    function kfttcallback(ret)
    {
        alert(ret);
    }

    function doJJC(email)
    {
        RxqqDwr.jjc(email, jjccallback);
    }

    function jjccallback(ret)
    {
        alert(ret);
    }

    function guaCPlayer(email, big)
    {
        if(email == null || email == '')
        {
            RxqqDwr.guaCPlayerAll(big, guaCPlayercallback);
        }
        else
        {
            RxqqDwr.guaCPlayer(email, big, guaCPlayercallback);
        }
    }

    function guaCPlayercallback(ret)
    {
        alert('C���������');
    }

    function beibaozhengli(email)
    {
        RxqqDwr.beibaozhengli(email, beibaozhenglicallback);
    }

    function beibaozhenglicallback(ret)
    {
        alert('�������');
    }

    function paimai(email)
    {
        var table = document.getElementById('paimai');
        for(var i = table.rows.length - 1; i > 0; i--)
        {
            table.deleteRow(i);
        }
        RxqqDwr.dopaimai(email, paimaicallback);
    }

    function paimaicallback(ret)
    {
        for(var i = 0; i < ret.length; i++)
        {
            var id = ret[i][0];
            var json = JSON.parse(ret[i][1]);
            var items = json['Info'];
            var table = document.getElementById('paimai');
            var row;
            var cell;
            if(items[0] != null)
            {
                for(var j = 0; j < items.length; j++)
                {
                    row = table.insertRow(-1);
                    row.className = 'row' + (j % 2 + 1);
                    cell = row.insertCell(-1);
                    cell.innerHTML = id;
                    cell = row.insertCell(-1);
                    cell.innerHTML = items[j]['itemName'];
                    cell = row.insertCell(-1);
                    cell.innerHTML = items[j]['itemStrengthLevel'];
                    cell = row.insertCell(-1);
                    cell.innerHTML = items[j]['sellStartPrice'];
                    cell = row.insertCell(-1);
                    cell.innerHTML = items[j]['sellEndPrice'];
                    cell = row.insertCell(-1);
                    cell.innerHTML = items[j]['buyPrice'];
                    cell = row.insertCell(-1);
                    cell.innerHTML = formatDate(items[j]['rowTime']);
                    cell = row.insertCell(-1);
                    cell.innerHTML = formatDate(items[j]['limitTime']);
//                    cell = row.insertCell(-1);
//                    cell.innerHTML = items[j]['Type'];
//                    cell = row.insertCell(-1);
//                    cell.innerHTML = items[j]['Strengthen'];
//                    cell = row.insertCell(-1);
//                    cell.innerHTML = items[j]['Increment'];
//                    cell = row.insertCell(-1);
//                    cell.innerHTML = items[j]['Count'];
//                    cell = row.insertCell(-1);
//                    cell.innerHTML = items[j]['Order'];
//                    cell = row.insertCell(-1);
//                    cell.innerHTML = items[j]['Status'];
//                    cell = row.insertCell(-1);
//                    cell.innerHTML = items[j]['Description'];
//                    cell = row.insertCell(-1);
//                    cell.innerHTML = items[j]['EquipPrefix'];
//                    cell = row.insertCell(-1);
//                    cell.innerHTML = items[j]['EquipQuality'];
//                    cell = row.insertCell(-1);
//                    cell.innerHTML = items[j]['EquipBuff'];
                }
            }
        }
        alert('������Ʒ���г�');
    }

    function beibao(email)
    {
        var table = document.getElementById('beibao');
        for(var i = table.rows.length - 1; i > 0; i--)
        {
            table.deleteRow(i);
        }
        RxqqDwr.beibao(email, beibaocallback);
    }


    function beibaocallback(retinit)
    {
        for(var ii = 0; ii < retinit.length; ii++)
        {
            var ret = retinit[ii];
            var id = ret[0];
            var json = JSON.parse(ret[1]);
            var items = json['Items'];
            if(items == null)
            {
                continue;
            }
//            alert("items: " + items + ", length: " + items.length);
            var table = document.getElementById('beibao');
            var row;
            var cell;
            for(var i = 0; i < items.length; i++)
            {
                row = table.insertRow(-1);
                row.className = 'row' + (ii % 2 + 1);
                cell = row.insertCell(-1);
                cell.innerHTML = id;
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['Code'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['ItemId'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['IsBinding'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['Name'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['ImageId'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['PId'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['CardLevel'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['Type'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['Strengthen'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['Increment'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['Count'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['Order'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['Status'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['Description'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['EquipPrefix'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['EquipQuality'];
                cell = row.insertCell(-1);
                cell.innerHTML = items[i]['EquipBuff'];
            }
        }
        alert('������Ʒ���г�');
    }

    function doChangGui(email)
    {
        RxqqDwr.changgui(email, changguicallback);
    }

    function changguicallback(ret)
    {
        var json = JSON.parse(ret);
        var cards = json['ResultCards'];
        var players = '�õ���Ա��';
        for(var i = 0; i < cards.length; i++)
        {
            players += cards[i]['Name'] + ',';
        }
        alert(players);
    }

    function doChangGuiAll(email)
    {
        RxqqDwr.changguiAll(email, changguiallcallback);
    }

    function changguiallcallback(ret)
    {
        alert(ret);
    }

    function mofashe(email)
    {
        RxqqDwr.mfs(email, mofashecallback);
    }

    function mofashecallback(ret)
    {
        alert(ret);
    }

    function mofasheAll(email)
    {
        RxqqDwr.mfsAll(email, mofasheallcallback);
    }

    function mofasheallcallback(ret)
    {
        alert(ret);
    }

    function juanxian(id, wupin)
    {
        RxqqDwr.juanxian(id, wupin, juanxiancallback);
    }

    function juanxiancallback(ret)
    {
        alert(ret)
    }

    function diuqiwupin(id, wupin)
    {
        RxqqDwr.diuqiwupin(id, wupin, diuqiwupincallback);
    }

    function diuqiwupincallback(ret)
    {
        alert(ret);
    }

    function usewupin(id, wupin)
    {
        RxqqDwr.usewupin(id, wupin, usewupincallback);
    }

    function usewupincallback(ret)
    {
        alert(ret);
    }

    function doBs(id)
    {
        RxqqDwr.beisai(id, doBscallback);
    }

    function doBscallback(ret)
    {
        alert(ret);
    }

    function doGhbs(id)
    {
        RxqqDwr.gonghuiBeisai(id, doGhbscallback);
    }

    function doGhbscallback(ret)
    {
        alert(ret);
    }

    function jingcailiansai(id)
    {
        RxqqDwr.jingcaiLiansai(id, jingcailiansaicallback);
    }

    function jingcailiansaicallback(ret)
    {
        alert(ret);
    }

    function linshi(id)
    {
        var table = document.getElementById('linshi');
        for(var i = table.rows.length - 1; i > 0; i--)
        {
            table.deleteRow(i);
        }
        RxqqDwr.linshibeibao(id, linshibeibaocallback);
    }

    function linshibeibaocallback(ret)
    {
        var row, cell;
        var table = document.getElementById('linshi');
        for(var i = 0; i < ret.length; i++)
        {
            row = table.insertRow(-1);
            row.className = 'row' + (i % 2 + 1);
            cell = row.insertCell(-1);
            cell.innerHTML = ret[i][0];
            cell = row.insertCell(-1);
            cell.innerHTML = ret[i][1];
            cell = row.insertCell(-1);
            cell.innerHTML = ret[i][2];
            cell = row.insertCell(-1);
            cell.innerHTML = ret[i][3];
        }
        alert('��ʱ������Ʒ���г�');
    }

    function formatDate(str)
    {
        return (str.substr(0,10) + ' ' + str.substr(11,8))
    }

    function showTable(tableName)
    {
        var table = document.getElementById(tableName);
        if(table.style.display == 'none')
        {
            table.style.display = '';
        }
        else
        {
            table.style.display = 'none';
        }
    }
</script>
</html>