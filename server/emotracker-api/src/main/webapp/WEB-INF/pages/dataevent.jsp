<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <t:insertAttribute name="header" />

    <script type="text/javascript" src="/resources/js/DataEventLoader.js"></script>

    <title>Emotracker</title>
</head>
<body>

<script type="text/javascript">
    $( document ).ready(function() {
        new DataEventLoader({
            dataEventId: "${dataEventId}",
            dataEventDivId: "dataEventDiv",
            dataEventTitleId: "dataEventTitle"
        });
    });
</script>

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
            <div id="dataEventDiv" class="row">

                <ol class="breadcrumb">
                    <li><a href="<spring:url value="/"/>">Измерения</a></li>
                    <li id="dataEventTitle" class="active"></li>
                </ol>



            </div>

        </div>
    </div>
</div>

</body>
</html>
