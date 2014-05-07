<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <t:insertAttribute name="header" />

    <script type="text/javascript" src="/resources/js/emotracker-auth.js"></script>
    <script type="text/javascript" src="/resources/js/emotracker.js"></script>

    <title>Emotracker</title>
</head>
<body>


<t:insertAttribute name="navigation" />

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="#">Измерения</a></li>
                <li><a href="#">Профиль</a></li>
            </ul>
        </div>



        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="row">

                <ol class="breadcrumb">
                    <li><a href="<spring:url value="/"/>">Измерения</a></li>
                    <li class="active">01.02.2014 15:03:03 Фильм</li>
                </ol>



            </div>

        </div>
    </div>
</div>

</body>
</html>
