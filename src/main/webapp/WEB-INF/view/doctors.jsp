<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">




<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" >
<title>Doctors</title>


</head>
<body>

	<div >
		<%@ include file="CAHomeTemplate.jsp"%>
	</div>
	

	<ul  class="nav nav-tabs justify-content-center nav-fill"  >
		<li class="nav-item "><a  class="nav-link " style="color:#53e3a6;" href="/doctors" ><i class="fa fa-user-md" aria-hidden="true"></i> Doktori</a></li>
		<li class="nav-item "><a class="nav-link " style="color:#53e3a6;" href="/nurse"><i class="fa fa-user-md" aria-hidden="true"></i> Sestre</a></li>
		<li class="nav-item "><a class="nav-link " style="color:#53e3a6;" href="/doctorsSearch"><i class="fa fa-search" aria-hidden="true"></i>Pretraga doktora</a></li>	
	</ul>
<div class="container">
	<div class="row">
	 <div class="col-sm">
		<br> <br> <br>
	<form:form method="POST" action="/doctors/create"
		modelAttribute="userDto">
		<table>
			<tr>
				<td><form:label path="nameDto">Ime:</form:label></td>
				<td><form:input class="form-control" placeholder="Name" path="nameDto" /></td>
			</tr>
			<tr>
				<td><form:label path="surnameDto">Prezime:</form:label></td>
				<td><form:input class="form-control" placeholder="Surname" path="surnameDto" /></td>

			<tr>
				<td><form:label path="usernameDto">Korisničko ime:</form:label></td>
				<td><form:input class="form-control" path="usernameDto" /></td>
			</tr>
			<tr>
				<td><form:label path="passwordDto">Šifra:</form:label></td>
				<td><form:input class="form-control" path="passwordDto" /></td>
			
				
			</tr>
			
			
			<tr>
			
					
							<td colspan="2"><form:select placeholder="Klinika" class="form-control"
									path="clinicDto">
									<form:option value="null">Izaberi kliniku</form:option>
									<c:forEach items="${allClinics}" var="clinic">
										<form:option value="${clinic.idDto}">${clinic.nameDto}</form:option>
									</c:forEach>
								</form:select></td>
						</tr>
			
			<tr>
			<tr>
				
				
							
					<td colspan="2">		
					<form:select class="form-control" placeholder="Tip Pregleda" path="tipPregledaDto">		
						<form:option value="null">Izaberi tip pregleda</form:option>
						<form:option value="Ginekologija"></form:option>
						<form:option value="Urologija"></form:option>
						<form:option value="Stomatologija"></form:option>
						<form:option value="Hirurgija"></form:option>
						<form:option value="Kardiologija"></form:option>
					</form:select>
					</td>	
					</tr>
				
				
				<td></td>
				<td></td>
				<td><input type="submit" class="btn btn-outline-danger" value="Kreiraj" /></td>
			</tr>
		</table>
	</form:form>
	
	</div>
	 <div class="col-sm">
	 <br> <br> <br>
	<table class="table">
		
		<tr style="background-color: #53e3a6;">
			<th colspan=8 style="text-align=center;"><h6 style="color:white; letter-spacing: 4px; text-align=center;"> MEDICINSKO OSOBLJE </h6> </th>
		</tr>
		<tr>
			<td>Ime</td>
			<td>Adresa</td>
			<td>Mobilni telefon</td>
			<td>Biografija</td>
			
			<td>Obriši</td>
			<td>Izmeni</td>
			<td>Više</td>
		</tr>
		<tr>
			<c:forEach var="user" items="${doctorsDto}">
				<tr>
					<td><c:out value="${user.nameDto}" /></td>
					<td><c:out value="${user.surnameDto}" /></td>
					<td><c:out value="${user.phoneDto}" /></td>
					<td><c:out value="${user.biographyDto}" /></td>
					<td><a class="btn btn-outline-success" href="/doctors/delete/${user.idDto}">Delete</a></td>
					<td><a class="btn btn-outline-success" href="/doctors/edit/${user.idDto}">Edit</a></td>
					<td><a class="btn btn-outline-info" href="/clinics/details/${user.idDto}">Details</a></td>
				</tr>
			</c:forEach>
		</tr>

	</table>
	</div>
	</div>
	</div>
	
	
	
	
</body>
</html>