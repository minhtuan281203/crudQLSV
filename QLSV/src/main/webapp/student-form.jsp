<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 5/24/2023
  Time: 4:30 PM
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

  <div class="khung">
    <h1>Create Student</h1>
    <c:choose>
      <c:when test="${empty student.id}">

        <form action="students?action=create" method="POST">
          <table style="width: 350px">
            <tr>
              <td class="cot1"><label for="ten">Name:</label></td>
              <td class="cot1"><input type="text" id="ten" name="ten"></td>
            </tr>
            <tr>
              <td class="cot1"><label for="age">Age:</label></td>
              <td class="cot1"><input type="text" id="age" name="age"></td>
            </tr>
            <tr>
              <td class="cot1"><label for="address">Address:</label></td>
              <td class="cot1"><input type="text" id="address" name="address"></td>
            </tr>

<%--            add form field--%>
            <tr>
              <td class="cot1"><label for="image">Image:</label></td>
              <td class="cot1"><input type="file" id="image" name="image"></td>
            </tr>
            <tr>
              <td class="cot1"><input type="submit" class="btn" value="Create"></td>
              <td class="cot1"><a href="students" class="cancel">Cancel</a></td>
            </tr>
          </table>

        </form>
      </c:when>

      <c:otherwise>
        <form action="students?action=update" method="POST">
          <input type="hidden" name="id" value="${student.id}">

          <table style="width: 350px">
            <tr>
              <td class="cot1"><label for="ten2">Name:</label></td>
              <td class="cot1"><input type="text" id="ten2" name="ten" value="${student.ten}"></td>
            </tr>
            <tr>
              <td class="cot1"><label for="age2">Age:</label></td>
              <td class="cot1"><input type="text" id="age2" name="age" value="${student.age}"></td>
            </tr>
            <tr>
              <td class="cot1"><label for="address2">Address:</label></td>
              <td class="cot1"><input type="text" id="address2" name="address" value="${student.address}"></td>
            </tr>

<%--            add new field--%>
            <tr>
              <td class="cot1"><label for="image2">Image:</label></td>
              <td class="cot1"><input type="file" id="image2" name="image"></td>
            </tr>

            <tr>
              <td class="cot1"><input type="submit" class="btn" value="Update"></td>
              <td class="cot1"><a href="students" class="cancel">Cancel</a></td>
            </tr>
          </table>



        </form>

      </c:otherwise>
    </c:choose>

  </div>

</body>
</html>
