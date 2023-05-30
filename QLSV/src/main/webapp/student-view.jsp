<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 5/26/2023
  Time: 2:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>QLSV</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<%--<div class="banner">--%>
<%--  <img src="img/banner.jpg" alt="">--%>
<%--</div>--%>

<div class="khung">
  <h1>Student Manager</h1><br>

  <div class="newSearch">
    <a href="students?action=new" class="student_link">Add new Student</a>

    <br><br>
    <form method="get" action="students">
      <input type="hidden" name="action" value="search">
      <input type="text" name="searchItem" placeholder="Nhập tên sinh viên muốn tìm" class="ipSearch">
      <button type="submit" class="btn-search">Tìm kiếm</button>
    </form>
  </div>
  <br>
  <table >
    <tr class="tr_first">
    <th>Id</th>
    <th>Name</th>
    <th>Age</th>
      <th>Address</th>
      <th>Image</th>
    <th>Action</th>
    </tr>
    <c:forEach var="student" items="${studentList}">
      <tr>
        <td>${student.id}</td>
        <td>${student.ten}</td>
        <td>${student.age}</td>
        <td>${student.address}</td>
        <td>

            <img src="${student.imageUrl}" alt="Student image" class="image-container">
        </td>
        <td>
          <a style="text-decoration: none; padding: 10px; background-color: gray; color: #fff;"
             href="students?action=edit&id=${student.id}">Edit</a>
          <a  style="text-decoration: none; padding: 10px; background-color: red; color: #fff;"
              href="students?action=delete&id=${student.id}"
             onclick="return confirm('Are you sure you want to delete this student?')">Delete</a>
        </td>
      </tr>
    </c:forEach>
  </table>

</div>
</body>
</html>
