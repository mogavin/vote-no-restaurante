<!DOCTYPE html>
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
    </style>
  </head>
  <body>
    <div id="carousel-example-generic" class="carousel slide">
	  <!-- Indicators -->
	  <ol class="carousel-indicators">
	    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
	    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
	  </ol>
	
	  <!-- Wrapper for slides -->
		<div class="carousel-inner">
		     <!-- Slide -->
		  <div class="item active">
	       <div class="row">
	           <div class="col-sm-6 col-md-6 col-xs-6 nopadding img-container">
	              	<img src="http://placehold.it/1280x720" alt="" class="voto voto-positivo" data-restaurante="A"/>
	           </div>
	           <div class="col-sm-6 col-md-6 col-xs-6 nopadding img-container">
	               <img src="http://placehold.it/1280x720" alt="" class="voto voto-positivo" data-restaurante="B"/>
	           </div>
	           <div class="carousel-caption">Qual dos dois você mais gosta ?</div>
	       </div>
		  </div>
			<!-- Slide -->
			<div class="item">
			    <div class="row">
			        <div class="col-sm-12 col-xs-12 nopadding img-container">
			            <img src="http://placehold.it/1280x720" alt=""/>
			            <div class="carousel-caption">
			            	<button type="button" class="btn btn-primary voto voto-positivo" data-restaurante="C">Primary</button>
			            	<button type="button" class="btn btn-danger voto voto-negativo" data-restaurante="C">Danger</button>
			            </div>
			        </div>
			    </div>
			</div>
		</div>
	</div>
  <!-- Controls -->
  <!--<a class="carousel-control" href="#carousel-example-generic" data-slide="next"></a>-->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    
    <script>
    	$carousel = $('#carousel-example-generic');
    	
    	$carousel.carousel({
    		interval: false
    	});    	
    	$('.voto').click(function() {
    		$carousel.carousel('next');
    	});
    	
    	var votos = {};
    	$(".voto-positivo").click(function(){
    		var restaurante = $(this).attr("data-restaurante");
    		votos[restaurante] = true;
    		alert(JSON.stringify(votos, null, 4));
    	});
		$(".voto-negativo").click(function(){
			var restaurante = $(this).attr("data-restaurante");
    		votos[restaurante] = false;
    		alert(JSON.stringify(votos, null, 4));
    	});
    </script>
  </body>
</html>