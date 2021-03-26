<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1> 분류할 이미지를 선택하세요 (JPG, JPEG, PNG만 가능) </h1>
    <form action="http://localhost:8082/image" method="POST" enctype="multipart/form-data">
    <input type="file" name = "file" />
    <input type="submit" />
</form>
</body>
</html>