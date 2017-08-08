<%@ page import="com.saille.html.travian.TravianMain"%>
<%@ page import="com.saille.html.travian.TravianUserMain"%>
<%@ page import="com.saille.html.travian.TravianTown"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2008-11-12
  Time: 9:37:14
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../include/include.jsp" %>
<%@ page pageEncoding="gb2312" %>
<script type='text/javascript' src='/dwr/engine.js'> </script>
<script type='text/javascript' src='/dwr/util.js'> </script>
<script type='text/javascript' src='/dwr/interface/TravianDwr.js'></script>
<%
    TravianMain main = TravianMain.getInstance();
//    main.addUser("��翹���", "pmgk");
//    main.addUser("zz", "zloves");
//    main.addUser("������˹", "zloves");
//    main.addUser("����ɵɵ", "zloves");
//    main.addUser("Oo��oO", "zloves");
//    main.addUser("ˮ��", "zloves");
    List<TravianUserMain> users = main.getUsers();
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");

%>
<html>
  <head>
      <title>Simple jsp page</title>
  </head>
  <script type="text/javascript">
      function refreshUser(userid)
      {
          TravianDwr.refreshUser(userid, refreshFrame);
      }

      function adduser()
      {
          var usr = document.getElementsByName('userid')[0].value;
          var pwd = document.getElementsByName('userpwd')[0].value;
          TravianDwr.addUser(usr, pwd, refreshFrame);
      }

      function deleteuser(id)
      {
          TravianDwr.deleteUser(id, refreshFrame);
      }

      function viewMerchant(userid, townId)
      {
          TravianDwr.refreshMerchant(userid, townId, refreshMerchant);
      }

      function refreshMerchant(value)
      {
          var values = value.split(',');
          var alerts = '';
          for(var i = 0; i < values.length; i++)
          {
//              alert(values[i]);
              var single = values[i].split('-');
              alerts += single[1] + ', ';
              alerts += 'ľ�ģ�' + single[4] + '��������' + single[5] + '�����飺' + single[6] + '��ʳ��' + single[7];
              alerts += '������ ' + single[8] + ' ������';
              alerts += '\n��Ҫ ' + single[2] + ' Сʱ���� ' + single[3] + ' ����';
              alerts += '\n\n';
          }
          alert(alerts);
      }

      function refreshFrame()
      {
          window.location.reload();
      }
  </script>
  <body>
  <%--<input class="otterbtn" value="try" onclick="tryDwr();"/>--%>
  �����ʺţ�
  <input class="inputbox" name="userid"/>
  ���룺
  <input type="password" class="inputbox" name="userpwd"/>
  <input type="button" onclick="adduser()" value="����" class="otterbtn"/>

  <%
      if(users != null) {

      for(TravianUserMain user : users) {
      //    user.login();
//          user.refreshTowns();
  %>
    <table class="navigator" width="100%">
        <head>
            <%=user.getUserid()%>
            <input type="button" class="otterbtn" value="ˢ��" onclick="refreshUser('<%=user.getUserid()%>')"/>
            <input type="button" class="otterbtn" value="ɾ��" onclick="deleteuser('<%=user.getUserid()%>')"/>
        </head>
        <tr class="head">
            <td nowrap width="10%">����<br/>����</td>
            <td nowrap width="8%">����</td>
            <td nowrap width="12%">ľ��</td>
            <td nowrap width="12%">����</td>
            <td nowrap width="12%">����</td>
            <td nowrap width="12%">��ʳ</td>
            <td nowrap width="20%">������</td>
            <td nowrap width="14%">����</td>
        </tr>
        <%
            if(user.getTowns() != null) {
            for(TravianTown town : user.towns) {
        %>
            <tr class="row<%=user.towns.indexOf(town) % 2 + 1%>">
                <td nowrap>
                    <%=town.getName()%><br/>
                    <a href="#" onclick="viewMerchant('<%=user.getUserid()%>','<%=town.getId()%>');">
                        <%=town.getMerchants()%>/<%=town.getMaxMerchants()%>
                    </a>
                </td>
                <td nowrap><%=town.getX()%>&nbsp;|&nbsp;<%=town.getY()%></td>
                <td nowrap>
                    <%=town.getWood()%>/<%=town.getWoodSize()%>(<%=town.getWoodProduct()%>)<br/>
                    (
                        <%
                            Calendar c = Calendar.getInstance();
                            c.setTime(new Date());
                            int second = (town.getWoodSize() - town.getWood()) * 60 * 60 / town.getWoodProduct();
                            c.add(Calendar.SECOND, second);
                        %>
                        <%=sdf.format(c.getTime())%>
                    )
                </td>
                <td nowrap>
                    <%=town.getClay()%>/<%=town.getClaySize()%>(<%=town.getClayProduct()%>)<br/>
                    (
                        <%
//                            Calendar c = Calendar.getInstance();
                            c.setTime(new Date());
                            second = (town.getClaySize() - town.getClay()) * 60 * 60 / town.getClayProduct();
                            c.add(Calendar.SECOND, second);
                        %>
                        <%=sdf.format(c.getTime())%>
                    )
                </td>
                <td nowrap>
                    <%=town.getIron()%>/<%=town.getIronSize()%>(<%=town.getIronProduct()%>)<br/>
                    (
                        <%
//                            Calendar c = Calendar.getInstance();
                            c.setTime(new Date());
                            second = (town.getIronSize() - town.getIron()) * 60 * 60 / town.getIronProduct();
                            c.add(Calendar.SECOND, second);
                        %>
                        <%=sdf.format(c.getTime())%>
                    )
                </td>
                <td nowrap>
                    <%=town.getFood()%>/<%=town.getFoodSize()%>(<%=town.getFoodProduct()%>)<br/>
                    (
                        <%
//                            Calendar c = Calendar.getInstance();
                            c.setTime(new Date());
                            second = (town.getFoodSize() - town.getFood()) * 60 * 60 / town.getFoodProduct();
                            c.add(Calendar.SECOND, second);
                        %>
                        <%=sdf.format(c.getTime())%>
                    )
                </td>
                <td nowrap><%=town.getConstructing()%>(<%=town.getEndTime() == null ? "��" : sdf.format(town.getEndTime())%>)</td>
                <td nowrap>
                    <select>
                <%
                    for(int i = 0; i < town.getBuilds().length; i++) {
                %>
                        <option><%=town.getBuilds()[i]%>&nbsp;<%=town.getLevels()[i]%></option>
                <%
                    }
                %>
                    </select>
                </td>
            </tr>
        <%
            }
            }
        %>
    </table>
  <%
      }
      }
  %>
  </body>
</html>