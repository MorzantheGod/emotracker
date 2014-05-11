<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <t:insertAttribute name="header" />

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/globalize.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/dx.module-core.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/dx.module-viz-core.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/dx.module-viz-charts.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/api/DataEventLoader.js"></script>

    <title>Emotracker Data</title>
</head>
<body>

<script type="text/javascript">
    $( document ).ready(function() {
        new DataEventLoader({
            dataEventId: "${dataEventId}",
            dataEventDivId: "dataEventDiv",
            dataEventTitleId: "dataEventTitle",
            dataEventDescriptionId: "dataEventDescription",
            startDateId: "startDate",
            endDateId: "endDateId",
            dataEventContentTbodyId: "dataEventContentTbody",
            chartContainerId: "chartPulseContainer",
            chartAccContainerId: "chartAccContainer",
            reportLinkId: "reportLink"
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

                <p id="dataEventDescription"></p>
                <p>
                    <label>Начало измерения: </label>
                    <label id="startDate"></label>
                    <label>Конец измерения</label>
                    <label id="endData"></label>
                </p>
            </div>

            <div class="row">
                <div id="chartPulseContainer" style="height: 400px;"></div>
            </div>

            <div class="row">
                <div id="chartAccContainer" style="height: 400px;"></div>
            </div>

            <div class="row">
                <p class="pull-right">
                    <a id="reportLink" href="<spring:url value="/api/data/getDataEventReport.action"/>">Скачать в Excel</a>
                </p>
                <div id="dataEventContentTable" class="table-responsive">
                    <table class="table table-condensed table-hover table-bordered table-striped">

                        <thead>
                            <tr>
                                <th>Заголовок</th>
                                <th>Счетчик</th>
                                <th>Дата Android</th>
                                <th>Дата устройства</th>
                                <th>Пульс</th>
                                <th>X</th>
                                <th>Y</th>
                                <th>Z</th>
                            </tr>
                        </thead>

                        <tbody id="dataEventContentTbody">

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
