<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/template" %>

<t:geral>
	<jsp:attribute name="title">
	  Ranking de restaurantes
	</jsp:attribute>
	<jsp:body>  	
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
	</jsp:body>
</t:geral>