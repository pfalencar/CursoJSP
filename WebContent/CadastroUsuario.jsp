<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Usu?rio</title>
<link rel="stylesheet" href="resources/css/cadastro.css">


<!-- Adicionando JQuery -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
  integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
  crossorigin="anonymous"></script>
</head>

<body>
  <a href="AcessoLiberado.jsp">In?cio</a>
  <a href="index.jsp">Sair</a>
  <h1>Cadastro de Usu?rio</h1>

  <h3 class="msg">${msg}</h3>

  <form action="salvarUsuario" method="post" id="formUser"
    onsubmit="return validarCampos() ? true : false;" enctype="multipart/form-data" >
    <ul class="form-style-1">
      <table>
        
        <tr>
          <td>C?digo:</td>
          <td><input type="text" readonly="readonly" id="id"
            name="id" value="${user.id}" class="field-long"></td>
            <td>Estado:</td>
          <td><input type="text" id="uf" name="uf"
            value="${user.estado}"></td>
        </tr>

        <tr>
          <td>Login:</td>
          <td><input type="text" id="login" name="login"
            value="${user.login}" placeholder="Insira um login"></td>
          <td>Rua:</td>
          <td><input type="text" id="rua" name="rua"
            value="${user.rua}"></td>
        </tr>

        <tr>
          <td>Senha:</td>
          <td><input type="password" id="senha" name="senha"
            value="${user.senha}" class="field-long" placeholder="Digite uma senha"></td>
            <td>Bairro:</td>
          <td><input type="text" id="bairro" name="bairro"
            value="${user.bairro}"></td>
        </tr>

        <tr>
          <td>Nome:</td>
          <td><input type="text" id="nome" name="nome"
            value="${user.nome}" placeholder="Digite o seu nome"></td>
            <td>Cidade:</td>
          <td><input type="text" id="cidade" name="cidade"
            value="${user.cidade}"></td>
        </tr>

        <tr>           
          <td>Cep:</td>
          <td><input type="text" id="cep" name="cep"
            value="${user.cep}" onblur="consultarCep()" placeholder="Digite um cep v?lido"></td> 
          <td>IBGE:</td>
          <td><input type="text" id="ibge" name="ibge"
            value="${user.ibge}"></td>
        </tr>
        
        <tr>
          <td>Foto: </td>
          <td>
            <input type="file" name="foto" />
              <input type="text" name="fotoTemp" readonly="readonly" value="${user.foto}"/>
              <input type="text" name="contentTypeFotoTemp" readonly="readonly" value="${user.contentType}"/>
          </td>
        </tr>
        
        <tr>
          <td>Curr?culo: </td>
          <td>
            <input type="file" name="curriculo"/>
              <input type="text" name="curriculoTemp" readonly="readonly" value="${user.curriculoBase64}"/> 
              <input type="text" name="curriculoContentType" readonly="readonly" value="${user.contentTypeCurriculo}"/> 
          </td>
        </tr>
        
        <tr>
          <td></td>
          <td><input type="submit" value="Salvar" /> 
          <input type="submit" value="Cancelar"
            onclick="document.getElementById('formUser').action='salvarUsuario?acao=reset'" />
          </td>
        </tr>

      </table>

    </ul>
  </form>
  
  <table id="customers">
    <caption><h3>Usu?rios Cadastrados</h3></caption>
    <tr>
      <th>Id</th>
      <th>Login</th>
      <th>Foto</th>
      <th>Curr?culo</th>
      <th>Nome</th>
      <th>Cep</th>
      <th>Rua</th>
      <th>Bairro</th>
      <th>Cidade</th>
      <th>Estado</th>
      <th>IBGE</th>
      <th>Deletar</th>
      <th>Editar</th>
      <th>Fones</th>
    </tr>
    
    <c:forEach items="${usuario}" var="user">
      <tr>
        <!-- "user.id" s?o os nomes do objeto! -->
        <td><c:out value="${user.id}"></c:out></td>
        <td><c:out value="${user.login}"></c:out></td>
        <td><a href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}" ><img src='<c:out value="${user.tempFotoUser}" />' alt="Imagem User" title="Imagem User" width="32px" height="32px"/></a></td>
        <td><a href="salvarUsuario?acao=download&tipo=curriculo&user=${user.id}">Curr?culo</a></td>
        <td><c:out value="${user.nome}"></c:out></td>
        <td><c:out value="${user.cep}"></c:out></td>
        <td><c:out value="${user.rua}"></c:out></td>
        <td><c:out value="${user.bairro}"></c:out></td>
        <td><c:out value="${user.cidade}"></c:out></td>
        <td><c:out value="${user.estado}"></c:out></td> <!-- "user" ? a entidade, por isso ".estado", que ? o atributo. -->
        <td><c:out value="${user.ibge}"></c:out></td>

        <td><a href="salvarUsuario?acao=delete&user=${user.id}"><img
            src="resources/img/lixeira.png" alt="Excluir"
            title="Excluir"></a>
        <td><a href="salvarUsuario?acao=editar&user=${user.id}">
        <img src="resources/img/editar.png" alt="Editar" title="Editar" /></a>
        <td><a href="salvarTelefone?acao=listarTelefones&user=${user.id}"><img
        src="resources/img/phone20px.png" alt="Telefones" title="Telefones" /></a>
      </tr>
      
    </c:forEach>
    
  </table>
  
  <script type="text/javascript">
			
			function validarCampos() {
				if (document.getElementById("login").value == '') {
					alert("Login n?o pode ser vazio.");
					return false;
				} else if (document.getElementById("senha").value == '') {
					alert("Senha n?o pode ser vazia.");
					return false;
				} else if (document.getElementById("nome").value == '') {
					alert("Nome n?o pode ser vazio.");
					return false;
				}
				return true;
			}
			
			
			function consultarCep() {
				var cep = $("#cep").val(); //"#" ? o id do cep	

				//Consulta o webservice viacep.com.br/
				$.getJSON("https://viacep.com.br/ws/" + cep
						+ "/json/?callback=?", function(dados) {

					if (!("erro" in dados)) {
						//Atualiza os campos com os valores da consulta.
						$("#rua").val(dados.logradouro);
						$("#bairro").val(dados.bairro);
						$("#cidade").val(dados.localidade);
						$("#uf").val(dados.uf);
						$("#ibge").val(dados.ibge);

					} else {
						$("#cep").val('');
						$("#rua").val('');
						$("#bairro").val('');
						$("#cidade").val('');
						$("#uf").val('');
						$("#ibge").val('');
						alert("CEP n?o encontrado.");
					}
				});

			}
		</script>
</body>
</html>