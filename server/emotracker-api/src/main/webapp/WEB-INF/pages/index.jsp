<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <t:insertAttribute name="header" />

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/api/Emotracker.js"></script>

    <title>Emotracker</title>
</head>
<body>

<t:insertAttribute name="navigation" />

<script type="text/javascript">
    $( document ).ready(function() {
        new Emotracker({
            dataEventTbodyId: "dataEventTbody",
            dataEventTableId: "dataEventTable",
            dataEventAlertId: "dataEventAlert"
        });
    });
</script>

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
                    <li class="active"><a href="<spring:url value="/"/>">Измерения</a></li>
                </ol>

                <div style="display: none;" id="dataEventAlert" class="alert alert-info">
                    У вас пока нет результатов измерений Emotracker Mobile
                </div>

                <div id="dataEventTable" class="table-responsive">
                    <table class="table table-condensed table-hover table-bordered table-striped">

                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Дата</th>
                                <th>Название</th>
                                <th>Описание</th>
                            </tr>
                        </thead>

                        <tbody id="dataEventTbody">

                        </tbody>
                    </table>
                </div>

            </div>

        </div>
    </div>
</div>

</body>
</html>

