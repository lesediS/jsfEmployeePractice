<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register page</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
<div class="wrapper">
<div class="title">
Register
</div>
<div align="center">
	<form action="<%= request.getContextPath() %>/register" method="post">
	
	<div class="field">
		<input type="text" name="firstName" required>
		<label>First Name</label>
    </div>
    
    <div class="field">
		<input type="text" name="lastName" required>
		<label>Last Name</label>
    </div>
    
    <div class="field">
		<input type="text" name="userName" required>
		<label>User Name</label>
    </div>
    
    <div class="field">
		<input type="text" name="address" required>
		<label>Address</label>
    </div>
    
    <div class="field">
		<input type="text" name="contact" required>
		<label>Contact Number</label>
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
                <td><input type="text" name="firstName" placeholder="First Name"/></td>
            </tr>
            
            <tr>
                <td><input type="text" name="lastName" placeholder="Last Name"/></td>
            </tr>
            
            <tr>
                <td><input type="text" name="userName" placeholder="Username"/></td>
            </tr>
            
            <tr>
                <td><input type="text" name="address" placeholder="Address"/></td>
            </tr>
            
            <tr>
                <td><input type="text" name="contact" placeholder="Contact Number"/></td>
            </tr>
                        
            <tr>
                <td><input type="password" name="password" placeholder="Password"/></td>
            </tr>
        </table> -->