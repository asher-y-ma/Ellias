<%--
  Created by IntelliJ IDEA.
  User: Ellias
  Date: 2008-4-28
  Time: 18:03:43
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../include/include.jsp"%>
<html>
<head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
    <title>�鼮��ӳɹ�</title>
</head>
<body>
    <div class="page_title">�鼮��ӳɹ�</div>
    <br/>
    �鼮��ӳɹ���
    <br/>
    <br/>
    <script type="text/javascript">
        function clickButton()
        {
            window.location = '/book.do?method=listBook';
        }
    </script>
    <input type="button" value="�鿴�鼮�б�" onclick="clickButton();"/>
</body>
</html>