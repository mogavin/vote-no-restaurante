<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/template" %>
<%@taglib tagdir="/WEB-INF/tags/votacao" prefix="votacaotaglib"%>

<t:geral>
	<jsp:attribute name="title">
	  Vote no restaurante
	</jsp:attribute>
	<jsp:attribute name="style">
	     <link rel="stylesheet" href="<c:url value="/css/votacao.css"/>">
	</jsp:attribute>
	<jsp:attribute name="script">
        <script src="<c:url value="/js/jquery.redirect.js"/>"></script>
		<script src="<c:url value="/js/votacao.js"/>"></script>
	</jsp:attribute>
	<jsp:body>
	    <div id="carousel-example-generic" class="carousel slide">
		  <!-- Wrapper for slides -->
			<div class="carousel-inner">
			  <!-- Slide -->
			  <div class="item active container-fluid">
		       <div class="row">
		           <div class="col-sm-6 col-md-6 col-xs-6 nopadding img-container image-zoom voto voto-positivo" data-restaurante="${restaurantes[0]}" data-inverse="${restaurantes[1]}">
		              	<img src="<votacaotaglib:cssImgRest restaurante='${restaurantes[0]}'/>"/>
		           </div>
		           <div class="col-sm-6 col-md-6 col-xs-6 nopadding img-container image-zoom voto voto-positivo" data-restaurante="${restaurantes[1]}" data-inverse="${restaurantes[0]}">
		              	<img src="<votacaotaglib:cssImgRest restaurante='${restaurantes[1]}'/>"/>
		           </div>
		           <div class="carousel-caption">
		           		<div class="panel panel-primary">
					      <div class="panel-heading"><h3>Qual dos dois você mais gosta ?</h3></div>
					    </div>
		           </div>
		       </div>
			  </div>
				<c:forEach var="restaurante" items="${restaurantes}" varStatus="status" begin="2">
				<!-- Slide -->
					<div class="item">
					    <div class="row container-fluid">
					        <div class="col-sm-12 col-xs-12 nopadding img-container">
		   		              	<img src="<votacaotaglib:cssImgRest restaurante='${restaurante}'/>"/>
					            <div class="carousel-caption">
					            	<div class="carousel-caption">
						           		<div class="panel panel-primary">
									      <div class="panel-heading">
									      	<h3>E deste, você gosta ?</h3>
									      </div>
						            	  <div class="panel-body">
						            	  	<button type="button" class="btn btn-primary voto voto-positivo" data-restaurante="${restaurante}">
						            	  		<i class="fa fa-thumbs-o-up fa-3x" aria-hidden="true"></i>
						            	  	</button>
					            			<button type="button" class="btn btn-danger voto voto-negativo" data-restaurante="${restaurante}">
					            				<i class="fa fa-thumbs-o-down fa-3x" aria-hidden="true"></i>
					            			</button>
						            	  </div>								      
									    </div>
						           	</div>				            	
					            </div>
					        </div>
					    </div>
					</div>
				</c:forEach>
				<!-- Slide form -->
				<div class="item">
				    <div class="row container-fluid">
				        <div class="col-sm-offset-2 col-sm-8 col-xs-12 nopadding img-container">
				        	<div class="panel panel-primary">
						      <div class="panel-heading">
						      	<h4>
						      		Ok, finalizamos ! Obrigado pela sua opinião... (Poderia nos informar seus dados <i class="fa fa-smile-o" aria-hidden="true"></i> ?)
						      	</h4>
						      </div>
			            	  <div class="panel-body">
			            	  	<form id="form-usuario" class="form-horizontal">
								  <div class="form-group">
								    <label for="inputEmail" class="col-sm-2 control-label">Email</label>
								    <div class="col-sm-8">
								      <input type="email" class="form-control" id="inputEmail" placeholder="Email" required="true">
								    </div>
								  </div>
		  						  <div class="form-group">
								    <label for="inputNome" class="col-sm-2 control-label">Nome</label>
								    <div class="col-sm-8">
								      <input type="text" class="form-control" id="inputNome" placeholder="Nome" required="true">
								    </div>
								  </div>
								  <div class="form-group">
								    <div class="col-sm-offset-2 col-sm-6">
								      <button class="btn btn-primary">Confirmar</button>
								    </div>
								  </div>
								</form>
			            	  </div>								      
						    </div>			            
				        </div>
				    </div>
				</div>
			</div>
		</div>
	</jsp:body>
</t:geral>