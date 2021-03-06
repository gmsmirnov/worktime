<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>

<!-- Popper JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<%--
  Created by IntelliJ IDEA.
  User: Artress
  Date: 09.06.2019
  Time: 12:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Worktime</title>
</head>
<body>

<script>
    function timeConversion(millisec) {
        var seconds = (millisec / 1000).toFixed(1);
        var minutes = (millisec / (1000 * 60)).toFixed(1);
        var hours = (millisec / (1000 * 60 * 60)).toFixed(1);
        var days = (millisec / (1000 * 60 * 60 * 24)).toFixed(1);
        if (seconds < 60) {
            return seconds + " Sec";
        } else if (minutes < 60) {
            return minutes + " Min";
        } else if (hours < 24) {
            return hours + " Hrs";
        } else {
            return days + " Days"
        }
    }
</script>

<div class="container">
    <h2>Worktime service</h2>
    <form action="${pageContext.servletContext.contextPath}/start" method="post">
        <input type="submit" value="Start" class="btn btn-outline-success">
    </form>
    <form action="${pageContext.servletContext.contextPath}/finish" method="post">
        <input type="submit" value="Stop" class="btn btn-outline-danger">
    </form>
    <form action="${pageContext.servletContext.contextPath}/clear" method="post">
        <input type="submit" value="Clear table" class="btn btn-outline-primary">
    </form>
    <table class="table table-striped">
        <thead>
            <tr style="width: 120px;">
                <th width="300">Start time:</th>
                <th width="300">Finish time:</th>
                <th width="100">Length:</th>
            </tr>
        </thead>
        <tbody id="table">
            <tr>
                <th></th>
                <th>Total time:</th>
                <th>0.0 Sec </th>
            </tr>
        </tbody>
    </table>
    <script type="text/javascript">
        var timerId = setTimeout(function updateTable() {
            $.ajax("./table", {
                method : "get",
                complete : function(data) {
                    console.log(JSON.parse(data.responseText));
                    var result = "";
                    var intervals = JSON.parse(data.responseText);
                    var sum = 0;
                    for (var index = 0; index < intervals.length; index++) {
                        console.log(intervals[index].finishTime);
                        if (intervals[index].finishTime != null) {
                            sum += intervals[index].finishTime - intervals[index].startTime;
                            result += "<tr>" +
                                "<th width=\"300\">" + new Date(intervals[index].startTime) + "</th>" +
                                "<th width=\"300\">" + new Date(intervals[index].finishTime) + "</th>" +
                                "<th width=\"100\">" + timeConversion(intervals[index].finishTime - intervals[index].startTime) + "</th>" +
                                "</tr>";
                        }
                    }
                    result += "<tr><th></th>" +
                        "<th>Total time:</th>" +
                        "<th>" + timeConversion(sum) + "</th></tr>"
                    var table = document.getElementById("table");
                    table.innerHTML = result;
                }
            });
            timerId = setTimeout(updateTable, 1000);
        }, 1000);
    </script>
</div>
</body>
</html>