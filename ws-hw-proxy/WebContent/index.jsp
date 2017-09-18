<%@page import="java.net.URI"%>
<%@page import="java.util.List"%>
<%@ page import="ec.com.umbral.ws.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hardware-proxy</title>
</head>
<body>
	<h1>Test Page!</h1>
	
	<%
		ServerAPI api = new ServerAPI();
		api.connect("ws://localhost:8080/ws-hw-proxy/websocket/hw-proxy");
		out.write("<h2>"+api.getCid().toString()+"</h2>\n");
// 		for(String dev : devices) {
// 			out.write(dev+"<br/>");
// 		}
		api.disconnect();
	%>
	
</body>
</html>