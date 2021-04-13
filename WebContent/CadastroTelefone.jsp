<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Telefone</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

</head>

<body>
  <a href="AcessoLiberado.jsp">Início</a>
  <a href="index.jsp">Sair</a>
  <h1>Cadastro de Telefones</h1>

  <h3 class="msg">${msg}</h3>

  <form action="salvarTelefone" method="post" id="formFone"
    onsubmit="return validarCampos() ? true : false">
    <ul class="form-style-1">
      <table>
        
        <tr>
          <td>Id Usuário:</td>
          <td><input type="text" readonly="readonly" id="usuarioId"
            name="usuarioId" value="${userEscolhido.id}" class="field-long"></td>
            
          <td>Usuário:</td>
          <td><input type="text" readonly="readonly" id="nome"
            name="nome" value="${userEscolhido.nome}" class="field-long"></td>
        </tr>
        
        <tr>
          <td>Código:</td>
          <td><input type="text" readonly="readonly" id="id"
            name="id" value="${fone.id}" class="field-long"></td>
            
          <td>Número:</td>
          <td><input type="text" id="numero" name="numero"
            value="${fone.numero}"></td>
            
          <td>Tipo:</td>
          <td><select id="tipo" name="tipo">
            <option>Celular</option>
            <option>Casa</option>
            <option>Trabalho</option>
            <option>Recado</option>
            <option>Outros</option>
          </select></td>
        </tr>

    
        <tr>
          <td></td>
          <td><input type="submit" value="Salvar" /> 
          </td>
        </tr>

      </table>

    </ul>
  </form>
  <table id="customers">
    <caption>
      <h3>Telefones Cadastrados</h3>
    </caption>
    <tr>
      <th>Id Usuário</th>
      <th>Usuário</th>
      <th>Id Telefone</th>
      <th>Número</th>
      <th>Tipo</th>
      <th>Excluir</th>
    </tr>
    
    <!-- "${telefone}" é a lista que vou carregar do banco de dados -->
    <c:forEach items="${telefone}" var="fone">


      <tr>
        <!-- "user.id" são os nomes do objeto! -->
        <!-- "user" é a entidade, por isso ".estado", que é o atributo. -->
        <td><c:out value="${userEscolhido.id}"></c:out></td>
        <td><c:out value="${userEscolhido.nome}"></c:out></td>
        <td><c:out value="${fone.id}"></c:out></td>
        <td><c:out value="${fone.numero}"></c:out></td>
        <td><c:out value="${fone.tipo}"></c:out></td>

        <td><a href="salvarTelefone?acao=delete&foneId=${fone.id}"><img
            src="resources/img/lixeira.png" alt="Excluir"
            title="Excluir"></a>
        </td>
      </tr>
    </c:forEach>
  </table>
  <script type="text/javascript">
			function validarCampos() {
				if (document.getElementById("numero").value == '') {
					alert("Número não pode ser vazio.");
					return false;
				} else tion validarCampos() {
					if (document.getElementById("tipo").value == '') {
						alert("Tipo não pode ser vazio.");
						return false;
					}
				return true;
			}
  </script>
</body>
</html>