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
});
$(".voto-negativo").click(function(){
	var restaurante = $(this).attr("data-restaurante");
	mapaVotos[restaurante] = false;
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
		//Usuario será definido no servidor. Setado como 'undefined' pois 'null' não inclui o atributo 
		data["votos[{I}].usuario".replace("{I}", i)] = undefined;
		data["votos[{I}].isPositivo".replace("{I}", i)] = mapaVotos[restaurante];
		data["votos[{I}].restaurante".replace("{I}", i)] = restaurante;
		i++;
	}
	
	return data;
}