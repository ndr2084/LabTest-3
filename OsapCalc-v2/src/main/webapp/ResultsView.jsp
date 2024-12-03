<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0">
	<jsp:directive.page contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8" session="true" />
	<jsp:output doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
		omit-xml-declaration="true" />
	<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Student Loan Calculator - Result</title>
<link rel="StyleSheet"
	href="${pageContext.request.contextPath}/res/mc.css" type="text/css"
	title="cse4413" media="screen, print" />
</head>
<body>
	<header> ${appName} </header>
	<nav>
		<ul>
			<li><a href="#"> Home </a></li>
			<li><a href="#"> About</a></li>
		</ul>
	</nav>
	<form action="UI.html" method="GET" class="resultForm">
		<fieldset>
			<legend>Calculator</legend>
			<table>
				<tr>
					<td><strong>Monthly payments: </strong></td>
					<td>${payment}</td>
				</tr>
				<tr>
					<td colspan="2">
					  <small>Calculation used
							principal=${principle}, interest=${interest} and period=${period}
					  </small>
					</td>
				</tr>
				<tr>
					<td><button action="restart" name="restart" value="true">Re-compute</button></td>
				</tr>
			</table>
		</fieldset>
	</form>

	<aside>
		<figure>
			<img
				src="https://lassonde.yorku.ca/wp-content/themes/lassonde/images/Lassonde-YorkU-Logo-RGB.svg"
				height="100px" width="100px" />
			<figcaption>York University</figcaption>
		</figure>
	</aside>
	<footer> Copyright EECS 4413Z- All rights reserved</footer>
</body>
	</html>
</jsp:root>
