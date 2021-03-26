<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Classification Result!</title>
</head>
<body>
    <img src="data:image/jpg;base64,${img}" width="${imgWidth}" height="${imgHeight}" />
    <h2> ^ Your Image </h2>
    <h5> ${result.class[0]} : ${result.probability[0]} % </h5>
    <h5> ${result.class[1]} : ${result.probability[1]} % </h5>
    <h5> ${result.class[2]} : ${result.probability[2]} % </h5>
</body>
</html>