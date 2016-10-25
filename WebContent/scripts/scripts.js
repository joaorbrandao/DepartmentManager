/**
 * Este script permite manipular as Modalidades
 */
function printModalidades(listaModalidades){
	var i = 0;
	
	for(i = 0; i < listaModalidades.length; i++){
		var obj = JSON.parse(listaModalidades[i]);
		select = document.getElementById('displayModalidades');
		select.options[select.options.length] = new Option(obj.mod_nome, obj.mod_id);
	}
}

function getModalidades(){
	var ajaxRequest = new XMLHttpRequest();
	var listaModalidades;
	
    ajaxRequest.onreadystatechange = function() {
        if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
        	listaModalidades = JSON.parse(ajaxRequest.responseText);
        	printModalidades(listaModalidades);
        }
    };
	ajaxRequest.open("POST", "/RINTE_GDD/ServicoModalidades", true);
	ajaxRequest.send();
	
}
/**
 * Este script permite manipular as Provas
 */
function printProvas(listaProvas){
	var i = 0;
	
	for(i = 0; i < listaProvas.length; i++){
		var obj = JSON.parse(listaProvas[i]);
		select = document.getElementById('displayProvas');
		select.options[select.options.length] = new Option(obj.prova_nome, obj.prova_id);
	}
}

function getProvas(){
	var ajaxRequest = new XMLHttpRequest();
	var listaProvas;
	
    ajaxRequest.onreadystatechange = function() {
        if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
        	listaProvas = JSON.parse(ajaxRequest.responseText);
        	printProvas(listaProvas);
        }
    };
	ajaxRequest.open("POST", "/RINTE_GDD/ServicoProvas", true);
	ajaxRequest.send();
	
}
/**
 * Este script permite manipular os Utilizadores
 */
function printUtilizadores(listaUtil){
	var i = 0;
	
	for(i = 0; i < listaUtil.length; i++){
		var obj = JSON.parse(listaUtil[i]);
		select = document.getElementById('displayUtil');
		select.options[select.options.length] = new Option(obj.util_nome, obj.util_id);
		select1 = document.getElementById('displayUtil1');
		select1.options[select1.options.length] = new Option(obj.util_nome, obj.util_id);
	}
}

function getUtilizadores(){
	var ajaxRequest = new XMLHttpRequest();
	var listaUtil;
	
    ajaxRequest.onreadystatechange = function() {
        if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
        	listaUtil = JSON.parse(ajaxRequest.responseText);
        	printUtilizadores(listaUtil);
        }
    };
	ajaxRequest.open("POST", "/RINTE_GDD/ServicoUtilizadores", true);
	ajaxRequest.send();
	
}
/**
 * Este script permite manipular os Pedidos de inscrição
 * relativas às modalidades
 */
function printPedidosMod(listaPedidos){
	var i = 0;
	
	for(i = 0; i < listaPedidos.length; i++){
		var obj = JSON.parse(listaPedidos[i]);
		select = document.getElementById('displayInfoMod');
		var selectOptionText = obj.name + " -> " + obj.value;
		select.options[select.options.length] = new Option(selectOptionText, listaPedidos[i]);
	}
}

function getPedidosMod(){
	var ajaxRequest = new XMLHttpRequest();
	var listaPedidos;
	
    ajaxRequest.onreadystatechange = function() {
        if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
        	if(ajaxRequest.responseText == "false"){
        		select = document.getElementById('displayInfoMod');
        		select.options[select.options.length] = new Option("Nenhum pedido de inscrição", "false");
        	}else{
	        	listaPedidos = JSON.parse(ajaxRequest.responseText);
	        	//alert(listaPedidos[0]);
	        	printPedidosMod(listaPedidos);
        	}
        }
    };
	ajaxRequest.open("POST", "/RINTE_GDD/AdminServicoModInfo", true);
	ajaxRequest.send();
	
}
/**
 * Este script permite manipular os Pedidos de inscrição
 * relativas às modalidades
 */
function printPedidosProvas(listaPedidos){
	var i = 0;
	
	for(i = 0; i < listaPedidos.length; i++){
		var obj = JSON.parse(listaPedidos[i]);
		select = document.getElementById('displayInfoProva');
		var selectOptionText = obj.name + " -> " + obj.value;
		select.options[select.options.length] = new Option(selectOptionText, listaPedidos[i]);
	}
}

function getPedidosProvas(){
	var ajaxRequest = new XMLHttpRequest();
	var listaPedidos;
	
    ajaxRequest.onreadystatechange = function() {
        if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
        	if(ajaxRequest.responseText == "false"){
        		select = document.getElementById('displayInfoProva');
        		select.options[select.options.length] = new Option("Nenhum pedido de inscrição", "false");
        	}else{
        		listaPedidos = JSON.parse(ajaxRequest.responseText);
            	//alert(listaPedidos[0]);
            	printPedidosProvas(listaPedidos);
        	}
        }
    };
	ajaxRequest.open("POST", "/RINTE_GDD/AdminServicoProvasInfo", true);
	ajaxRequest.send();
	
}

/*-------------------------------------COOKIES-------------------------------------*/
/**
 * Este script permite destruir os Cookies.
 */
function verificaCookies(){
	var ajaxRequest = new XMLHttpRequest();
	//alert("cheguei1");
    ajaxRequest.onreadystatechange = function() {
        if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
        	var resposta = JSON.parse(ajaxRequest.responseText);
        	//alert("Res: "+resposta.cookie.toString());
        	if(resposta.cookie == false){
        		alert("A sua sessão expiro!");
        		window.location.href = "/RINTE_GDD/login.html";
        	}else{
        		return true;
        	}
        }
    };
	ajaxRequest.open("POST", "/RINTE_GDD/ServicoVerificaCookies", true);
	//alert("cheguei2");
	ajaxRequest.send();
}


/**
 * Este script permite destruir os Cookies.
 */
function destroiCookies(){
	var ajaxRequest = new XMLHttpRequest();
	//alert("cheguei1");
    ajaxRequest.onreadystatechange = function() {
        if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
        	//alert("cheguei2");
        }
    };
	ajaxRequest.open("POST", "/RINTE_GDD/ServicoDestroiCookies", true);
	//alert("cheguei1");
	ajaxRequest.send();
}


/*-------------------------------------INFO-------------------------------------*/
/**
 * Este script permite obter informação do
 * utilizador que tem o login efetuado.
 */
function printUtilInfo(listaUtilInfo){
	var obj = JSON.parse(listaUtilInfo[0]);
	//alert(obj.util_nome.toString());
	document.getElementById("util_nome").innerHTML = obj.util_nome;
	document.getElementById("util_email").innerHTML = obj.util_email;
	document.getElementById("util_cartao_cidadao").innerHTML = obj.util_cartao_cidadao;
	document.getElementById("util_data_nascimento").innerHTML = obj.util_data_nascimento;
	document.getElementById("util_telefone").innerHTML = obj.util_telefone;
	document.getElementById("util_sexo").innerHTML = obj.util_sexo;
}
function getUtilInfo(){
	var ajaxRequest = new XMLHttpRequest();
	var test;
	//alert("ANTES DA RESPOSTA");
    ajaxRequest.onreadystatechange = function() {
        if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
        	//alert("NA RESPOSTA!");
        	test = JSON.parse(ajaxRequest.responseText);
        	printUtilInfo(test);
        	//alert(test[0].toString());
        }
    };
	ajaxRequest.open("POST", "/RINTE_GDD/ServicoUtilInfo", true);
	//alert("ANTES DO PEDIDO");
	ajaxRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajaxRequest.send("toServer=0");
}

/**
 * Este script permite obter informação das
 * provas em que o utilizador está ou não inscrito.
 */
function printProvasInfo(listaProvas){
	//alert(obj.util_nome.toString());
	var tabela = document.getElementById("tabelaProvas_body");
	var i = 0;
	
	for(i = 0; i < listaProvas.length; i++){
		var obj = JSON.parse(listaProvas[i]);
		
		var linha = tabela.insertRow(i);
		var col1 = linha.insertCell(0);
		var col2 = linha.insertCell(1);
		var col3 = linha.insertCell(2);
		var col4 = linha.insertCell(3);
		var col5 = linha.insertCell(4);
		var col6 = linha.insertCell(5);
		
		col1.innerHTML = obj.prova_nome;
		col2.innerHTML = obj.prova_sexo;
		col3.innerHTML = obj.prova_local;
		col4.innerHTML = obj.prova_hora;
		col5.innerHTML = obj.prova_data;
		
		if(obj.estado == 0){
			//Não Inscrito na modalidade
			col6.innerHTML = "NÃO";
		}
		if(obj.estado == 1){
			//Pedido de inscrição pendente
			col6.innerHTML = "PENDENTE";
		}
		if(obj.estado == 2){
			//Inscrito na modalidade
			col6.innerHTML = "SIM";
		}
	}
}
function getProvasInfo(){
	var ajaxRequest = new XMLHttpRequest();
	var listaProvas;
	//alert("ANTES DA RESPOSTA");
    ajaxRequest.onreadystatechange = function() {
        if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
        	//alert("NA RESPOSTA!");
        	listaProvas = JSON.parse(ajaxRequest.responseText);
        	printProvasInfo(listaProvas);
        	//alert(test[0].toString());
        }
    };
	ajaxRequest.open("POST", "/RINTE_GDD/ServicoProvasInfo", true);
	//alert("ANTES DO PEDIDO");
	ajaxRequest.send();
}

/**
 * Este script permite obter informação das
 * modalidades em que o utilizador está ou não inscrito.
 */
function printModInfo(listaMod){
	//alert(obj.util_nome.toString());
	var tabela = document.getElementById("tabelaMod_body");
	var i = 0;
	
	for(i = 0; i < listaMod.length; i++){
		var obj = JSON.parse(listaMod[i]);
		
		var linha = tabela.insertRow(i);
		var col1 = linha.insertCell(0);
		var col2 = linha.insertCell(1);
		
		col1.innerHTML = obj.mod_nome;
		
		if(obj.estado == 0){
			//Não Inscrito na modalidade
			col2.innerHTML = "NÃO";
		}
		if(obj.estado == 1){
			//Pedido de inscrição pendente
			col2.innerHTML = "PENDENTE";
		}
		if(obj.estado == 2){
			//Inscrito na modalidade
			col2.innerHTML = "SIM";
		}
	}
}
function getModInfo(){
	var ajaxRequest = new XMLHttpRequest();
	var listaMod;
	//alert("ANTES DA RESPOSTA");
    ajaxRequest.onreadystatechange = function() {
        if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
        	//alert("NA RESPOSTA!");alert(test[0].toString());
        	listaMod = JSON.parse(ajaxRequest.responseText);
        	//alert(listaMod[0].toString());
        	printModInfo(listaMod);
        	//alert(test[0].toString());
        }
    };
	ajaxRequest.open("POST", "/RINTE_GDD/ServicoModInfo", true);
	//alert("ANTES DO PEDIDO");
	ajaxRequest.send();
}
