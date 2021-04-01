<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">


<!-- Adicionando JQuery -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
  integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
  crossorigin="anonymous"></script>
</head>

<body>
  <a href="AcessoLiberado.jsp">Início</a>
  <a href="index.jsp">Sair</a>
  <h1>Cadastro de Usuário</h1>

  <h3 class="msg">${msg}</h3>

  <form action="salvarUsuario" method="post" id="formUser"
    onsubmit="return validarCampos() ? true : false">
    <ul class="form-style-1">
      <table>
        
        <tr>
          <td>Código:</td>
          <td><input type="text" readonly="readonly" id="id"
            name="id" value="${user.id}" class="field-long"></td>
          <td>Cep:</td>
          <td><input type="text" id="cep" name="cep"
            value="${user.cep}" onblur="consultarCep()"></td>
        </tr>

        <tr>
          <td>Login:</td>
          <td><input type="text" id="login" name="login"
            value="${user.login}"></td>
          <td>Rua:</td>
          <td><input type="text" id="rua" name="rua"
            value="${user.rua}"></td>
        </tr>

        <tr>
          <td>Senha:</td>
          <td><input type="password" id="senha" name="senha"
            value="${user.senha}" class="field-long"></td>
            <td>Bairro:</td>
          <td><input type="text" id="bairro" name="bairro"
            value="${user.bairro}"></td>
        </tr>

        <tr>
          <td>Nome:</td>
          <td><input type="text" id="nome" name="nome"
            value="${user.nome}"></td>
            <td>Cidade:</td>
          <td><input type="text" id="cidade" name="cidade"
            value="${user.cidade}"></td>
        </tr>

        <tr>
          <td>Fone:</td>
          <td><input type="text" id="fone" name="fone"
            value="${user.fone}"></td>
            <td>Estado:</td>
          <td><input type="text" id="uf" name="uf"
            value="${user.estado}"></td>
        </tr>

        <tr>
          <td>IBGE:</td>
          <td><input type="text" id="ibge" name="ibge"
            value="${user.ibge}"></td>
        </tr>
        
        <tr>
          <td></td>
          <td><input type="submit" value="Salvar" /> <input
            type="submit" value="Cancelar"
            onclick="document.getElementById('formUser').action='salvarUsuario?acao=reset'" />
          </td>
        </tr>

      </table>

    </ul>
  </form>
  <table id="customers">
    <caption>
      <h3>Usuários Cadastrados</h3>
    </caption>
    <tr>
      <th>Id</th>
      <th>Login</th>
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
        <!-- "user.id" são os nomes do objeto! -->
        <td><c:out value="${user.id}"></c:out></td>
        <td><c:out value="${user.login}"></c:out></td>
        <td><c:out value="${user.nome}"></c:out></td>
        <td><c:out value="${user.cep}"></c:out></td>
        <td><c:out value="${user.rua}"></c:out></td>
        <td><c:out value="${user.bairro}"></c:out></td>
        <td><c:out value="${user.cidade}"></c:out></td>
        <td><c:out value="${user.estado}"></c:out></td>
        <!-- "user" é a entidade, por isso ".estado", que é o atributo. -->
        <td><c:out value="${user.ibge}"></c:out></td>

        <td><a href="salvarUsuario?acao=delete&user=${user.id}"><img
            src="resources/img/lixeira.png" alt="Excluir"
            title="Excluir"></a>
        <td><a href="salvarUsuario?acao=editar&user=${user.id}"><img
            src="resources/img/editar.png" alt="Editar" title="Editar" /></a>
        <td><a href="salvarTelefone?acao=listarTelefones&user=${user.id}"><img
        src="resources/img/phone20px.png" alt="Telefones" title="Telefones" /></a>
      </tr>
    </c:forEach>
  </table>
  <script type="text/javascript">
			function validarCampos() {
				if (document.getElementById("login").value == '') {
					alert("Login não pode ser vazio.");
					return false;
				} else if (document.getElementById("senha").value == '') {
					alert("Senha não pode ser vazia.");
					return false;
				} else if (document.getElementById("nome").value == '') {
					alert("Nome não pode ser vazio.");
					return false;
				} else if (document.getElementById('fone').value == '') {
					alert("Telefone não pode ser vazio.");
					return false;
				}
				return true;
			}

			function consultarCep() {
				var cep = $("#cep").val(); //"#" é o id do cep	

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
						alert("CEP não encontrado.");
					}
				});

			}
		</script>
</body>
</html>