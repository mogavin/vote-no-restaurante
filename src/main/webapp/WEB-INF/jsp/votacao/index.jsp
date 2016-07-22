<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/votacao" prefix="votacaotaglib"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <style>
    	.carousel .row{
    		height: 100vh;
    	}
    	
    	.carousel.slide img {
		    width: 100%;
		    height: 100% !important;
		}
		
		.nopadding {
		   padding: 0;
		}
		
		.img-container{
		    height:100%;
		    max-height:1000px;
		}
		
		.voto{
			cursor:pointer;
		}
    </style>
  </head>
  <body>
    <div id="carousel-example-generic" class="carousel slide">
	  <!-- Wrapper for slides -->
		<div class="carousel-inner">
		  <!-- Slide -->
		  <div class="item active container-fluid">
	       <div class="row">
	           <div class="col-sm-6 col-md-6 col-xs-6 nopadding img-container voto voto-positivo" data-restaurante="${restaurantes[0]}" data-inverse="${restaurantes[1]}">
	              	<img src="<votacaotaglib:cssImgRest restaurante='${restaurantes[0]}'/>"/>
	           </div>
	           <div class="col-sm-6 col-md-6 col-xs-6 nopadding img-container voto voto-positivo" data-restaurante="${restaurantes[1]}" data-inverse="${restaurantes[0]}">
	              	<img src="<votacaotaglib:cssImgRest restaurante='${restaurantes[1]}'/>"/>
	           </div>
	           <div class="carousel-caption">Qual dos dois você mais gosta ?</div>
	       </div>
		  </div>
			<!-- Slide -->
			<div class="item">
			    <div class="row container-fluid">
			        <div class="col-sm-12 col-xs-12 nopadding img-container">
   		              	<img src="<votacaotaglib:cssImgRest restaurante='${restaurantes[2]}'/>"/>
			            <div class="carousel-caption">
			            	<button type="button" class="btn btn-primary voto voto-positivo" data-restaurante="WENDYS">Primary</button>
			            	<button type="button" class="btn btn-danger voto voto-negativo" data-restaurante="WENDYS">Danger</button>
			            </div>
			        </div>
			    </div>
			</div>
			<!-- Slide form -->
			<div class="item">
			    <div class="row container-fluid">
			        <div class="col-sm-offset-2 col-sm-8 col-xs-12 nopadding img-container">
			            <form id="form-usuario" class="form-horizontal">
						  <div class="form-group">
						    <label for="inputEmail" class="col-sm-2 control-label">Email</label>
						    <div class="col-sm-8">
						      <input type="email" class="form-control" id="inputEmail" placeholder="Email">
						    </div>
						  </div>
  						  <div class="form-group">
						    <label for="inputNome" class="col-sm-2 control-label">Nome</label>
						    <div class="col-sm-8">
						      <input type="text" class="form-control" id="inputNome" placeholder="Nome">
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

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    <script src="<c:url value="/js/jquery.redirect.js"/>"></script>
    
    <script>
    	$carousel = $('#carousel-example-generic');
    	
    	$carousel.carousel({
    		interval: false
    	});    	
    	$('.voto').click(function() {
    		$carousel.carousel('next');
    	});
    	
    	var mapaVotos = {};
    	$(".voto-positivo").click(function(){
    		var restauranteVotoPositivo = $(this).attr("data-restaurante");
    		mapaVotos[restauranteVotoPositivo] = true;
    		//Se houver mais de uma opção, vota negativo pela outra
    		var restauranteVotoNegativo = $(this).attr("data-inverse");    		
    		if(restauranteVotoNegativo) mapaVotos[restauranteVotoNegativo] = false;
    		alert(JSON.stringify(mapaVotos, null, 4));
    	});
		$(".voto-negativo").click(function(){
			var restaurante = $(this).attr("data-restaurante");
			mapaVotos[restaurante] = false;
    		alert(JSON.stringify(mapaVotos, null, 4));
    	});
				
		$("form#form-usuario").submit(function(e){
		     e.preventDefault(); //Prevent the normal submission action
		     var form = this;
		     
		     var usuario = {
		    	nome : $("input[id='inputNome']",form).val(),
		    	email : $("input[id='inputEmail']",form).val()
		     }
		     
		     //Obtemos os dados do usuario
		     var data = montarDadosParaSubmeterForm(mapaVotos, usuario);
			 $.redirect("votacao/votar", data);//POST por padrão no plugin 'jquery.redirect'
		});
		
		function montarDadosParaSubmeterForm(votos, usuario){			
			var data = {};
			
			//Popula dados do usuario
			data["usuario.nome"] = usuario.nome;
			data["usuario.email"] = usuario.email;
			
			//Popula dados dos votos
			var i = 0;
			for(var restaurante in mapaVotos){
				console.log(restaurante + " : " + mapaVotos[restaurante]);
				//Usuario será definido no servidor. Setado como 'undefined' pois 'null' não inclui o atributo 
				data["votos[{I}].usuario".replace("{I}", i)] = undefined;
				data["votos[{I}].isPositivo".replace("{I}", i)] = mapaVotos[restaurante];
				data["votos[{I}].restaurante".replace("{I}", i)] = restaurante;
				i++;
			}
			
			return data;
		} 
    </script>
  </body>
</html>