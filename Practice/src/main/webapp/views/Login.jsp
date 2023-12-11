<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login page</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
<div class="wrapper">
<div class="title">
Login
</div>
<div align="center">
	<form action="<%= request.getContextPath() %>/login" method="post">
	
	<div class="field">
		<input type="text" name="username" required>
		<label>Username</label>
    </div>
    
    <div class="field">
		<input type="password" name="password" required>
		<label>Password</label>
    </div>
        
    <div class="field">
    	<input type="submit" value="Submit"/>
    </div>
        
    </form>
</div>
</div>
</body>
</html>

<!-- <table style="with: 80%">
            <tr>
                <td><input type="text" name="username" placeholder="Username"/></td>
            </tr>

            <tr>
                <td><input type="password" name="password" placeholder="Password"/></td>
            </tr>
        </table> -->