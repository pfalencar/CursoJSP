<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>

<body>
	<h1>Cadastro de Usuário</h1>
	
	<form action="salvarUsuario" method="post" id="formUser">
		<ul class="form-style-1">
			<table>
				<tr>
					<td>Código:</td>
					<td><input type="text" readonly="readonly" id="id" name="id"
						value="${user.id}" class="field-long"></td>
				</tr>

				<tr>
					<td>Login:</td>
					<td><input type="text" id="login" name="login"
						value="${user.login}"></td>
				</tr>

				<tr>
					<td>Senha:</td>
					<td><input type="password" id="senha" name="senha"
						value="${user.senha}" class="field-long"></td>
				</tr>
				
				<tr>
					<td>Nome:</td>
					<td><input type="text" id="nome" name="nome"
						value="${user.nome}"></td>
				</tr>
				
				<tr>
					<td></td>
					<td>
						<input type="submit"value="Salvar" />
						<input type="submit" value ="Cancelar" onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset' "/>
					</td>
					
				</tr>
			</table>
		</ul>
	</form>
	<table id="customers">
		<caption><h3>Usuários Cadastrados</h3></caption>
		<tr>
			<th>Id</th>
			<th>Login</th>
			<th>Senha</th>
			<th>Nome</th>
			<th>Deletar</th>
			<th>Editar</th>
		</tr>
		<c:forEach items="${usuario}" var="user">
			
			
			<tr>
				<td style="width: 50px"><c:out value="${user.id}"></c:out></td>
				<td style="width: 150px"><c:out value="${user.login}"></c:out></td>
				<td><c:out value="${user.senha}"></c:out></td>
				<td><c:out value="${user.nome}"></c:out></td>

				<td><a href="salvarUsuario?acao=delete&user=${user.id}"><img src="resources/img/lixeira.png" alt="Excluir" title="Excluir"></a>
				<td><a href="salvarUsuario?acao=editar&user=${user.id}"><img src="resources/img/editar.png"  alt="Editar" title="Editar" /></a>
			</tr>
		</c:forEach>
	</table>

</body>
</html>