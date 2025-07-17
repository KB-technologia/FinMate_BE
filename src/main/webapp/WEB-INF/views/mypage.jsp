<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>마이페이지</title>
</head>
<body>
<h1>👤마이페이지</h1>
<ul>
  <li><strong>계정 ID:</strong> ${myInfo.accountId}</li>
  <li><strong>이메일:</strong> ${myInfo.email}</li>
  <li><strong>생년월일:</strong> ${myInfo.birth}</li>
</ul>
</body>
</html>
