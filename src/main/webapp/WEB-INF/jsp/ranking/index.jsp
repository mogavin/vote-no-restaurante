<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Ranking de restaurantes</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
  	
  	<div class="container-fluid">  		
  		<div class="row">
	  		<c:forEach var="error" items="${errors}">
			    ${error.category} - ${error.message}<br />
			</c:forEach>
		</div>
 		<div class="row"> 			
		    <div class="panel panel-primary">
		    	<div class="panel-heading text-center">
		      		<h4>Ranking Geral</h4>
		      	</div>
		      	<div class="panel-body">		    	
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Posição</th>
								<th>Restaurante</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="itemRankingGeral" items="${rankingGeral}" varStatus="status">
								<tr>
									<td>#${status.count}</td>
									<td>${itemRankingGeral.restaurante}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<c:if test="${not empty usuario.id}">
			<div class="row">
				<div class="panel panel-primary">
					<div class="panel-heading text-center">
			      		<h4>Seu ranking, ${usuario.nome}</h4>
			      	</div>
			      	<div class="panel-body">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>Posição</th>
									<th>Restaurante</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="itemRankingUsuario" items="${rankingUsuario}" varStatus="status">
									<tr>
										<td>#${status.count}</td>
										<td>${itemRankingUsuario.restaurante}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</c:if>
		<div class="row text-center">
			<a class="btn btn-primary col-xs-12 col-md-offset-5 col-md-2" href="<c:url value="/"/>" role="button">
				Votar<c:if test="${not empty usuario.id}"> novamente</c:if>
			</a>
		</div>
	</div>
	
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
  </body>
</html>