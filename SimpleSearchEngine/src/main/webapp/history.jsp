<%@page import ="java.util.ArrayList"%>
<%@page import ="com.Accio.HistoryResult"%>
<html>
<body>
<table border = 2>
<tr>
<th>keyword</th>
<th>link</th>
</tr>
<%
ArrayList<HistoryResult>results=(ArrayList<HistoryResult>)request.getAttribute("results");
for(HistoryResult result:results){
%>
<tr>
<td><%out.println(result.getKeyword());%></td>
<td><a href ="<%out.println(rseult.getLink());%>"><%out.println(rseult.getLink());%></a></td>
</tr>
<%
}
%>
</table>
</body>
<html>